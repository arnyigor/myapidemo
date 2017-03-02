package com.arny.myapidemo.net;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.arny.myapidemo.utils.BaseUtils;

import java.util.concurrent.TimeUnit;

import static com.arny.myapidemo.utils.Consts.INTENT_NAME_TEST;
import static com.arny.myapidemo.utils.Consts.TAG;
import static com.arny.myapidemo.utils.Consts.TEST_STRING_DATA;

public class DataLoader extends Loader<String> {
    private DataTask datatask;

    public DataLoader(Context context, Bundle bdl) {
        super(context);
        Log.i(TAG, hashCode() + " create TimeLoader");
        if (bdl != null) {
            String dat = bdl.getString(INTENT_NAME_TEST);
            Log.i(TAG, "DataLoader: dat = " + dat);
        }
    }

    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.i(TAG, hashCode() + " onForceLoad");
        if (datatask != null) {
            datatask.cancel(true);
        }
        datatask = new DataTask();
        datatask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, TEST_STRING_DATA);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(TAG, hashCode() + " onStartLoading");
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.i(TAG, hashCode() + " onStartLoading");
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
        Log.i(TAG, hashCode() + " onAbandon");
    }

    private void getResultFromTask(String result) {
        deliverResult(result);
    }

    private class DataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, DataLoader.this.hashCode() + " doInBackground");
            Log.i(TAG, "doInBackground: start huge task!!!!!!");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: end huge task!!!!!!");
            return BaseUtils.getDateTime(0,null);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i(TAG, DataLoader.this.hashCode() + " onPostExecute " + result);
            getResultFromTask(result);
        }
    }
}
