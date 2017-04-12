package com.arny.myapidemo.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.arny.myapidemo.utils.BaseUtils;


public class MyLoader extends AsyncTaskLoader<String> {

    private LoaderOperation currentOperation = null;
    public enum LoaderOperation {load,calc};

    public static final String EXTRA_LOADER_OPERATION = "MyLoader.loader.operation";
    private static final String TAG = MyLoader.class.getSimpleName();

    public MyLoader(Context context,Bundle bundle) {
        super(context);
        if (bundle !=null){
            currentOperation = (LoaderOperation) bundle.get(EXTRA_LOADER_OPERATION);
            Log.i(MyLoader.class.getSimpleName(), "MyLoader: currentOperation = " + currentOperation);
        }
    }
    @Override
    public String loadInBackground() {
        Log.i(TAG, "loader:" + hashCode() + " loadInBackground start operation "+ currentOperation+" in " + BaseUtils.getDateTime(0, null));
        if (currentOperation != null) {
            switch (currentOperation) {
                case load:
                    SystemClock.sleep(5000);
                    break;
                case calc:
                    SystemClock.sleep(3000);
                    break;
            }
        }else{
            SystemClock.sleep(1000);
        }
        String finish = BaseUtils.getDateTime(0, null);
        Log.i(TAG, "loader:" + hashCode() + " loadInBackground: finish " + finish);
        return finish;
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