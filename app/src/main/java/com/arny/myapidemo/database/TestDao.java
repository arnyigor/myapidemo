package com.arny.myapidemo.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.arny.myapidemo.models.Test;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface TestDao {
    @Query("SELECT * FROM test")
    Flowable<List<Test>> getListFl();

    @Query("DELETE FROM test WHERE _id=:id")
    int delete(int id);

    @Query("DELETE FROM test")
    int delete();

    @Query("SELECT * FROM test")
    List<Test> getListTest();

    @Query("SELECT * FROM test")
    LiveData<List<Test>> getListTestLD();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Test test);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Test test);
}
