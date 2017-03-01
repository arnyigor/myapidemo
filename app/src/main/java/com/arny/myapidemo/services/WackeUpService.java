package com.arny.myapidemo.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.arny.myapidemo.R;
import com.arny.myapidemo.helpers.Utils;

import static com.arny.myapidemo.helpers.Consts.TAG;

public class WackeUpService extends IntentService {
    public WackeUpService() {
        super("WackeUpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: intent = " + intent.getExtras().toString());
        Log.i(TAG, "onHandleIntent: intent time= " + Utils.getDateTime(0,null));
        // Create Notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher))
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                .setTicker(getApplicationContext().getResources().getString(R.string.app_name))
                .setContentText(Utils.getDateTime(0,"dd MMM yyyy HH:mm:ss"))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setOnlyAlertOnce(true);

        NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(777, mBuilder.build());
        BootBroadcastReceiver.completeWakefulIntent(intent);
    }
}
