package com.arny.myapidemo.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Funcs {
    private static Funcs instance;
    private Funcs() {
    }
    public static synchronized Funcs getInstance(){
        if (instance == null){
            instance = new Funcs();
        }
        return instance;
    }

    public String getTimeMIN_SEC_MS(long ms){
        return (new SimpleDateFormat("mm:ss:SSS", Locale.getDefault())).format(new Date(ms));
    }

    public String getTimeMIN_SEC(long ms){
        return (new SimpleDateFormat("mm:ss", Locale.getDefault())).format(new Date(ms));
    }

    public String getTimeMIN_SEC(int sec){
        return (new SimpleDateFormat("mm:ss", Locale.getDefault())).format(new Date(sec*1000));
    }



}
