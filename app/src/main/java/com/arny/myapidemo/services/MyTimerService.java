package com.arny.myapidemo.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
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
    public static final String EXTRA_KEY_PAUSE = "MyTimerService.extra.pause";

    private Timer timer = new Timer();
    private int tastSec = 0,pauseSec,startId;
    private boolean stels,pause;


    @Override
    public void onCreate() {
        setServiceNotification();
        updateNotification();
    }

    private void downTimeOperation() {
        reInitTimer();
        timer.scheduleAtFixedRate(new downTimeTask(), 0, 1000);
    }

    private void pauseTimeOperation() {
        reInitTimer();
        timer.scheduleAtFixedRate(new pouseTimeOperation(), 0, 1000);
    }

    private void reInitTimer() {
        Log.i(MyTimerService.class.getSimpleName(), "downTimeOperation: timer 1= " + timer);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        Log.i(MyTimerService.class.getSimpleName(), "downTimeOperation: timer 2= " + timer);
        timer = new Timer();
        Log.i(MyTimerService.class.getSimpleName(), "downTimeOperation: timer 3= " + timer);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        Bundle extras = intent.getExtras();
        Log.i(MyTimerService.class.getSimpleName(), "onStartCommand: pause 1= " + pause);
        Log.i(MyTimerService.class.getSimpleName(), "onStartCommand: tastSec 1= " + tastSec);
        Log.i(MyTimerService.class.getSimpleName(), "onStartCommand: stels 1= " + stels);
        pause = extras.getBoolean(EXTRA_KEY_PAUSE, false);
        tastSec = extras.getInt(EXTRA_KEY_TIME, 60);
        stels = extras.getBoolean(EXTRA_KEY_STELS, false);
        Log.i(MyTimerService.class.getSimpleName(), "onStartCommand: pause 2= " + pause);
        Log.i(MyTimerService.class.getSimpleName(), "onStartCommand: tastSec 2= " + tastSec);
        Log.i(MyTimerService.class.getSimpleName(), "onStartCommand: stels 2= " + stels);
        if (!pause) {
            downTimeOperation();
        }else{
            pauseTimeOperation();
        }
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void displaySeconds() {
        if (!stels){
            updateNotification();
        }
        Intent intent = new Intent(ACTION_UPDATE);
        intent.putExtra(EXTRA_KEY_TIME, BaseUtils.strLogTime(tastSec));
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
                .setContentText(BaseUtils.strLogTime(tastSec)); // Текст уведомления
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
        timer = null;
        if (stels) {
           stopForeground(true);
        }
        stopSelf(startId);
    }

    private class downTimeTask extends TimerTask {
        public void run() {
            Log.i(pouseTimeOperation.class.getSimpleName(), "downTimeTask: sec = " + tastSec +" pauseSec = " + pauseSec+ " taskid = " + startId);
            displaySeconds();
            if (tastSec <=0){
                stopTimerService();
            }else{
                tastSec--;
            }
        }
    }


    private class pouseTimeOperation extends TimerTask {
        public void run() {
            Log.i(pouseTimeOperation.class.getSimpleName(), "pouseTimeOperation: sec = " + tastSec +" pauseSec = " + pauseSec+ " taskid = " + startId);
            displaySeconds();
            pauseSec++;
        }
    }

}