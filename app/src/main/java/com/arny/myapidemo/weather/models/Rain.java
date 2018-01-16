
package com.arny.myapidemo.weather.models;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    @Expose
    private int _3h;

	@Override
	public String toString() {
		return Utility.getFields(this);
	}

	public int get3h() {
        return _3h;
    }

    public void set3h(int _3h) {
        this._3h = _3h;
    }

}
