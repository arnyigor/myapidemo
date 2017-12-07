package com.arny.myapidemo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.arny.arnylib.utils.Utility

@Entity
data class InfoObject(var name: String? = null) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "info_id")
    var id:Long = 0
    var infoId:Long = 0
    var uid:String? = null
    var type:String? = null
    var size:Long? = null
    constructor() : this("")
    constructor(name:String?=null,uid:String? = null) : this(name){
        this.uid = uid
    }

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
