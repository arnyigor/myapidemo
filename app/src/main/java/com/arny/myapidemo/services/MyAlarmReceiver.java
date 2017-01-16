package com.arny.myapidemo.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.arny.myapidemo.services.MyAlarmReceiver";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, WackeUpService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
