package com.arny.myapidemo.models

import android.arch.persistence.room.Entity
import com.arny.arnylib.utils.Utility
import org.chalup.microorm.annotations.Column
import java.io.Serializable

@Entity
open class TestObject(@Column("id") var id: String? = null, @Column("title") var name: String? = null) : Serializable {

    @Column("_id")
    var dbId: Long? = null
    constructor() : this(null, null)
    override fun toString(): String {
        return Utility.getFields(this)
    }
}
