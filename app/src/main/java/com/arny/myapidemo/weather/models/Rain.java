
package com.arny.myapidemo.weather.models;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    @Expose
    private double volume;

	@Override
	public String toString() {
		return Utility.getFields(this);
	}

	public double getVolume() {
        return volume;
    }

}
