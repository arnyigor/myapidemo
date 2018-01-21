package com.arny.myapidemo.weather.models

import com.arny.arnylib.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Clouds {

    @SerializedName("all")
    @Expose
    var all: Int = 0

    override fun toString(): String {
        return Utility.getFields(this)
    }

}
