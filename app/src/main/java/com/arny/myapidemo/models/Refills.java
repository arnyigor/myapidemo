package com.arny.myapidemo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.arny.arnylib.database.DBObject;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.Utility;

import static com.arny.myapidemo.database.DB.*;

public class Refills implements Parcelable,DBObject {

    private int dbID;
    private int car;
    private int odometer;
    private double amount;
    private double price;
    private double sum;
    private long date;
    private String comment;
    private int fuel;

    public Refills() {
    }

    protected Refills(Parcel in) {
        dbID = in.readInt();
        car = in.readInt();
        odometer = in.readInt();
        amount = in.readDouble();
        price = in.readDouble();
        sum = in.readDouble();
        date = in.readLong();
        comment = in.readString();
        fuel = in.readInt();
    }

    public static final Creator<Refills> CREATOR = new Creator<Refills>() {
        @Override
        public Refills createFromParcel(Parcel in) {
            return new Refills(in);
        }

        @Override
        public Refills[] newArray(int size) {
            return new Refills[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dbID);
        dest.writeInt(car);
        dest.writeInt(odometer);
        dest.writeDouble(amount);
        dest.writeDouble(price);
        dest.writeDouble(sum);
        dest.writeLong(date);
        dest.writeString(comment);
        dest.writeInt(fuel);
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    @Override
    public ContentValues getObjectValues() {
        return null;
    }

    @Override
    public boolean dbRemoveObj(Context context) {
        String condition = DB_KEY_ID + "=" + getDbID();
        return DBProvider.deleteDB(DB_TABLE_REFILLS, condition, context) > 0;
    }

    @Override
    public boolean dbAddObj(Context context) {
        return DBProvider.insertDB(DB_TABLE_REFILLS, getObjectValues(), context)>0;
    }

    @Override
    public boolean dbUpdateObg(Context context) {
        String where = DB_KEY_ID + "=" + getDbID();
        return DBProvider.updateDB(DB_TABLE_REFILLS, getObjectValues(), where, context)>0;
    }

    @Override
    public void setCursorValues(Cursor cursor) {
        setDbID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DB_KEY_ID))));
        setCar((int) cursor.getLong(cursor.getColumnIndex(DB_KEY_CAR)));
        setComment(cursor.getString(cursor.getColumnIndex(DB_KEY_COMMENT)));
        setOdometer(cursor.getInt(cursor.getColumnIndex(DB_KEY_ODOMETR)));
        setAmount(cursor.getDouble(cursor.getColumnIndex(DB_KEY_AMOUNT)));
        setPrice(cursor.getDouble(cursor.getColumnIndex(DB_KEY_PRICE)));
        setSum(cursor.getDouble(cursor.getColumnIndex(DB_KEY_SUM)));
        long dat = Utility.convertTimeStringToLong(cursor.getString(cursor.getColumnIndex(DB_KEY_DATE)), "yyyy-MM-dd");
        setDate(dat);
        setComment(cursor.getString(cursor.getColumnIndex(DB_KEY_COMMENT)));
        setFuel(cursor.getInt(cursor.getColumnIndex(DB_KEY_FUEL)));
    }

    @Override
    public String toString() {
        return "id = " + getDbID() +
                " car = " + getCar() +
                " comment = " + getComment();
    }
}
