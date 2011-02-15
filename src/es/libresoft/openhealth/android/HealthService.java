/*
Copyright (C) 2008-2011 GSyC/LibreSoft, Universidad Rey Juan Carlos.

Author: Jose Antonio Santos Cadenas <jcaden@libresoft.es>
Author: Santiago Carot-Nemesio <scarot@libresoft.es>

This program is a (FLOS) free libre and open source implementation
of a multiplatform manager device written in java according to the
ISO/IEEE 11073-20601. Manager application is designed to work in
DalvikVM over android platform.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package es.libresoft.openhealth.android;

import ieee_11073.part_20601.asn1.HANDLE;
import ieee_11073.part_20601.asn1.INT_U16;
import ieee_11073.part_20601.fsm.State;
import ieee_11073.part_20601.phd.channel.tcp.TcpManagerChannel;
import ieee_11073.part_20601.phd.dim.Attribute;
import ieee_11073.part_20601.phd.dim.DIM;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import es.libresoft.openhealth.Agent;
import es.libresoft.openhealth.android.aidl.IAgent;
import es.libresoft.openhealth.android.aidl.types.IError;
import es.libresoft.openhealth.android.aidl.IAgentService;
import es.libresoft.openhealth.android.aidl.IManagerClientCallback;
import es.libresoft.openhealth.android.aidl.IManagerService;
import es.libresoft.openhealth.android.aidl.types.IAttribute;
import es.libresoft.openhealth.android.aidl.types.measures.IAgentMetric;
import es.libresoft.openhealth.android.aidl.types.objects.IDIMClass;
import es.libresoft.openhealth.android.aidl.types.objects.INumeric;
import es.libresoft.openhealth.android.aidl.types.objects.IRT_SA;
import es.libresoft.openhealth.android.aidl.types.objects.IScanner;
import es.libresoft.openhealth.error.ErrorCodes;
import es.libresoft.openhealth.error.ErrorException;
import es.libresoft.openhealth.error.ErrorFactory;
import es.libresoft.openhealth.events.EventType;
import es.libresoft.openhealth.events.InternalEventManager;
import es.libresoft.openhealth.events.InternalEventReporter;
import es.libresoft.openhealth.events.MeasureReporter;
import es.libresoft.openhealth.events.MeasureReporterFactory;
import es.libresoft.openhealth.storage.ConfigStorageFactory;
import es.libresoft.openhealth.utils.DIM_Tools;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class HealthService extends Service {

	/** Registered clients */
	Vector<IManagerClientCallback> clients = new Vector<IManagerClientCallback>();

	private TcpManagerChannel channelTCP;
	private boolean started = false;
	private Vector<Agent> agents = new Vector<Agent>();

	/************************************************************
	 * Internal events triggered from manager thread
	 ************************************************************/
	private final InternalEventManager ieManager = new InternalEventManager() {

		@Override
		public void agentChangeState(Agent agent, int state) {
			IManagerClientCallback[] cliArray;
			cliArray = clients.toArray(new IManagerClientCallback[0]);
			for (IManagerClientCallback c: cliArray) {
				try {
					String str = "";
					switch (state) {
					case State.DISCONNECTED:
						str = getString(R.string.DISCONNECTED);
						break;
					case State.CONNECTED_UNASSOCIATED:
						str = getString(R.string.CONNECTED_UNASSOCIATED);
						break;
					case State.CONNECTED_ASSOCIATING:
						str = getString(R.string.CONNECTED_ASSOCIATING);
						break;
					case State.CONNECTED_ASSOCIATED_CONFIGURING_WAITING:
						str = getString(R.string.CONNECTED_ASSOCIATED_CONFIGURING_WAITING);
						break;
					case State.CONNECTED_ASSOCIATED_CONFIGURING_CHECKING_CONFIG:
						str = getString(R.string.CONNECTED_ASSOCIATED_CONFIGURING_CHECKING_CONFIG);
						break;
					case State.CONNECTED_ASSOCIATED_OPERATING:
						str = getString(R.string.CONNECTED_ASSOCIATED_OPERATING);
						break;
					case State.CONNECTED_DISASSOCIATING:
						str = getString(R.string.CONNECTED_DISASSOCIATING);
						break;
					default:
						return;
					}
					c.agentChangeState(new IAgent(agent.getId()), str);
				} catch (RemoteException e) {
					clients.removeElement(c);
				}
			}
		}

		@Override
		public void receivedMeasure(Agent agent, MeasureReporter mr) {
			if (!(mr instanceof AndroidMeasureReporter))
				return;
			AndroidMeasureReporter amr = (AndroidMeasureReporter) mr;
			IAgentMetric metric = amr.getMetric();
			IAgent iagent = new IAgent(agent.getId());

			IManagerClientCallback[] cliArray;
			cliArray = clients.toArray(new IManagerClientCallback[0]);
			for (IManagerClientCallback c: cliArray) {
				try {
					c.agentNewMeassure(iagent, metric);
				} catch (RemoteException e) {
					clients.removeElement(c);
				}
			}
		}

		@Override
		public void agentPlugged(Agent agent) {
			agents.add(agent);
			IManagerClientCallback[] cliArray;
			cliArray = clients.toArray(new IManagerClientCallback[0]);
			for (IManagerClientCallback c: cliArray) {
				try {
					c.agentPlugged(new IAgent(agent.getId()));
				} catch (RemoteException e) {
					clients.removeElement(c);
				}
			}
		}

		@Override
		public void agentUnplugged(Agent agent) {
			agents.removeElement(agent);
			IManagerClientCallback[] cliArray;
			cliArray = clients.toArray(new IManagerClientCallback[0]);
			for (IManagerClientCallback c: cliArray) {
				try {
					c.agentUnplugged(new IAgent(agent.getId()));
				} catch (RemoteException e) {
					clients.removeElement(c);
				}
			}
		}

		@Override
		public void error(Agent agent, int errorCode) {
			IAgent iagent = new IAgent(agent.getId());
			IError error;
			try {
				error = new IError(errorCode, ErrorFactory.getDefaultErrorGenerator().error2string(errorCode));
			} catch (ErrorException e) {
				error = new IError(errorCode, HealthService.this.getString(R.string.UNEXPECTED_ERROR));
			}

			IManagerClientCallback[] cliArray;
			cliArray = clients.toArray(new IManagerClientCallback[0]);
			for (IManagerClientCallback c: cliArray) {
				try {
					c.error(iagent, error);
				} catch (RemoteException e) {
					clients.removeElement(c);
				}
			}
		}
	};

	@Override
	public void onCreate() {
		//Set the event manager handler to get internal events from the manager thread
		InternalEventReporter.setDefaultEventManager(ieManager);
		//Set target platform to android to report measures using IPC mechanism
		MeasureReporterFactory.setDefaultMeasureReporter(MeasureReporterFactory.ANDROID);
		ConfigStorageFactory.setDefaultConfigStorage(new AndroidConfigStorage(this.getApplicationContext()));
		AttributeUtils.setContext(getApplicationContext());
		ErrorFactory.setDefaultErrorGenerator(new AndroidError(this.getApplicationContext()));
		System.out.println("Service created");
		channelTCP = new TcpManagerChannel();
		super.onCreate();
	}

	void initTransportLayers() {
		channelTCP.start();
	}

	void shutdownTransportLayers() {
		channelTCP.finish();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!started) {
			initTransportLayers();
			started = true;
		}

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		shutdownTransportLayers();
		started = false;
	}

	/**
	 * The IManagerService is defined through IDL
	 */
	private final IManagerService.Stub managerServiceStub = new IManagerService.Stub() {

		@Override
		public void agents(List<IAgent> agentList) throws RemoteException {
			if (agentList == null)
				return;

			for(Agent agent: agents)
				agentList.add(new IAgent(agent.getId()));
		}

		@Override
		public void registerApplication(IManagerClientCallback mc)
				throws RemoteException {
			if (mc != null)
				clients.add(mc);
		}

		@Override
		public void unregisterApplication(IManagerClientCallback mc)
				throws RemoteException {
			clients.removeElement(mc);
			if (clients.size() == 0) {
				HealthService.this.stopSelf();
			}
		}

	};

	private Agent getAgent(IAgent agent) {
		for(Agent a : agents) {
			if (a.getId() == agent.getId())
				return a;
		}
		return null;
	}

	private DIM getObject(Agent a, IDIMClass obj) {
		HANDLE handle = new HANDLE();
		handle.setValue(new INT_U16(obj.getHandle()));

		return a.mdsHandler.getMDS().getObject(handle);
	}

	private void parcelAttributes(DIM object, List<IAttribute> attrs, IError error) {
		Hashtable<Integer, Attribute> attList = object.getAttributes();
		for (Integer key: attList.keySet()) {
			Attribute att = attList.get(key);
			IAttribute iAtt = new IAttribute();
			if (!IAttrFactory.getParcelableAttribute(att.getAttributeType(), iAtt)) {
				error.setErrCode(ErrorCodes.INVALID_ATTRIBUTE);
				setErrorMessage(error);
				return;
			}
			iAtt.setAttrId(key);
			iAtt.setAttrIdStr(DIM_Tools.getAttributeName(key));

			attrs.add(iAtt);
		}

		error.setErrCode(ErrorCodes.NO_ERROR);
		setErrorMessage(error);
	}
	/**
	 * The IAgentService is defined through IDL
	 */
	private final IAgentService.Stub agentServiceStub = new IAgentService.Stub() {

		@Override
		public void connect(IAgent agent) throws RemoteException {
			System.out.println("TODO: Connect with the agent");
		}

		@Override
		public void getAttributes(IAgent agent, List<IAttribute> attrs, IError error) {

			Agent a = getAgent(agent);

			if (error == null || attrs == null)
				return;

			if (a == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			parcelAttributes(a.mdsHandler.getMDS(), attrs, error);
		}

		@Override
		public void getAttribute(IAgent agent, int attrId, IAttribute attr, IError error)
				throws RemoteException {

			Agent a = getAgent(agent);
			if (error == null || attr == null)
				return;

			if (a == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			Attribute at = a.mdsHandler.getMDS().getAttribute(attrId);
			if (at == null || !IAttrFactory.getParcelableAttribute(at.getAttributeType(), attr)) {
				error.setErrCode(ErrorCodes.INVALID_ATTRIBUTE);
				setErrorMessage(error);
				return;
			}

			attr.setAttrId(attrId);
			attr.setAttrIdStr(DIM_Tools.getAttributeName(attrId));

			error.setErrCode(ErrorCodes.NO_ERROR);
			setErrorMessage(error);
		}

		@Override
		public boolean updateMDS(IAgent agent, IError err) throws RemoteException {
			Agent a = getAgent(agent);

			if (err == null) {
				err = new IError();
			}

			if (a == null) {
				err.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(err);
				return false;
			}

			AndroidExternalEvent<Boolean, Object> ev = new AndroidExternalEvent<Boolean, Object>(EventType.REQ_MDS, null);

			a.sendEvent(ev);

			try {
				ev.proccessing();
			} catch (InterruptedException e) {
				err.setErrCode(ErrorCodes.UNEXPECTED_ERROR);
				setErrorMessage(err);
				return false;
			}

			if (ev.wasError()) {
				err.setErrCode(ev.getError());
				setErrorMessage(err);
				return false;
			}

			err.setErrCode(ErrorCodes.NO_ERROR);
			setErrorMessage(err);

			return ev.getRspData();
		}

		@Override
		public void getState(IAgent agent, String[] state, IError error)
		{
			if (error == null) {
				error = new IError();
			}

			if (state == null) {
				state = new String[1];
			}

			Agent a;
			if (agent == null || (a = getAgent(agent)) == null)
			{
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			state[0] = a.getStateName();
		}

		@Override
		public void getNumeric(IAgent agent, List<INumeric> nums, IError error)
				throws RemoteException {

			if (error == null) {
				error = new IError();
			}

			if (nums == null) {
				nums = new ArrayList<INumeric>();
			}

			if (agent == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			Agent a = getAgent(agent);

			if (a == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			for (Integer handle: a.mdsHandler.getMDS().getNumericHandlers())
				nums.add(new INumeric(handle, agent));

		}

		@Override
		public void getScanner(IAgent agent, List<IScanner> scanners, IError error)
				throws RemoteException {

			if (error == null) {
				error = new IError();
			}

			if (scanners == null) {
				scanners = new ArrayList<IScanner>();
			}

			if (agent == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			Agent a = getAgent(agent);

			if (a == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			for (Integer handle: a.mdsHandler.getMDS().getScannerHandlers())
				scanners.add(new IScanner(handle, agent));

		}

		@Override
		public void getRT_SA(IAgent agent, List<IRT_SA> rts, IError error)
				throws RemoteException {

			if (error == null) {
				error = new IError();
			}

			if (rts == null) {
				rts = new ArrayList<IRT_SA>();
			}

			if (agent == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			Agent a = getAgent(agent);

			if (a == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			for (Integer handle: a.mdsHandler.getMDS().getRT_SAHandlers())
				rts.add(new IRT_SA(handle, agent));

		}

		@Override
		public void getObjectAttrs(IAgent agent, IDIMClass obj, List<IAttribute> attrs, IError error)
					throws RemoteException {
			if (error == null) {
				error = new IError();
			}

			if (attrs  == null) {
				attrs = new ArrayList<IAttribute>();
			}

			if (agent == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			if (obj == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_OBJECT);
				setErrorMessage(error);
				return;
			}

			Agent a = getAgent(agent);

			if (a == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_AGENT);
				setErrorMessage(error);
				return;
			}

			DIM o = getObject(a, obj);

			if (o == null) {
				error.setErrCode(ErrorCodes.UNKNOWN_OBJECT);
				setErrorMessage(error);
				return;
			}

			parcelAttributes(o, attrs, error);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		if (IManagerService.class.getName().equals(intent.getAction()))
			return managerServiceStub;
		if (IAgentService.class.getName().equals(intent.getAction()))
			return agentServiceStub;
		return null;
	}

	/**
	 * Sets the error message with the string correct string for the error code already set
	 * @param err The error which error message will be set
	 */
	private void setErrorMessage(IError err) {
		try {
			err.setErrMsg(ErrorFactory.getDefaultErrorGenerator().error2string(err.getErrCode()));
		} catch (ErrorException e) {
			err.setErrMsg(HealthService.this.getString(R.string.UNEXPECTED_ERROR));
		}
	}

}
