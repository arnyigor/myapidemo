package com.arny.myapidemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import static com.arny.myapidemo.helpers.Consts.TAG;

public class WackeUpService extends IntentService {
    public WackeUpService() {
        super("WackeUpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: ");
    }
}
