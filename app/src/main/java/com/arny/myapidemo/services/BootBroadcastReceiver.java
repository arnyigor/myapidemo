package com.arny.myapidemo.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.arny.arnylib.utils.Utility;

// WakefulBroadcastReceiver ensures the device does not go back to sleep
// during the startup of the service
public class BootBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Launch the specified service when this message is received
        Bundle extras = intent.getExtras();
        Log.i(BootBroadcastReceiver.class.getSimpleName(), "onReceive: extras = " + extras.get("test"));
        Log.i(BootBroadcastReceiver.class.getSimpleName(), "onReceive: time = " + Utility.getDateTime(0,null));
        Intent startServiceIntent = new Intent(context, WackeUpService.class);
        startServiceIntent.putExtras(extras);
        startWakefulService(context, startServiceIntent);
    }
}