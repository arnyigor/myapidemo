package com.arny.myapidemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.Nullable;
import com.arny.arnylib.database.DBProvider;
import com.arny.myapidemo.api.User;
import com.arny.myapidemo.models.Category;
import com.arny.myapidemo.models.InfoObject;
import com.arny.myapidemo.models.TestSubObject;

import java.util.ArrayList;

@Database(entities = {TestSubObject.class, InfoObject.class, User.class, Category.class},version = 2)
@TypeConverters({DbTypeConverter.class})
public abstract class RoomDB extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static RoomDB sInstance;

    public static synchronized RoomDB getDb(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    Builder<RoomDB> roomdb = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "Room.db");
                    Migration[] roomMigrations = DBProvider.getRoomMigrations(context.getApplicationContext());
//                    Migration[] roomMigrations = getMigrations();
                    if (roomMigrations != null && roomMigrations.length != 0) {
                        roomdb.addMigrations(roomMigrations);
                    }
                    roomdb.fallbackToDestructiveMigration();
                    sInstance = roomdb.build();
                }
            }
        }
        return sInstance;
    }

    abstract public TestDao getTestDao();


    @Nullable
    private static Migration[] getMigrations() {
        ArrayList<Migration> migrationArrayList=new ArrayList<>();
        migrationArrayList.add(DBProvider.addRoomMigration(1, 2, "CREATE TABLE IF NOT EXISTS `test` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT, `id` TEXT, `title` TEXT)"));
        migrationArrayList.add(DBProvider.addRoomMigration(1, 2, "CREATE TABLE IF NOT EXISTS `users` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `login` TEXT, `name` TEXT, `parentId` INTEGER  NOT NULL, FOREIGN KEY(`parentid`) REFERENCES test(_id))"));
        migrationArrayList.add(DBProvider.addRoomMigration(1, 2, "CREATE TABLE `category` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cat_id` INTEGER, `name` TEXT)"));
        Migration[] migrations = new Migration[migrationArrayList.size()];
        for (int i = 0; i < migrationArrayList.size(); i++) {
            migrations[i] = migrationArrayList.get(i);
        }
        return migrations;
    }


}
