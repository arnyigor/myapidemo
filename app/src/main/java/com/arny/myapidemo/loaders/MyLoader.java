package com.arny.myapidemo.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.arny.myapidemo.helpers.Funcs;

import java.util.Locale;


public class MyLoader extends AsyncTaskLoader<String> {

    public static final String TAG = "LOG_TAG";
    public static final String ARG_WORD = "word";
    public static final String RESULT = "Completed";

    public MyLoader(Context context) {
        super(context);
        Log.i(TAG, "MyLoader: context = " + context);
    }

    @Override
    public String loadInBackground() {
        Log.i(TAG, "loadInBackground");
        return generateString();
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


    private String generateString()
    {
        long start = System.currentTimeMillis();
        Log.i(TAG, "generateString: start = " + start);
        int cnt = Funcs.getInstance().randInt(0,50);
        Log.i(TAG, "generateString: random = " + cnt);
        for (int i = 0; i <cnt ; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "generateString: i = " + i);
        }
        long finish = System.currentTimeMillis();
        Log.i(TAG, "generateString: finish = " + finish);
        long res = (finish - start)/1000;
        Log.i(TAG, "generateString: res = " + res);
        return RESULT + String.format(Locale.getDefault()," %.3d сек",res);
    }
}