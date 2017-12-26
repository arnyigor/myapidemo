package com.arny.myapidemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.arny.myapidemo.models.User;
import com.arny.myapidemo.models.Category;
import com.arny.myapidemo.models.GoodItem;
import com.arny.myapidemo.models.InfoObject;
import com.arny.myapidemo.models.TestSubObject;

@Database(entities = {TestSubObject.class, InfoObject.class, User.class, Category.class, GoodItem.class}, version = 2)
@TypeConverters({DbTypeConverter.class})
public abstract class RoomDB extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static RoomDB sInstance;

    public static synchronized RoomDB getDb(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    Builder<RoomDB> roomdb = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "Room.db");
                    roomdb.fallbackToDestructiveMigration();
                    sInstance = roomdb.build();
                }
            }
        }
        return sInstance;
    }

    abstract public TestDao getTestDao();
    abstract public ShopDao getShopDao();


}
