package com.arny.myapidemo.model.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;

public class MyIntentService extends IntentService {
    public static final String ACTION_MYINTENTSERVICE = "com.arny.myapidemo.model.services.RESPONSE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String ACTION_UPDATE = "com.arny.myapidemo.model.services.UPDATE";
    public static final String EXTRA_KEY_UPDATE = "EXTRA_UPDATE";
    public static final String EXTRA_KEY_UPDATE_PROGRESS = "EXTRA_UPDATE_PROGRESS";
    public static final String EXTRA_KEY_UPDATE_TOTAL = "EXTRA_UPDATE_TOTAL";
    private static final String TAG = "LOG_TAG";
    private int total = 1;
    private boolean mIsSuccess;
    private boolean mIsStopped;
    private static Timer timer = new Timer();

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
            notice = "onDestroy with success";
        } else {
            notice = "onDestroy WITHOUT success!";
        }
        Toast.makeText(getApplicationContext(), notice, Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        total = intent.getIntExtra(EXTRA_KEY_UPDATE_TOTAL, 1);
        loaderProcess();
    }


    private void loaderProcess() {
        int sleepTime = 10000 / total;
        for (int i = 0; i <= total; i++) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int progress = i * 100 / total;

            if (mIsStopped) {
                break;
            }
            Intent updateIntent = new Intent();
            updateIntent.setAction(ACTION_UPDATE);
            updateIntent.addCategory(Intent.CATEGORY_DEFAULT);
            updateIntent.putExtra(EXTRA_KEY_UPDATE_PROGRESS, progress);
            updateIntent.putExtra(EXTRA_KEY_UPDATE, i);
            updateIntent.putExtra(EXTRA_KEY_UPDATE_TOTAL, total);
            sendBroadcast(updateIntent);
            mIsSuccess = true;
        }
    }
}
