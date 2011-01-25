/*
Copyright (C) 2011 GSyC/LibreSoft, Universidad Rey Juan Carlos.

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

import ieee_11073.part_20601.phd.channel.tcp.TcpManagerChannel;

import java.util.List;
import java.util.Vector;

import es.libresoft.openhealth.Agent;
import es.libresoft.openhealth.events.InternalEventManager;
import es.libresoft.openhealth.events.InternalEventReporter;
import es.libresoft.openhealth.events.MeasureReporter;
import es.libresoft.openhealth.events.MeasureReporterFactory;
import es.libresoft.openhealth.storage.ConfigStorageFactory;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class HealthService extends Service {

	/** Registered clients */
	Vector<IManagerClientCallback> clients = new Vector<IManagerClientCallback>();

	private TcpManagerChannel channelTCP;

	/************************************************************
	 * Internal events triggered from manager thread
	 ************************************************************/
	private final InternalEventManager ieManager = new InternalEventManager() {

		@Override
		public void agentChangeState(Agent agent, String state) {
			System.out.println("TODO: agentChangeStatus");
		}

		@Override
		public void receivedMeasure(Agent agent, MeasureReporter mr) {
			System.out.println("TODO: receivedMeasure");
		}
	};

	@Override
	public void onCreate() {
		//Set the event manager handler to get internal events from the manager thread
		InternalEventReporter.setDefaultEventManager(ieManager);
		//Set target platform to android to report measures using IPC mechanism
		MeasureReporterFactory.setDefaultMeasureReporter(MeasureReporterFactory.ANDROID);
		ConfigStorageFactory.setDefaultConfigStorage(new AndroidConfigStorage(this.getApplicationContext()));
		System.out.println("Service created");
		channelTCP = new TcpManagerChannel();
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		try {
			channelTCP.start();
			super.onStart(intent, startId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		channelTCP.finish();
	}

	/**
	 * The IManagerService is defined through IDL
	 */
	private final IManagerService.Stub managerServiceStub = new IManagerService.Stub() {

		@Override
		public void agents(List<IAgent> agentList) throws RemoteException {
			System.out.println("TODO implement 'agents' method");
		}

		@Override
		public void registerApplication(IManagerClientCallback mc)
				throws RemoteException {
			clients.add(mc);
		}

		@Override
		public void unregisterApplication(IManagerClientCallback mc)
				throws RemoteException {
			clients.removeElement(mc);
		}

	};

	@Override
	public IBinder onBind(Intent intent) {
		if (IManagerService.class.getName().equals(intent.getAction()))
			return managerServiceStub;
		return null;
	}

}
