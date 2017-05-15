package com.arny.myapidemo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.arny.arnylib.database.DBObject;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.Utility;

import static com.arny.myapidemo.database.DB.*;

public class CarFuel implements Parcelable,DBObject,Comparable  {

    private int dbID;
    private long date;
    private long id_vehicle;
    private String note;
    private int mileage;
    private double volume;
    private double volumecost;
    private double cost;
    private String type;
    private int mark;
    private int mileageadd;
    private int current_tank;
    private double tankvolume;
    private double volumemileage;
    private double costmileage;
    private double volumemileage_1;
    private double costmileage_1;
    private double mil_coef;
    private double consumption_comp_0;
    private double consumption_comp_1;
    private int missed;

    protected CarFuel(Parcel in) {
        dbID = in.readInt();
        date = in.readLong();
        id_vehicle = in.readLong();
        note = in.readString();
        mileage = in.readInt();
        volume = in.readDouble();
        volumecost = in.readDouble();
        cost = in.readDouble();
        type = in.readString();
        mark = in.readInt();
        mileageadd = in.readInt();
        current_tank = in.readInt();
        tankvolume = in.readDouble();
        volumemileage = in.readDouble();
        costmileage = in.readDouble();
        volumemileage_1 = in.readDouble();
        costmileage_1 = in.readDouble();
        mil_coef = in.readDouble();
        consumption_comp_0 = in.readDouble();
        consumption_comp_1 = in.readDouble();
        missed = in.readInt();
    }

    public CarFuel() {
    }

    public static final Creator<CarFuel> CREATOR = new Creator<CarFuel>() {
        @Override
        public CarFuel createFromParcel(Parcel in) {
            return new CarFuel(in);
        }

        @Override
        public CarFuel[] newArray(int size) {
            return new CarFuel[size];
        }
    };

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId_vehicle() {
        return id_vehicle;
    }

