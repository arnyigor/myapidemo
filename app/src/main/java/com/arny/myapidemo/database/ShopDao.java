package com.arny.myapidemo.database;

import android.arch.persistence.room.*;
import com.arny.myapidemo.models.Category;
import com.arny.myapidemo.models.GoodItem;
import com.arny.myapidemo.models.TestSubObject;
import com.arny.myapidemo.models.User;

import java.util.List;

@Dao
public interface ShopDao {

    @Query("SELECT name FROM sqlite_master WHERE type='table'")
    String getSchema();

    @Query("SELECT DISTINCT tbl_name FROM sqlite_master")
    List<String> getDbTables();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Delete()
    void delete(User user);

    @Query("SELECT _id,login,name,admin,email,avatar,pass FROM users WHERE _id IS :id")
    User getUser(long id);

    @Query("SELECT _id,login,name,admin,email,avatar,pass FROM users ORDER BY login")
    List<User> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Category category);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Category category);

    @Delete()
    void delete(Category category);

    @Query("SELECT _id,title,image,description,parentId FROM category WHERE _id IS :id")
    Category getCategory(long id);

    @Query("SELECT _id,title,image,description,parentId FROM category ORDER BY title")
    List<Category> getCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(GoodItem goodItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(GoodItem goodItem);

    @Delete()
    void delete(GoodItem goodItem);

    @Query("SELECT _id,title,image,description,parentId FROM gooditem WHERE _id IS :id")
    GoodItem getGoodItem(long id);

    @Query("SELECT _id,title,image,description,parentId,price FROM gooditem ORDER BY title")
    List<GoodItem> getGoodItems();
}
