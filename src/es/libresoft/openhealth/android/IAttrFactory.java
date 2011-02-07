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

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Parcelable;
import es.libresoft.openhealth.android.aidl.types.IAbsoluteTime;
import es.libresoft.openhealth.android.aidl.types.IAbsoluteTimeAdjust;
import es.libresoft.openhealth.android.aidl.types.IAttribute;
import es.libresoft.openhealth.android.aidl.types.IAttrValMap;
import es.libresoft.openhealth.android.aidl.types.IAttrValMapEntry;
import es.libresoft.openhealth.android.aidl.types.IBITSTRING;
import es.libresoft.openhealth.android.aidl.types.IConfigId;
import es.libresoft.openhealth.android.aidl.types.IHANDLE;
import es.libresoft.openhealth.android.aidl.types.IHighResRelativeTime;
import es.libresoft.openhealth.android.aidl.types.IINT_U16;
import es.libresoft.openhealth.android.aidl.types.IMdsTimeCapState;
import es.libresoft.openhealth.android.aidl.types.IMdsTimeInfo;
import es.libresoft.openhealth.android.aidl.types.INomPartition;
import es.libresoft.openhealth.android.aidl.types.IOCTETSTRING;
import es.libresoft.openhealth.android.aidl.types.IOID_Type;
import es.libresoft.openhealth.android.aidl.types.IPowerStatus;
import es.libresoft.openhealth.android.aidl.types.IPrivateOid;
import es.libresoft.openhealth.android.aidl.types.IProductionSpec;
import es.libresoft.openhealth.android.aidl.types.IProductionSpecEntry;
import es.libresoft.openhealth.android.aidl.types.IRelativeTime;
import es.libresoft.openhealth.android.aidl.types.ISystemModel;
import es.libresoft.openhealth.android.aidl.types.ITYPE;
import ieee_11073.part_20601.asn1.AbsoluteTime;
import ieee_11073.part_20601.asn1.AbsoluteTimeAdjust;
import ieee_11073.part_20601.asn1.AttrValMap;
import ieee_11073.part_20601.asn1.AttrValMapEntry;
import ieee_11073.part_20601.asn1.ConfigId;
import ieee_11073.part_20601.asn1.HANDLE;
import ieee_11073.part_20601.asn1.HighResRelativeTime;
import ieee_11073.part_20601.asn1.INT_U16;
import ieee_11073.part_20601.asn1.MdsTimeInfo;
import ieee_11073.part_20601.asn1.PowerStatus;
import ieee_11073.part_20601.asn1.ProdSpecEntry;
import ieee_11073.part_20601.asn1.ProductionSpec;
import ieee_11073.part_20601.asn1.RelativeTime;
import ieee_11073.part_20601.asn1.SystemModel;
import ieee_11073.part_20601.asn1.TYPE;


public class IAttrFactory {

	private static IHANDLE HANDLE2parcelable(HANDLE handle) {
		IHANDLE ihandle = new IHANDLE(handle.getValue().getValue());
		return ihandle;
	}

	private static ITYPE TYPE2parcelable(TYPE type) {
		INomPartition partition = new INomPartition(type.getPartition().getValue());
		IOID_Type code = new IOID_Type(type.getCode().getValue().getValue());
		ITYPE itype = new ITYPE(partition, code);
		return itype;
	}

	private static Parcelable SystemModel2parcelable(SystemModel model) {
		return new ISystemModel(new String(model.getManufacturer()), new String(model.getModel_number()));
	}

	private static Parcelable OCTETSTRING2parcelable(byte[] octetString) {
		return new IOCTETSTRING(octetString);
	}

	private static Parcelable ConfigId2parcelable(ConfigId confId) {
		return new IConfigId(confId.getValue());
	}

	private static Parcelable AttrValMap2parcelable(AttrValMap valMap) {
		ArrayList<IAttrValMapEntry> values = new ArrayList<IAttrValMapEntry>();
		Iterator<AttrValMapEntry> it = valMap.getValue().iterator();
		while (it.hasNext()) {
			AttrValMapEntry entry = it.next();
			values.add(new IAttrValMapEntry(new IOID_Type(entry.getAttribute_id().getValue().getValue()), entry.getAttribute_len()));
		}
		return new IAttrValMap(values);
	}

	private static Parcelable AttrProductionSpec2parcelable(ProductionSpec spec) {
		ArrayList<IProductionSpecEntry> values = new ArrayList<IProductionSpecEntry>();
		Iterator<ProdSpecEntry> it = spec.getValue().iterator();
		while (it.hasNext()) {
			ProdSpecEntry entry = it.next();
			values.add(new IProductionSpecEntry(entry.getSpec_type(),
									new IPrivateOid(entry.getComponent_id().getValue().getValue()),
									new IOCTETSTRING(entry.getProd_spec())));
		}
		return new IProductionSpec(values);
	}

