package com.arny.myapidemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.arny.myapidemo.BuildConfig;


public class WackeUpService extends IntentService {
    public WackeUpService() {
        super("WackeUpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (BuildConfig.DEBUG) Log.d(WackeUpService.class.getSimpleName(), "onHandleIntent: " + extras.get("test"));
        BootBroadcastReceiver.completeWakefulIntent(intent);
    }

}