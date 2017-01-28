package com.arny.myapidemo.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.arny.myapidemo.helpers.Funcs;


public class MyLoader extends AsyncTaskLoader<String> {

    public static final String EXTRA_LOADER_STRING = "EXTRA_STRING";
    private static final String TAG = "LOG_TAG";
    private  String  mData;

    public MyLoader(Context context,Bundle bundle) {
        super(context);
        if (bundle !=null){
            Log.i(TAG, "MyLoader: bundle = " + bundle.toString());
        }
        Log.i(TAG, "MyLoader: context = " + context);
    }
    //сюда помещаем рабочий код, то, что возвращается, колбэчится в onLoadFinished
    //без необходимости вызова deliverResult
    @Override
    public String loadInBackground() {
        Log.i(TAG, hashCode() + " loadInBackground start");
        String result = backgroundOperation();
        Log.i(TAG, "loadInBackground: result - " + result);
        return result;
    }


    private String backgroundOperation() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                Log.i(TAG, "backgroundOperation: i = " + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = Funcs.getDateTime(0, null);
        Log.i(TAG, "backgroundOperation: result = " + result);
        return result;
    }

    //У класса AsyncTaskLoader есть метод отмены: cancelLoad. Отмененный лоадер
    // по окончании работы вызовет уже не onLoadFinished, а onCanceled в AsyncTaskLoader.
    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
        Log.i(TAG, "onCanceled: data = " + data);
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.i(TAG, "onStopLoading");
    }

}