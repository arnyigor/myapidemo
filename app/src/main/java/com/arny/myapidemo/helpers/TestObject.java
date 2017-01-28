package com.arny.myapidemo.helpers;

import android.os.Parcel;
import android.os.Parcelable;

public class TestObject implements Parcelable {
    private int ID;
    private String title;
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
        return "\nid=" + this.getID() + "; title=" + this.getTitle();
    }
}
