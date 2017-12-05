package com.arny.myapidemo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.arny.arnylib.utils.Utility

@Entity(tableName = "test")
data class Test(@ColumnInfo(name = "title") var title:String? = null) {
    @ColumnInfo(name = "id")
    var guid: String? = null
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null
    @Embedded
    var info: InfoObject? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}