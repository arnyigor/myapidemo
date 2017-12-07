package com.arny.myapidemo.models

import android.arch.persistence.room.*
import com.arny.arnylib.utils.Utility
import com.arny.myapidemo.database.DbTypeConverter

@Entity(tableName = "test")
data class TestSubObject(@ColumnInfo(name = "title") var title:String? = null) {
    @ColumnInfo(name = "id")
    var guid: String? = null
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null
    var info: ArrayList<InfoObject>? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}