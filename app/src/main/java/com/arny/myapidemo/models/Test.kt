package com.arny.myapidemo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.arny.arnylib.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "test")
data class Test(@SerializedName("index") @Expose @ColumnInfo(name = "index") var index:Int? = null) {
    @SerializedName("isActive")
    @Expose
    @ColumnInfo(name = "active")
    var isActive: Boolean? = null
    @SerializedName("guid")
    @Expose
    @ColumnInfo(name = "guid")
    var guid: String? = null
    @SerializedName("_id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}