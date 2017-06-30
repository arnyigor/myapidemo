package com.arny.myapidemo.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.arny.arnylib.database.DBObject;

public class Refills extends DBObject implements Parcelable {

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
    public String toString() {
        return "id = " + getDbID() +
                " car = " + getCar() +
                " comment = " + getComment();
    }
}
