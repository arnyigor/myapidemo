package com.arny.myapidemo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import com.arny.arnylib.database.DBObject;
import com.arny.arnylib.database.DBProvider;

import static com.arny.myapidemo.database.DB.*;


public class TestObject implements Parcelable, DBObject {
    private int ID;
    private String title;
    private ArrayList<GoodItem> goodItems;
    public TestObject() {
    }

    private TestObject(Parcel in) {
        ID = in.readInt();
        title = in.readString();
    }

    public static final Creator<TestObject> CREATOR = new Creator<TestObject>() {
        @Override
        public TestObject createFromParcel(Parcel in) {
            return new TestObject(in);
        }

        @Override
        public TestObject[] newArray(int size) {
            return new TestObject[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(title);
    }

    @Override
    public String toString() {
        return "\nid:" + this.getID() + ";title:" + this.getTitle() + ";goodItems:" + getGoodItems();
    }

    @Override
    public ContentValues getObjectValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_KEY_TITLE,getTitle());
        return null;
    }

    @Override
    public boolean dbRemoveObj(Context context) {
        String condition = DB_KEY_ID + "=" + getID();
        return DBProvider.deleteDB(DB_TABLE_TEST, condition, context) > 0;
    }

    @Override
    public boolean dbAddObj(Context context) {
        return DBProvider.insertDB(DB_TABLE_TEST, getObjectValues(), context)>0;
    }

    public boolean dbUpdateObg(Context context) {
        String where = DB_KEY_ID + "=" + getID();
        return DBProvider.updateDB(DB_TABLE_TEST, getObjectValues(), where, context)>0;
    }

    @Override
    public void setCursorValues(Cursor cursor) {

    }

    public ArrayList<GoodItem> getGoodItems() {
        return goodItems;
    }

    public void setGoodItems(ArrayList<GoodItem> goodItems) {
        this.goodItems = goodItems;
    }
}
