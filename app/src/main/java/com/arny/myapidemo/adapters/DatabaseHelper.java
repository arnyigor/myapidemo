package com.arny.myapidemo.adapters;

import android.content.Context;
import android.database.Cursor;

import com.arny.myapidemo.models.TestObject;

import java.util.ArrayList;

import pw.aristos.arnylib.database.DBObjects;
import pw.aristos.arnylib.database.DBProvider;

public class DatabaseHelper implements DBObjects<TestObject> {
    private static final DatabaseHelper instance = new DatabaseHelper();

    public static DatabaseHelper getInstance() {
        return instance;
    }

    public static final String DB_KEY_ID = "_id";
    public static final String DB_KEY_TITLE = "title";
    public static final String DB_TABLE_TEST = "test";

    @Override
    public ArrayList<TestObject> getObjList(Context context, String query, String orderby) {
        return getCursorObjs(DBProvider.selectDB(DB_TABLE_TEST, null, query, orderby, context));
    }

    @Override
    public ArrayList<TestObject> getCursorObjs(Cursor cursor) {
        ArrayList<TestObject> itemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TestObject testObject = new TestObject();
                testObject.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DB_KEY_ID))));
                testObject.setTitle(cursor.getString(cursor.getColumnIndex(DB_KEY_TITLE)));
                itemList.add(testObject);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    @Override
    public TestObject getCursorObject(Cursor cursor, TestObject testObject) {
        if (cursor.moveToFirst()) {
            testObject.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DB_KEY_ID))));
            testObject.setTitle(cursor.getString(cursor.getColumnIndex(DB_KEY_TITLE)));
        }
        cursor.close();
        return testObject;
    }

    @Override
    public TestObject getObject(Context context, String query) {
        return getCursorObject(DBProvider.selectDB(DB_TABLE_TEST, null, query, null, context), new TestObject());
    }

}

