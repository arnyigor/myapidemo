package com.arny.myapidemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.arny.myapidemo.api.jsongenerator.Place;
import com.arny.myapidemo.models.*;

@Database(entities = {TestSubObject.class, InfoObject.class, User.class, Category.class, GoodItem.class, Place.class}, version = 1, exportSchema = false)
@TypeConverters({DbTypeConverter.class})
public abstract class RoomDB extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static volatile RoomDB sInstance;

    public static RoomDB getDb(Context context) {
        RoomDB localInstance = sInstance;
        if (localInstance == null) {
            synchronized (LOCK) {
                localInstance = sInstance;
                if (localInstance == null) {
                    Builder<RoomDB> roomdb = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "Room.db");
                    roomdb.fallbackToDestructiveMigration();
                    sInstance = localInstance = roomdb.build();
                }
            }
        }
        return localInstance;
    }

    abstract public TestDao getTestDao();
    abstract public ShopDao getShopDao();
    abstract public PlacesDao getPlacesDao();


}
