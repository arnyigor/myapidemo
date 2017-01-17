package com.arny.myapidemo.model.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.arny.myapidemo.R;

import static com.arny.myapidemo.model.services.MyTimerService.NOTIFICATION_ID;

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
        startForeground(NOTIFICATION_ID, notification);
        stopForeground(true);
    }

}
