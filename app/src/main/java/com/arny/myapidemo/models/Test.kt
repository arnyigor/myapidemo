package com.arny.myapidemo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.arny.arnylib.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "test")
data class Test(@ColumnInfo(name = "title") var title:String? = null) {
    @ColumnInfo(name = "id")
    var guid: String? = null
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}