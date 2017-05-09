package com.arny.myapidemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.arny.myapidemo.models.CarFuel;
import com.arny.myapidemo.models.Refills;

import java.util.ArrayList;
import java.util.Collections;

import pw.aristos.arnylib.database.DBProvider;


public class DB {

    public static final String DB_TABLE_TEST = "test";
    public static final String DB_KEY_TITLE = "title";
    public static final String DB_TABLE_CAR_FUEL = "car_fuel";
    public static final String DB_TABLE_REC = "rec_table";
    public static final String DB_TABLE_REFILLS = "refills";
    public static final String DB_KEY_ID = "_id";
    public static final String DB_KEY_DATE = "date";
    public static final String DB_KEY_ID_VEHICLE = "id_vehicle";
    public static final String DB_KEY_NOTE = "note";
    public static final String DB_KEY_MILEAGE = "mileage";
    public static final String DB_KEY_VOLUME = "volume";
    public static final String DB_KEY_VOLUME_COST = "volumecost";
    public static final String DB_KEY_COST = "cost";
    public static final String DB_KEY_CAR = "car";
    public static final String DB_KEY_ODOMETR = "odometer";
    public static final String DB_KEY_AMOUNT = "amount";
    public static final String DB_KEY_TYPE = "type";
    public static final String DB_KEY_MARK = "mark";
    public static final String DB_KEY_MILEAGEADD = "mileageadd";
    public static final String DB_KEY_CURRENT_TANK = "current_tank";
    public static final String DB_KEY_TANKVOLUME = "tankvolume";
    public static final String DB_KEY_VOLUMEMILEAGE = "volumemileage";
    public static final String DB_KEY_COSTMILEAGE = "costmileage";
    public static final String DB_KEY_VOLUMEMILEAGE_1 = "volumemileage_1";
    public static final String DB_KEY_COSTMILEAGE_1 = "costmileage_1";
    public static final String DB_KEY_MIL_COEF = "mil_coef";
    public static final String DB_KEY_CONSUMPTION_COMP_0 = "consumption_comp_0";
    public static final String DB_KEY_CONSUMPTION_COMP_1 = "consumption_comp_1";
    public static final String DB_KEY_PRICE = "price";
    public static final String DB_KEY_SUM = "sum";
    public static final String DB_KEY_COMMENT = "comment";
    public static final String DB_KEY_FUEL = "fuel";
    public static final String DB_KEY_MISSED = "missed";


    public static  ArrayList<CarFuel> getObjList(Context context, CarFuel carFuel) {
        return getCursorObjs(DBProvider.selectDB(DB_TABLE_REC, null, null, null, context), carFuel);
    }

    public static void testDb(Context context){
        DBProvider.deleteDB(DB_TABLE_CAR_FUEL,null,context);
        ArrayList<CarFuel> cars = getObjList(context, new CarFuel());
        ArrayList<Refills> refillses = getObjList(context, new Refills());
        ArrayList<CarFuel> total = new ArrayList<>();
        total.addAll(cars);
        for (Refills refills : refillses) {
            CarFuel fuel = new CarFuel();
            fuel.setDate(refills.getDate());
            fuel.setId_vehicle(1);
            fuel.setNote(refills.getComment());
            fuel.setMileage(refills.getOdometer());
            fuel.setVolume(refills.getAmount());
            fuel.setVolumecost(refills.getPrice());
            fuel.setCost(refills.getSum());
            fuel.setType("3");
            fuel.setMark(10);
            fuel.setMileageadd(0);
            fuel.setCurrent_tank(0);
            fuel.setTankvolume(0);
            fuel.setVolumemileage(0);
            fuel.setCostmileage(0);
            fuel.setVolumemileage_1(0);
            fuel.setCostmileage_1(0);
            fuel.setMil_coef(1);
            fuel.setConsumption_comp_0(0);
            fuel.setConsumption_comp_1(0);
            fuel.setMissed(0);
            total.add(fuel);
        }
        Log.d(DB.class.getSimpleName(), "testDb: total = " + total.size());
        Collections.sort(total);
        int odom = 0;
        for (CarFuel carFuel : total) {
            if (odom == 0) {
                carFuel.setMileageadd(0);
                carFuel.setCostmileage(0);
                carFuel.setVolumemileage(0);
            }else{
                carFuel.setMileageadd(carFuel.getMileage()-odom);
                double cost1km = carFuel.getCost() / carFuel.getMileageadd();
                double volume100km = (carFuel.getVolume() / carFuel.getMileageadd()) * 100;
                carFuel.setCostmileage(cost1km);
                carFuel.setVolumemileage(volume100km);
            }
            odom = carFuel.getMileage();
            DBProvider.insertDB(DB_TABLE_CAR_FUEL, carFuel.getObjectValues(), context);
        }

    }

    public static ArrayList<Refills> getObjList(Context context, Refills refills) {
        return getCursorObjs(DBProvider.selectDB(DB_TABLE_REFILLS, null, null, null, context), refills);
    }


    public static ArrayList<Refills> getCursorObjs(Cursor cursor, Refills refills) {
        ArrayList<Refills> itemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                refills = new Refills();
                refills.setCursorValues(cursor);
                itemList.add(refills);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public static ArrayList<CarFuel> getCursorObjs(Cursor cursor, CarFuel carFuel) {
        ArrayList<CarFuel> itemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                carFuel = new CarFuel();
                carFuel.setCursorValues(cursor);
                itemList.add(carFuel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public static CarFuel getCursorObject(Cursor cursor, CarFuel carFuel) {
        carFuel = new CarFuel();
        if (cursor.moveToFirst()) {
            carFuel.setCursorValues(cursor);
        }
        return carFuel;
    }

    public static Refills getCursorObject(Cursor cursor, Refills refills) {
        refills = new Refills();
        if (cursor.moveToFirst()) {
            refills.setCursorValues(cursor);
        }
        return refills;
    }

    public static CarFuel getObject(Context context, String where, CarFuel carFuel) {
        return getCursorObject(DBProvider.selectDB(DB_TABLE_CAR_FUEL, null, where, null, context), carFuel);
    }

    public static Refills getObject(Context context, String where, Refills refills) {
        return getCursorObject(DBProvider.selectDB(DB_TABLE_CAR_FUEL, null, where, null, context), refills);
    }

}
