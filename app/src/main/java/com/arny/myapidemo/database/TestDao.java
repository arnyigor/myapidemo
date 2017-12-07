package com.arny.myapidemo.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.arny.myapidemo.api.User;
import com.arny.myapidemo.models.Category;
import com.arny.myapidemo.models.TestSubObject;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface TestDao {
    @Query("SELECT * FROM test")
    Flowable<List<TestSubObject>> getListFl();

    @Query("DELETE FROM test WHERE _id=:id")
    int delete(int id);

    @Query("DELETE FROM test")
    int delete();

    @Query("SELECT * FROM test")
    List<TestSubObject> getListTest();

    @Query("SELECT * FROM category")
    List<Category> getCategories();

    @Query("SELECT DISTINCT tbl_name FROM sqlite_master")
    List<String> getDbTables();

    @Query("SELECT * FROM test")
    LiveData<List<TestSubObject>> getListTestLD();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TestSubObject testSubObject);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<User> users);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(TestSubObject testSubObject);

    @Query("SELECT * FROM users WHERE parentId IS :parentId")
    List<User> getUsers(long parentId);
}
