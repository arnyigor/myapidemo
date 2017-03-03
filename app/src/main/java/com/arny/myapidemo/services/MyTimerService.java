package com.arny.myapidemo.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.arny.myapidemo.R;
import com.arny.myapidemo.activities.TimerTaskActivity;
import com.arny.myapidemo.utils.BaseUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimerService extends Service {

    public static final int NOTIFICATION_ID = 111;
    public static final int REQUEST_CODE = 100;
    public static final String ACTION_UPDATE = "MyTimerService.action.update";
    public static final String EXTRA_KEY_TIME = "MyTimerService.extra.time";
    public static final String EXTRA_KEY_STELS = "MyTimerService.extra.stels";

    private Timer timer = new Timer();
    private int seconds = 0,startId;
    private boolean stels;



    @Override
    public void onCreate() {
        setServiceNotification();
        updateNotification();
    }

    private void startOperation() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        Bundle extras = intent.getExtras();
        seconds = extras.getInt(EXTRA_KEY_TIME, 60);
        stels = extras.getBoolean(EXTRA_KEY_STELS, false);
        startOperation();
        if (stels){
            Intent hideIntent = new Intent(this, HideNotificationService.class);
            startService(hideIntent);
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.i(MyTimerService.class.getSimpleName(), "onDestroy: startId = " + startId);
        stopTimerService();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void displaySeconds() {
        if (!stels){
            updateNotification();
        }
        Intent intent = new Intent(ACTION_UPDATE);
        intent.putExtra(EXTRA_KEY_TIME, BaseUtils.strLogTime(seconds));
        sendBroadcast(intent);
    }

    private void setServiceNotification() {
        Notification notification = getServiceNotification();
        startForeground(NOTIFICATION_ID, notification);
    }

    private Notification getServiceNotification() {
        Notification notification;
        Intent mainIntent = new Intent(getApplicationContext(), TimerTaskActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,REQUEST_CODE,mainIntent,0);
        Notification.Builder notifbuild = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)// маленькая иконка
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getBaseContext().getResources().getString(R.string.str_service_running))// Заголовок уведомления
                .setContentText(BaseUtils.strLogTime(seconds)); // Текст уведомления
//        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifbuild.setContentIntent(pendingIntent);
        notification = notifbuild.build();
        return notification;
    }

    private void updateNotification() {
        Notification notification = getServiceNotification();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void stopTimerService() {
        timer.cancel();
        stopForeground(true);
        stopSelf();
    }

    private class mainTask extends TimerTask {
        public void run() {
            Log.i(mainTask.class.getSimpleName(), "run: sec = " + seconds);
            if (seconds<=0){
                stopTimerService();
            }else{
                displaySeconds();
                seconds--;
            }
        }
    }

}