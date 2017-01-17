package com.arny.myapidemo.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MyLoader extends AsyncTaskLoader<String> {

    public static final String EXTRA_LOADER_STRING = "EXTRA_STRING";
    private static final String TAG = "LOG_TAG";

    public MyLoader(Context context,Bundle bundle) {
        super(context);
        if (bundle !=null){
            Log.i(TAG, "MyLoader: bundle = " + bundle.toString());
        }
        Log.i(TAG, "MyLoader: context = " + context);
    }

    @Override
    public String loadInBackground() {
        Log.i(TAG, hashCode() + " loadInBackground start");
        return getStringInbackground();
    }


    private String getStringInbackground() {
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.SSS", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public void forceLoad() {
        Log.i(TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.i(TAG, "onStopLoading");
    }

    @Override
    public void deliverResult(String data) {
        Log.i(TAG, "deliverResult");
        super.deliverResult(data);
    }
}