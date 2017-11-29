package com.arny.myapidemo.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.arny.arnylib.utils.DateTimeUtils;
import com.arny.arnylib.utils.Utility;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 101;

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(MyAlarmReceiver.class.getSimpleName(), "onReceive: time  =" + DateTimeUtils.getDateTime());
    }
}