	private static IMdsTimeInfo MdsTimeInfo2parcelable(MdsTimeInfo timeInfo) {
		return new IMdsTimeInfo(new IMdsTimeCapState(new IBITSTRING(timeInfo.getMds_time_cap_state().getValue().getValue(), timeInfo.getMds_time_cap_state().getValue().getTrailBitsCnt())),
				new IOID_Type(timeInfo.getTime_sync_protocol().getValue().getValue().getValue()),
				new IRelativeTime(timeInfo.getTime_sync_accuracy().getValue().getValue()),
				timeInfo.getTime_resolution_abs_time(), timeInfo.getTime_resolution_rel_time(),
				timeInfo.getTime_resolution_high_res_time().getValue());
	}

	private static IAbsoluteTime AbsoluteTime2parcelable(AbsoluteTime absTime) {
		return new IAbsoluteTime(absTime.getCentury().getValue(),
							absTime.getYear().getValue(),
							absTime.getMonth().getValue(),
							absTime.getDay().getValue(),
							absTime.getHour().getValue(),
							absTime.getMinute().getValue(),
							absTime.getSecond().getValue(),
							absTime.getSec_fractions().getValue());
	}

	private static IRelativeTime RelativeTime2parcelable(RelativeTime relTime) {
		return new IRelativeTime(relTime.getValue().getValue());
	}

	private static IHighResRelativeTime HighResRelativeTime2parcelable(HighResRelativeTime relTime) {
		return new IHighResRelativeTime(new IOCTETSTRING(relTime.getValue()));
	}

	private static IAbsoluteTimeAdjust AbsoluteTimeAdjust2parcelable(AbsoluteTimeAdjust absTimeAdj) {
		return new IAbsoluteTimeAdjust(new IOCTETSTRING(absTimeAdj.getValue()));
	}

	private static IPowerStatus PowerStatus2parcelable(PowerStatus powerStatus) {
		return new IPowerStatus(new IBITSTRING(powerStatus.getValue().getValue(), powerStatus.getValue().getTrailBitsCnt()));
	}

	private static IINT_U16 INT_U162parcelable(INT_U16 intu16) {
		return new IINT_U16(intu16.getValue());
	}

	public static final boolean getParcelableAttribute (Object asnAttr, IAttribute attr) {

		Parcelable parcel = null;

		if (attr == null)
			return false;

		if (asnAttr instanceof HANDLE)
			parcel = HANDLE2parcelable((HANDLE) asnAttr);
		else if (asnAttr instanceof TYPE)
			parcel = TYPE2parcelable((TYPE) asnAttr);
		else if (asnAttr instanceof SystemModel)
			parcel = SystemModel2parcelable((SystemModel) asnAttr);
		else if (asnAttr instanceof byte[])
			parcel = OCTETSTRING2parcelable((byte []) asnAttr);
		else if (asnAttr instanceof ConfigId)
			parcel = ConfigId2parcelable((ConfigId) asnAttr);
		else if (asnAttr instanceof AttrValMap)
			parcel = AttrValMap2parcelable((AttrValMap) asnAttr);
		else if (asnAttr instanceof ProductionSpec)
			parcel = AttrProductionSpec2parcelable((ProductionSpec) asnAttr);
		else if (asnAttr instanceof MdsTimeInfo)
			parcel = MdsTimeInfo2parcelable((MdsTimeInfo) asnAttr);
		else if (asnAttr instanceof AbsoluteTime)
			parcel = AbsoluteTime2parcelable((AbsoluteTime) asnAttr);
		else if (asnAttr instanceof RelativeTime)
			parcel = RelativeTime2parcelable((RelativeTime) asnAttr);
		else if (asnAttr instanceof HighResRelativeTime)
			parcel = HighResRelativeTime2parcelable((HighResRelativeTime) asnAttr);
		else if (asnAttr instanceof AbsoluteTimeAdjust)
			parcel = AbsoluteTimeAdjust2parcelable((AbsoluteTimeAdjust) asnAttr);
		else if (asnAttr instanceof PowerStatus)
			parcel = PowerStatus2parcelable((PowerStatus) asnAttr);
		else if (asnAttr instanceof INT_U16)
			parcel = INT_U162parcelable((INT_U16) asnAttr);

		if (parcel != null) {
			attr.setAttr(parcel);
			return true;
		}

		System.err.println("Unknown method provided. Can't create parcelable attribute.");
		return false;
	}

}