    public void setId_vehicle(long id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolumecost() {
        return volumecost;
    }

    public void setVolumecost(double volumecost) {
        this.volumecost = volumecost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMileageadd() {
        return mileageadd;
    }

    public void setMileageadd(int mileageadd) {
        this.mileageadd = mileageadd;
    }

    public int getCurrent_tank() {
        return current_tank;
    }

    public void setCurrent_tank(int current_tank) {
        this.current_tank = current_tank;
    }

    public double getTankvolume() {
        return tankvolume;
    }

    public void setTankvolume(double tankvolume) {
        this.tankvolume = tankvolume;
    }

    public double getVolumemileage() {
        return volumemileage;
    }

    public void setVolumemileage(double volumemileage) {
        this.volumemileage = volumemileage;
    }

    public double getCostmileage() {
        return costmileage;
    }

    public void setCostmileage(double costmileage) {
        this.costmileage = costmileage;
    }

    public double getVolumemileage_1() {
        return volumemileage_1;
    }

    public void setVolumemileage_1(double volumemileage_1) {
        this.volumemileage_1 = volumemileage_1;
    }

    public double getCostmileage_1() {
        return costmileage_1;
    }

    public void setCostmileage_1(double costmileage_1) {
        this.costmileage_1 = costmileage_1;
    }

    public double getMil_coef() {
        return mil_coef;
    }

    public void setMil_coef(double mil_coef) {
        this.mil_coef = mil_coef;
    }

    public double getConsumption_comp_0() {
        return consumption_comp_0;
    }

    public void setConsumption_comp_0(double consumption_comp_0) {
        this.consumption_comp_0 = consumption_comp_0;
    }

    public double getConsumption_comp_1() {
        return consumption_comp_1;
    }

    public void setConsumption_comp_1(double consumption_comp_1) {
        this.consumption_comp_1 = consumption_comp_1;
    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dbID);
        dest.writeLong(date);
        dest.writeLong(id_vehicle);
        dest.writeString(note);
        dest.writeInt(mileage);
        dest.writeDouble(volume);
        dest.writeDouble(volumecost);
        dest.writeDouble(cost);
        dest.writeString(type);
        dest.writeInt(mark);
        dest.writeInt(mileageadd);
        dest.writeInt(current_tank);
        dest.writeDouble(tankvolume);
        dest.writeDouble(volumemileage);
        dest.writeDouble(costmileage);
        dest.writeDouble(volumemileage_1);
        dest.writeDouble(costmileage_1);
        dest.writeDouble(mil_coef);
        dest.writeDouble(consumption_comp_0);
        dest.writeDouble(consumption_comp_1);
        dest.writeInt(missed);
    }

    @Override
    public ContentValues getObjectValues() {
        ContentValues contentValues = new ContentValues();
        int strDate = Integer.parseInt(Utility.getDateTime(getDate(), "yyyyMMdd"));
        contentValues.put(DB_KEY_DATE,strDate);
        contentValues.put(DB_KEY_ID_VEHICLE,getId_vehicle());
        contentValues.put(DB_KEY_NOTE,getNote());
        contentValues.put(DB_KEY_MILEAGE,getMileage());
        contentValues.put(DB_KEY_VOLUME,getVolume());
        contentValues.put(DB_KEY_VOLUME_COST,getVolumecost());
        contentValues.put(DB_KEY_COST,getCost());
        contentValues.put(DB_KEY_TYPE,getType());
        contentValues.put(DB_KEY_MARK,getMark());
        contentValues.put(DB_KEY_MILEAGEADD,getMileageadd());
        contentValues.put(DB_KEY_CURRENT_TANK,getCurrent_tank());
        contentValues.put(DB_KEY_TANKVOLUME,getTankvolume());
        contentValues.put(DB_KEY_VOLUMEMILEAGE,getVolumemileage());
        contentValues.put(DB_KEY_COSTMILEAGE,getCostmileage());
        contentValues.put(DB_KEY_VOLUMEMILEAGE_1,getVolumemileage_1());
        contentValues.put(DB_KEY_COSTMILEAGE_1,getCostmileage_1());
        contentValues.put(DB_KEY_MIL_COEF,getMil_coef());
        contentValues.put(DB_KEY_CONSUMPTION_COMP_0,getConsumption_comp_0());
        contentValues.put(DB_KEY_CONSUMPTION_COMP_1,getConsumption_comp_1());
        contentValues.put(DB_KEY_MISSED,getMissed());
        return contentValues;
    }

    @Override
    public boolean dbRemoveObj(Context context) {
        String condition = DB_KEY_ID + "=" + getDbID();
        return DBProvider.deleteDB(DB_TABLE_CAR_FUEL, condition, context) > 0;
    }

    @Override
    public boolean dbAddObj(Context context) {
        return DBProvider.insertDB(DB_TABLE_CAR_FUEL, getObjectValues(), context)>0;
    }

    @Override
    public boolean dbUpdateObg(Context context) {
        String where = DB_KEY_ID + "=" + getDbID();
        return DBProvider.updateDB(DB_TABLE_CAR_FUEL, getObjectValues(), where, context)>0;
    }

    @Override
    public void setCursorValues(Cursor cursor) {
        setDbID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DB_KEY_ID))));
        String ceDate = String.valueOf(cursor.getInt(cursor.getColumnIndex(DB_KEY_DATE)));
        setDate(Utility.convertTimeStringToLong(ceDate, "yyyyMMdd"));
        setId_vehicle(cursor.getLong(cursor.getColumnIndex(DB_KEY_ID_VEHICLE)));
        setNote(cursor.getString(cursor.getColumnIndex(DB_KEY_NOTE)));
        setMileage(cursor.getInt(cursor.getColumnIndex(DB_KEY_MILEAGE)));
        setVolume(cursor.getDouble(cursor.getColumnIndex(DB_KEY_VOLUME)));
        setVolumecost(cursor.getDouble(cursor.getColumnIndex(DB_KEY_VOLUME_COST)));
        setCost(cursor.getDouble(cursor.getColumnIndex(DB_KEY_COST)));
        setType(cursor.getString(cursor.getColumnIndex(DB_KEY_TYPE)));
        setMark(cursor.getInt(cursor.getColumnIndex(DB_KEY_MARK)));
        setMileageadd(cursor.getInt(cursor.getColumnIndex(DB_KEY_MILEAGEADD)));
        setCurrent_tank(cursor.getInt(cursor.getColumnIndex(DB_KEY_CURRENT_TANK)));
        setTankvolume(cursor.getDouble(cursor.getColumnIndex(DB_KEY_TANKVOLUME)));
        setVolumemileage(cursor.getDouble(cursor.getColumnIndex(DB_KEY_VOLUMEMILEAGE)));
        setCostmileage(cursor.getDouble(cursor.getColumnIndex(DB_KEY_COSTMILEAGE)));
        setVolumemileage_1(cursor.getDouble(cursor.getColumnIndex(DB_KEY_VOLUMEMILEAGE_1)));
        setCostmileage_1(cursor.getDouble(cursor.getColumnIndex(DB_KEY_COSTMILEAGE_1)));
        setMil_coef(cursor.getDouble(cursor.getColumnIndex(DB_KEY_MIL_COEF)));
        setConsumption_comp_0(cursor.getDouble(cursor.getColumnIndex(DB_KEY_CONSUMPTION_COMP_0)));
        setConsumption_comp_1(cursor.getDouble(cursor.getColumnIndex(DB_KEY_CONSUMPTION_COMP_1)));
        setMissed(cursor.getInt(cursor.getColumnIndex(DB_KEY_MISSED)));
    }

    @Override
    public String toString() {
        return
                "odomentr = " + getMileage() +
                " odomentr added = " + getMileageadd() +
                " volume 100 km = " + getVolumemileage() +
                " cost 1 km = " + getCostmileage() +
                " date = " + Utility.getDateTime(getDate());
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof CarFuel) {
            CarFuel obj = (CarFuel) o;
            if ((this.getMileage() - obj.getMileage()<0)) {
                return -1;
            }else{
                return 1;
            }
        }
        return 0;
    }
}
