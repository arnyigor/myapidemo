package com.arny.myapidemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;

public class MyIntentService extends IntentService {
    public static final String ACTION = "com.arny.myapidemo.services.MyTestService";
    public static final String ACTION_MYINTENTSERVICE = "com.arny.myapidemo.services.RESPONSE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String ACTION_UPDATE = "com.arny.myapidemo.services.UPDATE";
    public static final String EXTRA_KEY_UPDATE = "EXTRA_UPDATE";
    public static final String EXTRA_KEY_UPDATE_PROGRESS = "EXTRA_UPDATE_PROGRESS";
    public static final String EXTRA_KEY_UPDATE_TOTAL = "EXTRA_UPDATE_TOTAL";
    public static final String EXTRA_KEY_FINISH = "EXTRA_PROCESS_FINISH";
    public static final String EXTRA_KEY_FINISH_SUCCESS = "EXTRA_PROCESS_FINISH_GOOD";
    private static final String TAG = "LOG_TAG";

    private int total = 1;
    private boolean mIsSuccess;
    private boolean mIsStopped;

    public MyIntentService() {
        super("myname");
        mIsSuccess = false;
        mIsStopped = false;
    }

    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        String notice;
        mIsStopped = true;
        if (mIsSuccess) {
            notice = "onDestroy WITHsuccess!";
        } else {
            notice = "onDestroy WITHOUT success!";
        }
        Intent updateIntent = initProadcastIntent();
        updateIntent.putExtra(EXTRA_KEY_UPDATE, total);
        updateIntent.putExtra(EXTRA_KEY_FINISH, true);
        updateIntent.putExtra(EXTRA_KEY_FINISH_SUCCESS, mIsSuccess);
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateIntent);
//        Toast.makeText(getApplicationContext(), notice, Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @NonNull
    private Intent initProadcastIntent() {
        Intent updateIntent = new Intent();
        updateIntent.setAction(ACTION_UPDATE);
        updateIntent.addCategory(Intent.CATEGORY_DEFAULT);
        return updateIntent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: ");
        total = intent.getIntExtra(EXTRA_KEY_UPDATE_TOTAL, 1);
        loaderProcess();
    }


    private void loaderProcess() {
        Log.i(TAG, "loaderProcess: ");
        int sleepTime = 10000 / total;
        for (int i = 0; i <= total; i++) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int progress = i * 100 / total;

            Intent updateIntent = initProadcastIntent();
            updateIntent.putExtra(EXTRA_KEY_UPDATE_PROGRESS, progress);
            updateIntent.putExtra(EXTRA_KEY_UPDATE, i);
            updateIntent.putExtra(EXTRA_KEY_UPDATE_TOTAL, total);
            updateIntent.putExtra(EXTRA_KEY_FINISH, false);
            updateIntent.putExtra(EXTRA_KEY_FINISH_SUCCESS, mIsSuccess);
            LocalBroadcastManager.getInstance(this).sendBroadcast(updateIntent);
            if (mIsStopped) {
                break;
            }
        }
        mIsSuccess = true;
    }
}
