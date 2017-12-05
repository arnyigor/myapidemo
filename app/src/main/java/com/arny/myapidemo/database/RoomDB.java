package com.arny.myapidemo.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.arny.myapidemo.models.InfoObject;
import com.arny.myapidemo.models.Test;

@Database(entities = {Test.class, InfoObject.class},version = 1)
public abstract class RoomDB extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static RoomDB sInstance;

    public static synchronized RoomDB getDb(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    Builder<RoomDB> roomdb = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "Room.db");
                    if (getMigrations() != null && getMigrations().length != 0) {
                        roomdb.addMigrations(getMigrations());
                    }
                    sInstance = roomdb.build();
                }
            }
        }
        return sInstance;
    }

    @Nullable
    private static Migration[] getMigrations() {
        Migration[] migrations = null;
        migrations = new Migration[2];
//        migrations[0] = new Migration(1, 2) {
//            @Override
//            public void migrate(@NonNull SupportSQLiteDatabase database) {
//                database.beginTransaction();
//                database.execSQL("ALTER TABLE test ADD COLUMN info TEXT");
//                database.endTransaction();
//            }
//        };
//        migrations[1] = new Migration(4, 5) {
//            @Override
//            public void migrate(@NonNull SupportSQLiteDatabase database) {
//                database.beginTransaction();
//                database.execSQL("ALTER TABLE test ADD COLUMN gooditem TEXT");
//                database.endTransaction();
//            }
//        };
        return migrations;
    }

    abstract public TestDao getTestDao();

}
