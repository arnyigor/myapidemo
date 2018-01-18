package com.arny.myapidemo.tracking

import android.location.Location
import com.arny.arnylib.utils.Utility
import java.util.*

open class Position {
    var id: Long = 0
    var deviceId: String? = null
    var time: Date? = null
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var altitude: Double = 0.toDouble()
    var speed: Double = 0.toDouble()
    var course: Double = 0.toDouble()
    var battery: Double = 0.toDouble()

    constructor() {}

    constructor(location: Location, battery: Double) {
        this.deviceId = deviceId
        time = Date(location.time)
        latitude = location.latitude
        longitude = location.longitude
        altitude = location.altitude
        speed = location.speed.toDouble()
        course = location.bearing.toDouble()
        this.battery = battery
    }

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
