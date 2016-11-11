package com.arny.myapidemo.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.arny.myapidemo.R;
import com.arny.myapidemo.activities.TimerTaskActivity;
import com.arny.myapidemo.helpers.Consts;
import com.arny.myapidemo.helpers.Funcs;

import java.util.Timer;
import java.util.TimerTask;

import static com.arny.myapidemo.helpers.Consts.TAG;

public class MyTimerService extends Service {

    public static final int NOTIFICATION_ID = 111;
    public static final String MY_TIMER_SERVICE_BROADCAST = "MyTimerService";
    public static final String MY_TIMER_SERVICE_TIME = "time";
    public static final String MY_TIMER_SERVICE_STELS = "stels";
    private static final int REQUEST_CODE = 100;
    private static Timer timer = new Timer();
    private int seconds = 0;
    private int startId;
    private boolean stels = false;
    private static Funcs funcs = Funcs.getInstance();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate service " + startId + " hash: " + this.hashCode());

    }

    @Override
    public boolean stopService(Intent name) {
        Log.i(TAG, "stopService " + this.hashCode());
        return super.stopService(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.startId = startId;
        try {
            Log.i(TAG, "onStartCommand " + startId + " hash: " + this.hashCode());
            Log.i(TAG, "onStartCommand intent = " + intent.hashCode());
            try {
                seconds = intent.getIntExtra(TimerTaskActivity.INTENT_PARAM_TIME, (int) Consts.ONE_MINUTE);
                stels = intent.getBooleanExtra(TimerTaskActivity.INTENT_PARAM_HIDE, false);
                Log.i(TAG, "onStartCommand seconds = " + seconds);
                Log.i(TAG, "onStartCommand stels = " + stels);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
                timer = new Timer();
            }
            timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
            setServiceNotification();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind service " + startId + " hash: " + this.hashCode());
        return null;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        Log.i(TAG, "onDestroy service " + startId + " hash: " + this.hashCode());
        super.onDestroy();
    }

    private void displaySeconds(int secs) {
        Log.i(TAG, "entered displaySeconds " + secs);
        Intent intent = new Intent(TimerTaskActivity.BROADCAST_ACTION);
        updateNotification();

        String time = funcs.getTimeMIN_SEC(seconds);
        intent.putExtra(MY_TIMER_SERVICE_TIME, time);
        sendBroadcast(intent);
    }

    private void setServiceNotification() {
        Log.i(TAG, "setServiceNotification ");
        Notification notification = getServiceNotification();
        startForeground(NOTIFICATION_ID, notification);

        //HideNotificationService
       if (stels){
           Intent hideIntent = new Intent(this, HideNotificationService.class);
           startService(hideIntent);
       }
    }

    private Notification getServiceNotification() {
        Notification notification;
        Intent mainIntent = new Intent(getApplicationContext(), TimerTaskActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,REQUEST_CODE,mainIntent,0);
        Notification.Builder notifbuild = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)// маленькая иконка
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getBaseContext().getResources().getString(R.string.str_service_start))// Заголовок уведомления
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText(funcs.getTimeMIN_SEC(seconds)); // Текст уведомления
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifbuild.setContentIntent(pendingIntent);
        notification = notifbuild.build();
        return notification;
    }

    private void updateNotification() {
        Notification notification = getServiceNotification();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void stop() {
        Log.i(TAG, "MyRun#" + startId + " end, stopSelf(" + startId + ")");
        stopForeground(true);
        stopSelf();
    }

    private class mainTask extends TimerTask {
        public void run() {
            Log.i(TAG, "run service " + seconds);
           try {
               if (seconds < 0) {
                   stop();
               }else{
                   displaySeconds(seconds);
                   seconds--;
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

}