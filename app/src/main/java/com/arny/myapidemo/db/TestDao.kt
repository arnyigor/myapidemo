package com.arny.myapidemo.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.arny.myapidemo.models.TestObject
import io.reactivex.Flowable

@Dao
interface TestDao {
    @Insert(onConflict = REPLACE)
    fun insert(testObject: TestObject)
    @Delete
    fun deleteAll(testObject: List<TestObject>)
    @Update
    fun update(testObject: TestObject)
    @Query("SELECT * FROM test WHERE _id=:id")
    fun getObjectById(id: String):TestObject
    @Query("SELECT * FROM test WHERE name=:name")
    fun getObjectsByName(name: String):Flowable<TestObject>
    @Query("DELETE FROM test WHERE _id=:id")
    fun delete(id:String):Int

}
