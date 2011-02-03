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

import android.os.Parcel;
import android.os.Parcelable;

public class AndroidAttribute implements Parcelable {

	private int type; /* Id type */
	private int code; /* value */

	public static final Parcelable.Creator<AndroidAttribute> CREATOR =
			new Parcelable.Creator<AndroidAttribute>() {
	    public AndroidAttribute createFromParcel(Parcel in) {
	        return new AndroidAttribute(in);
	    }

	    public AndroidAttribute[] newArray(int size) {
	        return new AndroidAttribute[size];
	    }
	};

	private AndroidAttribute (Parcel in){
		type = in.readInt();
		code = in.readInt();
	}

	public AndroidAttribute (int type, int code){
		this.type = type;
		this.code = code;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(type);
		dest.writeInt(code);
	}

	public int getAttrId () {
		return this.type;
	}

	public int getCode () {
		return this.code;
	}
}
