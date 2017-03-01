package com.arny.myapidemo.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

// WakefulBroadcastReceiver ensures the device does not go back to sleep
// during the startup of the service
public class BootBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Launch the specified service when this message is received
        Log.i(BootBroadcastReceiver.class.getSimpleName(), "onReceive: intent = " + intent);
        Intent startServiceIntent = new Intent(context, WackeUpService.class);
        startWakefulService(context, startServiceIntent);
    }
}