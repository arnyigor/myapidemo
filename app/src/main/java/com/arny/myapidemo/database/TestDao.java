package com.arny.myapidemo.database;

import android.arch.persistence.room.*;
import com.arny.myapidemo.models.TestObject;
import io.reactivex.Flowable;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
@Dao
public interface TestDao {
		@Insert(onConflict = REPLACE)
		long insert(TestObject testObject);
		@Update
		void update(TestObject object);
		@Query("SELECT * FROM test WHERE _id=:id")
		TestObject getObjectById(String id);
		@Query("SELECT * FROM test")
		Flowable<List<TestObject>> getObjects();
		@Query("DELETE FROM test WHERE _id=:id")
		int delete(String id);
		@Query("DELETE FROM test")
		int deleteAll();
}
