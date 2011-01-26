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
package ieee_11073.part_20601.fsm.manager;

import es.libresoft.openhealth.events.Event;
import es.libresoft.openhealth.events.EventType;
import ieee_11073.part_20601.asn1.ApduType;
import ieee_11073.part_20601.fsm.Disconnected;
import ieee_11073.part_20601.fsm.StateHandler;

public final class MDisconnected extends Disconnected {

	public MDisconnected(StateHandler handler) {
		super(handler);
	}

	@Override
	public synchronized void process(ApduType apdu) {
		//Do not process anything in Disconnected state.
	}

	@Override
	public synchronized boolean processEvent(Event event) {
		if (event.getTypeOfEvent() == EventType.IND_TRANS_CONN)
			state_handler.changeState(new MUnassociated(state_handler));
		else {
			System.out.println("Warning: unexpected event (" + event.getTypeOfEvent() + ") arrived in disconnected state.");
			return false;
		}
		return true;
	}

}