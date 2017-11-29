package com.arny.myapidemo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.arny.arnylib.utils.Utility
import org.chalup.microorm.annotations.Column

import java.io.Serializable
import java.util.ArrayList


@Entity(tableName = "test")
data class TestObject(@ColumnInfo(name = "id") var id: String?, @ColumnInfo(name = "title") var name: String?) : Serializable {
    constructor():this("","")
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var dbId: Long = 0
    override fun toString(): String {
        return Utility.getFields(this)
    }
}
