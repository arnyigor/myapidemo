package com.arny.myapidemo.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.arny.myapidemo.R;


public class HideNotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher);
        Notification notification;
        notification = builder.build();
        startForeground(MyTimerService.NOTIFICATION_ID, notification);
        stopForeground(true);
    }

}
