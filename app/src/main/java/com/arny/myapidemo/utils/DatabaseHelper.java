package com.arny.myapidemo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "LOG_TAG";
    // Database Info
    private static final String DATABASE_NAME = "MyApiDemo";
    private static final int DATABASE_VERSION = 1;
    // Table Names
    private static final String TABLE_NAME = "demo_table";
    // Table Columns
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        Log.i(TAG, "onConfigure: ");
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: db");
        String create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"// Define a primary key
                + " , " + KEY_TITLE + " TEXT"
                + ")";
        Log.i(TAG, "onCreate: create_table = " + create_table);
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    // update
    public boolean updateObj(TestObject testObject) {
        Log.i(TAG, "updateObj: testObject = " + testObject.toString());
        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, testObject.getTitle());
            String where = String.format("%s=%s",KEY_ID,testObject.getID());
            Log.i(TAG, "updateObj: where = " +  where);
            db.beginTransaction();
            int updCount = 0;
            try {
                updCount = db.update(TABLE_NAME, values, where, null);
                Log.i(TAG, "updateObj: updates rows count = " +  updCount);
                db.setTransactionSuccessful();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                db.endTransaction();
            }
        return updCount > 0;
    }

    //Delete
    public boolean removeObj(int id) {
        String condition = KEY_ID + "=" + id;
        Log.i(TAG, "removeObj: condition = " + condition);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_NAME, condition, null) > 0;
    }

    // Insert into the database
    public long addObj(TestObject testObject) {
        Log.i(TAG, "addObj: item = " + testObject.toString());
        long itemId = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, testObject.getTitle());
            itemId = db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error addFolder");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return itemId;
    }

    //Get
    public ArrayList<TestObject> getObjList() {
        Log.i(TAG, "getItemsList: ");
        String selectQuery = String.format("SELECT * FROM %s ORDER BY %s ASC", TABLE_NAME, KEY_ID);
        Log.i(TAG, "getItemsList: selectQuery = " + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<TestObject> itemList = getItemsLists(cursor);
        Log.i(TAG, "getFoldersList: itemList size= " + itemList.size());
        cursor.close();
        return itemList;
    }

    //Get
    public ArrayList<TestObject> getItemById(int id) {
        String selectQuery = String.format("SELECT * FROM %s WHERE %s=%s", TABLE_NAME, KEY_ID, id);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<TestObject> objList = getItemsLists(cursor);
        Log.i(TAG, "getObjList: objList size= " + objList.size());
        cursor.close();
        return objList;
    }

    private ArrayList<TestObject> getItemsLists(Cursor cursor) {
        Log.i(TAG, "getItemsLists: ");
        ArrayList<TestObject> itemList = new ArrayList<>();
        Log.i(TAG, "getItemsLists: cursor.getCount() = " + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                TestObject testObject = new TestObject();
                testObject.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                testObject.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                Log.i(TAG, "getObjLists: list = " + testObject.toString());
                itemList.add(testObject);
            } while (cursor.moveToNext());
        }
        return itemList;
    }

}

