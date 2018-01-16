
package com.arny.myapidemo.weather.models;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    private int all;

	@Override
	public String toString() {
		return Utility.getFields(this);
	}

	public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

}
