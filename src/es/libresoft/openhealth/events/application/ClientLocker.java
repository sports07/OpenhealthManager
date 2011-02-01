/*
Copyright (C) 2010 GSyC/LibreSoft, Universidad Rey Juan Carlos.

Author: Jose Antonio Santos Cadenas <jcaden@libresoft.es>

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

package es.libresoft.openhealth.events.application;

import java.util.concurrent.Semaphore;

public class ClientLocker {

	private Semaphore sem;
	private Object rspData;
	private String errMsg;
	private boolean processed;

	public ClientLocker() {
		sem = new Semaphore(0, true);
		errMsg = null;
		rspData = null;
		processed = false;
	}

	public void lock() throws InterruptedException {
		sem.acquire();
	}

	public void unlock(Object data, String error) {
		this.rspData = data;
		this.errMsg = error;
		processed = true;
		sem.release();
	}

	public boolean wasError() {
		if (!processed)
			return false;
		else
			return errMsg == null;
	}

	public Object getRspData() {
		return rspData;
	}

	public String getErrMsg() {
		return errMsg;
	}
}