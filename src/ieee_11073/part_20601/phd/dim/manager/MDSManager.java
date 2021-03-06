/*
Copyright (C) 2008-2009  Santiago Carot Nemesio
email: scarot@libresoft.es
Copyright (C) 2008-2009  José Antonio Santos Cadenas
email: jcaden@libresoft.es

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


package ieee_11073.part_20601.phd.dim.manager;

import ieee_11073.part_10101.Nomenclature;
import ieee_11073.part_20601.asn1.AVA_Type;
import ieee_11073.part_20601.asn1.AbsoluteTime;
import ieee_11073.part_20601.asn1.ApduType;
import ieee_11073.part_20601.asn1.AttrValMap;
import ieee_11073.part_20601.asn1.AttrValMapEntry;
import ieee_11073.part_20601.asn1.BasicNuObsValue;
import ieee_11073.part_20601.asn1.ConfigId;
import ieee_11073.part_20601.asn1.ConfigObject;
import ieee_11073.part_20601.asn1.ConfigReport;
import ieee_11073.part_20601.asn1.ConfigReportRsp;
import ieee_11073.part_20601.asn1.ConfigResult;
import ieee_11073.part_20601.asn1.DataApdu;
import ieee_11073.part_20601.asn1.GetResultSimple;
import ieee_11073.part_20601.asn1.HANDLE;
import ieee_11073.part_20601.asn1.INT_U16;
import ieee_11073.part_20601.asn1.InvokeIDType;
import ieee_11073.part_20601.asn1.MdsTimeInfo;
import ieee_11073.part_20601.asn1.MetricSpecSmall;
import ieee_11073.part_20601.asn1.OID_Type;
import ieee_11073.part_20601.asn1.ObservationScan;
import ieee_11073.part_20601.asn1.ObservationScanFixed;
import ieee_11073.part_20601.asn1.ProdSpecEntry;
import ieee_11073.part_20601.asn1.ProductionSpec;
import ieee_11073.part_20601.asn1.RegCertData;
import ieee_11073.part_20601.asn1.RegCertDataList;
import ieee_11073.part_20601.asn1.ScanReportInfoFixed;
import ieee_11073.part_20601.asn1.ScanReportInfoMPFixed;
import ieee_11073.part_20601.asn1.ScanReportInfoMPVar;
import ieee_11073.part_20601.asn1.ScanReportInfoVar;
import ieee_11073.part_20601.asn1.SystemModel;
import ieee_11073.part_20601.asn1.TYPE;
import ieee_11073.part_20601.asn1.TypeVer;
import ieee_11073.part_20601.asn1.TypeVerList;
import ieee_11073.part_20601.phd.dim.Attribute;
import ieee_11073.part_20601.phd.dim.DIM;
import ieee_11073.part_20601.phd.dim.DimTimeOut;
import ieee_11073.part_20601.phd.dim.Enumeration;
import ieee_11073.part_20601.phd.dim.InvalidAttributeException;
import ieee_11073.part_20601.phd.dim.MDS;
import ieee_11073.part_20601.phd.dim.Numeric;
import ieee_11073.part_20601.phd.dim.TimeOut;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import es.libresoft.mdnf.SFloatType;
import es.libresoft.openhealth.events.InternalEventReporter;
import es.libresoft.openhealth.events.MeasureReporter;
import es.libresoft.openhealth.events.MeasureReporterFactory;
import es.libresoft.openhealth.events.MeasureReporterUtils;
import es.libresoft.openhealth.messages.MessageFactory;
import es.libresoft.openhealth.utils.ASN1_Values;
import es.libresoft.openhealth.utils.DIM_Tools;
import es.libresoft.openhealth.utils.RawDataExtractor;

public class MDSManager extends MDS {

	/**
	 * Used only in extended configuration when the agent configuration is unknown
	 */
	public MDSManager (byte[] system_id, ConfigId devConfig_id){
		super(system_id,devConfig_id);
	}

	public MDSManager(Hashtable<Integer, Attribute> attributeList)
		throws InvalidAttributeException {
		super(attributeList);
	}

	public void configureMDS(Collection<ConfigObject> config) throws InvalidAttributeException {
		Iterator<ConfigObject> i = config.iterator();
		ConfigObject confObj;

		while (i.hasNext()){
			confObj = i.next();
			Hashtable<Integer,Attribute> attribs;
			//Get Attributes
			try {
				attribs = getAttributes(confObj.getAttributes(), getDeviceConf().getEncondigRules());
			} catch (Exception e) {
				e.printStackTrace();
				throw new InvalidAttributeException(e);
			}

			//Generate attribute Handle:
			HANDLE handle = new HANDLE();
			handle.setValue(new INT_U16(new Integer
					(confObj.getObj_handle().getValue().getValue())));
			Attribute attr = new Attribute(Nomenclature.MDC_ATTR_ID_HANDLE,
					handle);
			//Set Attribute Handle to the list
			attribs.put(Nomenclature.MDC_ATTR_ID_HANDLE, attr);

			//checkGotAttributes(attribs);
			int classId = confObj.getObj_class().getValue().getValue();
			switch (classId) {
			case Nomenclature.MDC_MOC_VMS_MDS_SIMP : // MDS Class
				addCheckedAttributes(attribs);
				break;
			case Nomenclature.MDC_MOC_VMO_METRIC : // Metric Class
				throw new UnsupportedOperationException("Unsoportedd Metric Class");
			case Nomenclature.MDC_MOC_VMO_METRIC_NU : // Numeric Class
				addNumeric(new Numeric(attribs));
				break;
			case Nomenclature.MDC_MOC_VMO_METRIC_SA_RT: // RT-SA Class
				throw new UnsupportedOperationException("Unsoportedd RT-SA Class");
			case Nomenclature.MDC_MOC_VMO_METRIC_ENUM: // Enumeration Class
				addEnumeration(new Enumeration(attribs));
				break;
			case Nomenclature.MDC_MOC_VMO_PMSTORE: // PM-Store Class
				addPM_Store(new MPM_Store(attribs));
				break;
			case Nomenclature.MDC_MOC_PM_SEGMENT: // PM-Segment Class
				throw new UnsupportedOperationException("Unsoportedd PM-Segment Class");
			case Nomenclature.MDC_MOC_SCAN: // Scan Class
				throw new UnsupportedOperationException("Unsoportedd Scan Class");
			case Nomenclature.MDC_MOC_SCAN_CFG: // CfgScanner Class
				throw new UnsupportedOperationException("Unsoportedd CfgScanner Class");
			case Nomenclature.MDC_MOC_SCAN_CFG_EPI: // EpiCfgScanner Class
				addScanner(new MEpiCfgScanner(attribs));
				break;
			case Nomenclature.MDC_MOC_SCAN_CFG_PERI: // PeriCfgScanner Class
				throw new UnsupportedOperationException("Unsoportedd PeriCfgScanner Class");
			}
		}
	}

	@Override
	public ConfigReportRsp MDS_Configuration_Event(ConfigReport config) {
		int configId = config.getConfig_report_id().getValue();

		try {
			configureMDS(config.getConfig_obj_list().getValue());
			/* Store current configuration */
			storeConfiguration();
			return generateConfigReportRsp(configId,
					ASN1_Values.CONF_RESULT_ACCEPTED_CONFIG);
		} catch (Exception e) {
			e.printStackTrace();
			clearObjectsFromMds();
			if ((ASN1_Values.CONF_ID_STANDARD_CONFIG_START <= configId) && (configId <= ASN1_Values.CONF_ID_STANDARD_CONFIG_END))
				//Error in standard configuration
				return generateConfigReportRsp(configId,
						ASN1_Values.CONF_RESULT_STANDARD_CONFIG_UNKNOWN);
			else return generateConfigReportRsp(configId,
					ASN1_Values.CONF_RESULT_UNSUPPORTED_CONFIG);
		}

	}

	@Override
	public void MDS_Dynamic_Data_Update_Fixed(ScanReportInfoFixed info) {
		try{
			String system_id = DIM_Tools.byteArrayToString(
					(byte[])getAttribute(Nomenclature.MDC_ATTR_SYS_ID).getAttributeType());

			Iterator<ObservationScanFixed> i= info.getObs_scan_fixed().iterator();
			ObservationScanFixed obs;
			while (i.hasNext()) {
				obs=i.next();

				//Get DIM from Handle_id
				DIM elem = getObject(obs.getObj_handle());
				AttrValMap avm = (AttrValMap)elem.getAttribute(Nomenclature.MDC_ATTR_ATTRIBUTE_VAL_MAP).getAttributeType();
				Iterator<AttrValMapEntry> it = avm.getValue().iterator();
				RawDataExtractor de = new RawDataExtractor(obs.getObs_val_data());
				MeasureReporter mr = MeasureReporterFactory.getDefaultMeasureReporter();
				MeasureReporterUtils.addAttributesToReport(mr,elem);
				while (it.hasNext()){
					AttrValMapEntry attr = it.next();
					int attrId = attr.getAttribute_id().getValue().getValue();
					int length = attr.getAttribute_len();
					try {
						mr.addMeasure(attrId, RawDataExtractor.decodeRawData(attrId,de.getData(length), this.getDeviceConf().getEncondigRules()));
					}catch(Exception e){
						System.err.println("Error: Can not get attribute " + attrId);
						e.printStackTrace();
					}
				}
				InternalEventReporter.receivedMeasure(system_id, mr);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void MDS_Dynamic_Data_Update_Var(ScanReportInfoVar info) {
		try{
			String system_id = DIM_Tools.byteArrayToString(
					(byte[])getAttribute(Nomenclature.MDC_ATTR_SYS_ID).getAttributeType());

			Iterator<ObservationScan> i= info.getObs_scan_var().iterator();
			ObservationScan obs;
			MeasureReporter mr = MeasureReporterFactory.getDefaultMeasureReporter();

			while (i.hasNext()) {
				obs=i.next();
				//Get Numeric from Handle_id
				Numeric numeric = getNumeric(obs.getObj_handle());
				MeasureReporterUtils.addAttributesToReport(mr,numeric);
				if (numeric == null)
					throw new Exception("Numeric class not found for handle: " + obs.getObj_handle().getValue().getValue());

				Iterator<AVA_Type> it = obs.getAttributes().getValue().iterator();
				while (it.hasNext()){
					AVA_Type att = it.next();
					Integer att_id = att.getAttribute_id().getValue().getValue();
					byte[] att_value = att.getAttribute_value();
					mr.addMeasure(att_id, RawDataExtractor.decodeRawData(att_id,att_value, this.getDeviceConf().getEncondigRules()));
				}
				InternalEventReporter.receivedMeasure(system_id, mr);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	//----------------------------------------PRIVATE-----------------------------------------------------------
	private void checkGotAttributes(Hashtable<Integer,Attribute> attribs){
		Iterator<Integer> i = attribs.keySet().iterator();
		while (i.hasNext()){
			int id = i.next();
			attribs.get(id);
			System.out.println("Checking attribute: " + DIM_Tools.getAttributeName(id) + " " + id);
			Attribute attr = attribs.get(id);
			switch (id){
			case Nomenclature.MDC_ATTR_ID_TYPE :
				TYPE t = (TYPE) attribs.get(new Integer(id)).getAttributeType();
				System.out.println("partition: " + t.getPartition().getValue());
				System.out.println("code: " + t.getCode().getValue().getValue());
				System.out.println("ok.");
				break;
			case Nomenclature.MDC_ATTR_TIME_ABS:
			case Nomenclature.MDC_ATTR_TIME_STAMP_ABS :
				AbsoluteTime time = (AbsoluteTime) attr.getAttributeType();
				System.out.println("century: " + Integer.toHexString(time.getCentury().getValue()));
				System.out.println("year: " + Integer.toHexString(time.getYear().getValue()));
				System.out.println("month: " + Integer.toHexString(time.getMonth().getValue()));
				System.out.println("day: "+ Integer.toHexString(time.getDay().getValue()));
				System.out.println("hour: " + Integer.toHexString(time.getHour().getValue()));
				System.out.println("minute: " + Integer.toHexString(time.getMinute().getValue()));
				System.out.println("second: " + Integer.toHexString(time.getSecond().getValue()));
				System.out.println("sec-fraction: " + Integer.toHexString(time.getSec_fractions().getValue()));
				break;
			case Nomenclature.MDC_ATTR_UNIT_CODE:
				OID_Type oid = (OID_Type)attribs.get(new Integer(id)).getAttributeType();
				System.out.println("oid: " + oid.getValue().getValue());
				System.out.println("ok.");
				break;
			case Nomenclature.MDC_ATTR_METRIC_SPEC_SMALL:
				MetricSpecSmall mss = (MetricSpecSmall)attribs.get(new Integer(id)).getAttributeType();
				//System.out.println("partition: " + getHexString(mss.getValue().getValue()));
				System.out.println("ok.");
				break;
			case Nomenclature.MDC_ATTR_NU_VAL_OBS_BASIC :
				BasicNuObsValue val = (BasicNuObsValue)attribs.get(new Integer(id)).getAttributeType();
				try {
						SFloatType sf = new SFloatType(val.getValue().getValue());
						System.out.println("BasicNuObsValue: " + sf.doubleValueRepresentation());
					} catch (Exception e) {
						e.printStackTrace();
					}
				System.out.println("ok.");
				break;
			case Nomenclature.MDC_ATTR_ATTRIBUTE_VAL_MAP:
				AttrValMap avm = (AttrValMap)attribs.get(new Integer(id)).getAttributeType();
				Iterator<AttrValMapEntry> iter = avm.getValue().iterator();
				while (iter.hasNext()){
					AttrValMapEntry entry = iter.next();
					System.out.println("--");
					System.out.println("attrib-id: " + entry.getAttribute_id().getValue().getValue());
					System.out.println("attrib-len: " + entry.getAttribute_len());
				}
				System.out.println("ok.");
				break;
			case Nomenclature.MDC_ATTR_SYS_TYPE_SPEC_LIST:
				TypeVerList sysTypes = (TypeVerList) attr.getAttributeType();
				Iterator<TypeVer> it = sysTypes.getValue().iterator();
				System.out.println("Spec. list values:");
				while (it.hasNext()) {
					System.out.println("\t" + it.next().getType().getValue().getValue());
				}
				break;
			case Nomenclature.MDC_ATTR_DEV_CONFIG_ID:
				ConfigId configId = (ConfigId) attr.getAttributeType();
				System.out.println("Dev config id: " + configId.getValue());
				break;
			case Nomenclature.MDC_ATTR_SYS_ID:
				byte[] octet = (byte[]) attr.getAttributeType();
				String sysId = new String(octet);
				System.out.println("Sys id: " + sysId);
				break;
			case Nomenclature.MDC_ATTR_ID_MODEL:
				SystemModel systemModel = (SystemModel) attr.getAttributeType();
				System.out.println("System manufactures: " + new String(systemModel.getManufacturer()));
				System.out.println("System model number: " + new String(systemModel.getModel_number()));
				break;
			case Nomenclature.MDC_ATTR_ID_HANDLE:
				HANDLE handle = (HANDLE) attr.getAttributeType();
				System.out.println("Id handle: " + handle.getValue().getValue());
				break;
			case Nomenclature.MDC_ATTR_REG_CERT_DATA_LIST:
				System.out.println("Reg cert. data list: ");
				RegCertDataList regList = (RegCertDataList) attr.getAttributeType();
				Iterator<RegCertData> regIt = regList.getValue().iterator();
				while (regIt.hasNext()) {
					RegCertData cert = regIt.next();
					System.out.println("\t" + cert.getAuth_body_and_struc_type().getAuth_body().getValue() +
								" " + cert.getAuth_body_and_struc_type().getAuth_body_struc_type().getValue());
				}
				break;
			case Nomenclature.MDC_ATTR_MDS_TIME_INFO:
				System.out.println("Mds time information:");
				MdsTimeInfo timeInfo = (MdsTimeInfo) attr.getAttributeType();
				byte[] capabilities = timeInfo.getMds_time_cap_state().getValue().getValue();
				System.out.print("\t");
				for (int i1 = 0; i1 < capabilities.length; i1++) {
					String binary = Integer.toBinaryString(capabilities[i1]);
					if (binary.length() > 8)
						binary = binary.substring(binary.length() - 8, binary.length());
					System.out.print(binary);
				}
				System.out.println();
				System.out.println("\t" + timeInfo.getTime_sync_protocol().getValue().getValue().getValue());
				System.out.println("\t" + timeInfo.getTime_sync_accuracy().getValue().getValue());
				System.out.println("\t" + timeInfo.getTime_resolution_abs_time());
				System.out.println("\t" + timeInfo.getTime_resolution_rel_time());
				System.out.println("\t" + timeInfo.getTime_resolution_high_res_time().getValue());
				break;
			case Nomenclature.MDC_ATTR_ID_PROD_SPECN:
				System.out.println("Production specification:");
				ProductionSpec ps = (ProductionSpec) attr.getAttributeType();
				Iterator<ProdSpecEntry> itps = ps.getValue().iterator();
				while (itps.hasNext()) {
					ProdSpecEntry pse = itps.next();
					System.out.println("\tSpec type: " + pse.getSpec_type());
					System.out.println("\tComponent id: " + pse.getComponent_id().getValue().getValue());
					System.out.println("\tProd spec: " + new String(pse.getProd_spec()));
				}
				break;
			default:
				System.out.println(">>>>>>>Id not implemented yet");
				break;
			}
		}
	}

	/**
	 * Generate a response for configuration
	 * @param result Reponse configuration
	 * @return
	 */
	private ConfigReportRsp generateConfigReportRsp (int report_id, int result) {
		ConfigReportRsp configRsp = new ConfigReportRsp();
		ConfigId confId = new ConfigId (new Integer(report_id));
		ConfigResult confResult = new ConfigResult(new Integer(result));
		configRsp.setConfig_report_id(confId);
		configRsp.setConfig_result(confResult);
		return configRsp;
	}

	public void GET () {
		HANDLE handle = new HANDLE();
		handle.setValue(new INT_U16(0));
		DataApdu data = MessageFactory.PrstRoivCmpGet(this, handle);
		ApduType apdu = MessageFactory.composeApdu(data, getDeviceConf());

		try{
			InvokeIDType invokeId = data.getInvoke_id();
			getStateHandler().send(apdu);
			DimTimeOut to = new DimTimeOut(TimeOut.MDS_TO_GET, invokeId.getValue(), getStateHandler()) {

				@Override
				public void procResponse(DataApdu data) {
					System.out.println("Received response for get MDS");

					if (!data.getMessage().isRors_cmip_getSelected()) {
						//TODO: Unexpected response format
						System.out.println("TODO: Unexpected response format");
						return;
					}

					GetResultSimple grs = data.getMessage().getRors_cmip_get();

					if (grs.getObj_handle().getValue().getValue() != 0) {
						//TODO: Unexpected object handle, should be reserved value 0
						System.out.println("TODO: Unexpected object handle, should be reserved value 0");
						return;
					}

					try {
						Hashtable<Integer, Attribute> attribs;
						attribs = getAttributes(grs.getAttribute_list(), getDeviceConf().getEncondigRules());
						checkGotAttributes(attribs);
						addCheckedAttributes(attribs);

						/* Store received configuration */
						byte[] sys_id = (byte[]) getAttribute(Nomenclature.MDC_ATTR_SYS_ID).getAttributeType();
						storeConfiguration(sys_id, getDeviceConf());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			to.start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void MDS_DATA_REQUEST() {
		// TODO Auto-generated method stub
		System.out.println("TODO: Implement MDS_DATA_REQUEST");
	}

	@Override
	public void Set_Time() {
		// TODO Auto-generated method stub
		System.out.println("TODO: Implement Set_Time");
	}

	@Override
	public void MDS_Dynamic_Data_Update_MP_Fixed(ScanReportInfoMPFixed info) {
		// TODO Auto-generated method stub
		System.out.println("TODO: Implement MDS_Dynamic_Data_Update_MP_Fixed");
	}

	@Override
	public void MDS_Dynamic_Data_Update_MP_Var(ScanReportInfoMPVar info) {
		// TODO Auto-generated method stub
		System.out.println("TODO: Implement MDS_Dynamic_Data_Update_MP_Var");
	}
}
