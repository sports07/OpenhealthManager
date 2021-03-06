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

package es.libresoft.openhealth.events;

import java.util.List;

import es.libresoft.openhealth.Agent;

public interface InternalEventManager {
	/**
	 * Agent event to indicate that new measure has been received from agent
	 * @param value
	 * @param date
	 */
	public void receivedMeasure(String system_id, MeasureReporter mr);

	/**
	 * Agent event to indicate that the agents has changed is state
	 * @param system_id
	 * @param state
	 */
	public void agentChangeStatus(String system_id, String state);

	/**
	 * Send a manager event to indicate that new agent has connected
	 * @param agent The agent device connected
	 */
	public void agentConnected(Agent agent);
	/**
	 * Send a manager event to indicate a disconnection of some agent
	 * @param system_id the system id of the agent
	 */
	public void agentDisconnected(String system_id);
}
