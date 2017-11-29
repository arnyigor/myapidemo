package com.arny.myapidemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.arny.myapidemo.models.TestObject;
@Database(entities = {TestObject.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TestDao getTestDao();
    private static AppDatabase personDatabase;
    private static final Object LOCK = new Object();
    public synchronized static AppDatabase getDb(Context context){
        if(personDatabase == null) {
            synchronized (LOCK) {
                if (personDatabase == null) {
                    personDatabase = Room.databaseBuilder(context, AppDatabase.class, "roomdb").build();
                }
            }
        }
        return personDatabase;
    }

    public static void destroyInstance() {
	    personDatabase = null;
    }
}
