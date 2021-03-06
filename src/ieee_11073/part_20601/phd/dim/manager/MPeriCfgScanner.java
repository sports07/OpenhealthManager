/*
Copyright (C) 2008-2009  Santiago Carot Nemesio
email: scarot@libresoft.es

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

import java.util.HashMap;
import java.util.Hashtable;

import es.libresoft.openhealth.messages.MessageFactory;
import es.libresoft.openhealth.utils.ASN1_Values;

import ieee_11073.part_20601.asn1.ApduType;
import ieee_11073.part_20601.asn1.DataApdu;
import ieee_11073.part_20601.asn1.EventReportArgumentSimple;
import ieee_11073.part_20601.phd.dim.Attribute;
import ieee_11073.part_20601.phd.dim.InvalidAttributeException;
import ieee_11073.part_20601.phd.dim.PeriCfgScanner;

public class MPeriCfgScanner extends PeriCfgScanner {

	public MPeriCfgScanner(Hashtable<Integer, Attribute> attributeList)
			throws InvalidAttributeException {
		super(attributeList);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void checkAttributes(
			Hashtable<Integer, Attribute> attributes)
			throws InvalidAttributeException {
		// TODO Auto-generated method stub

	}

	@Override
	public void Buf_Scan_Report_Fixed(EventReportArgumentSimple event) {
		// TODO Auto-generated method stub
		System.out.println("TODO: implement Buf_Scan_Report_Fixed");
	}

	@Override
	public void Buf_Scan_Report_Grouped(EventReportArgumentSimple event) {
		// TODO Auto-generated method stub
		System.out.println("TODO: implement Buf_Scan_Report_Grouped");
	}

	@Override
	public void Buf_Scan_Report_MP_Fixed(EventReportArgumentSimple event) {
		// TODO Auto-generated method stub
		System.out.println("TODO: implement Buf_Scan_Report_MP_Fixed");
	}

	@Override
	public void Buf_Scan_Report_MP_Grouped(EventReportArgumentSimple event) {
		// TODO Auto-generated method stub
		System.out.println("TODO: implement Buf_Scan_Report_MP_Grouped");
	}

	@Override
	public void Buf_Scan_Report_MP_Var(EventReportArgumentSimple event) {
		// TODO Auto-generated method stub
		System.out.println("TODO: implement Buf_Scan_Report_MP_Var");
	}

	@Override
	public void Buf_Scan_Report_Var(EventReportArgumentSimple event) {
		// TODO Auto-generated method stub
		System.out.println("TODO: implement Buf_Scan_Report_Var");
	}

	@Override
	public void SET(Attribute attr) {
		HashMap<Attribute, Integer> attrs = new HashMap<Attribute, Integer>();

		attrs.put(attr, ASN1_Values.MOD_OP_REPLACE);
		DataApdu data = MessageFactory.PrstRoivCmipSet(this, attrs);
		ApduType apdu = MessageFactory.composeApdu(data, getMDS().getDeviceConf());
		getMDS().getStateHandler().send(apdu);
		addAttribute(attr);
	}

}
