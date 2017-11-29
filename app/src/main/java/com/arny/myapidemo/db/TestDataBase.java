package com.arny.myapidemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.arny.myapidemo.models.TestObject;
@Database(entities = {TestObject.class}, version = 1)
public abstract class TestDataBase extends RoomDatabase {
    public abstract TestDao getTestDao();


    private static TestDataBase personDatabase;
    private static final Object LOCK = new Object();

    public synchronized static TestDataBase getInMemoryDatabase(Context context){
        if(personDatabase == null) {
            synchronized (LOCK) {
                if (personDatabase == null) {
                    personDatabase = Room.databaseBuilder(context,
                            TestDataBase.class, "apidemo").build();
                }
            }
        }
        return personDatabase;
    }
//
//
//
//    private static TestDataBase INSTANCE;
//
//    public static TestDataBase getInMemoryDsatabase(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), TestDataBase.class)
//                            // To simplify the codelab, allow queries on the main thread.
//                            // Don't do this on a real app! See PersistenceBasicSample for an example.
//                            .allowMainThreadQueries()
//                            .build();
//        }
//        return INSTANCE;
//    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
