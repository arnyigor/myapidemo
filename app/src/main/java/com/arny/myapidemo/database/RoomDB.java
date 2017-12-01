package com.arny.myapidemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.arny.myapidemo.models.Test;

@Database(entities = {Test.class},version = 2)
public abstract class RoomDB extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static RoomDB sInstance;
    public static synchronized RoomDB getDb(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "roomdb").build();
                }
            }
        }
        return sInstance;
    }
    abstract public TestDao getTestDao();

}
