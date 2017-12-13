package com.arny.myapidemo.database;

import android.arch.persistence.room.TypeConverter;
import com.arny.myapidemo.models.InfoObject;
import com.google.gson.Gson;

import java.util.ArrayList;
public class DbTypeConverter {
    @TypeConverter
    public static String infoToJson(ArrayList<InfoObject> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static ArrayList<InfoObject> jsonToInfo(String list) {
        return (ArrayList<InfoObject>) new Gson().fromJson(list, ArrayList.class);
    }
}
