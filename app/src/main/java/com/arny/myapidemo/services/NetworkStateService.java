package com.arny.myapidemo.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.arny.arnylib.utils.DateTimeUtils;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.myapidemo.R;
import com.arny.myapidemo.activities.NetworkActivity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkStateService extends Service {

    public static final int NOTIFICATION_ID = 111;
    public static final int REQUEST_CODE = 100;
    public static final String ACTION_UPDATE = "NetworkStateService.action.update";
    public static final String EXTRA_KEY_TIME = "NetworkStateService.extra.time";
    public static final String EXTRA_KEY_NETINFO = "NetworkStateService.extra.networkinfo";
    public static final String ACTION = "NetworkStateService.action";
    private final IBinder mBinder = new MyBinder();
    private Messenger outMessenger;
    private Timer timer = new Timer();
    private int startId;
    private long pauseSec;
    private boolean connected;

    public class MyBinder extends Binder {
        public NetworkStateService getService() {
            return NetworkStateService.this;
        }
    }

    @Override
    public void onCreate() {
        setServiceNotification();
        updateNotification();
    }

    private void initTimer() {
        reInitTimer();
        timer.scheduleAtFixedRate(new TimerOperation(), 0, 1000);
    }

    private void reInitTimer() {
        cancelTimer();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        Bundle extras = intent.getExtras();
        initTimer();
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

    private void updateInfo() {
        updateNotification();
        Intent intent = new Intent(ACTION_UPDATE);
        intent.putExtra(EXTRA_KEY_NETINFO, DroidUtils.getNetworkInfo(getApplicationContext()));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void setServiceNotification() {
        Notification notification = getServiceNotification();
        startForeground(NOTIFICATION_ID, notification);
    }

    @SuppressLint("StaticFieldLeak")
    public void isHostAvailable(String host, int port, int timeoutMs) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Socket sock = new Socket();
                    SocketAddress sockaddr = new InetSocketAddress(host, port);
                    sock.connect(sockaddr, timeoutMs); // This will block no more than timeoutMs
                    sock.close();
                    connected = true;
                } catch (IOException e) {
                    connected = false;
                }
                return null;
            }
        }.execute();
    }

    private Notification getServiceNotification() {
        Intent mainIntent = new Intent(getApplicationContext(), NetworkActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, mainIntent, 0);
        isHostAvailable("www.google.com", 80, 500);
        String content = DateTimeUtils.getDateTime("HH:mm:ss") + " Net:" + DroidUtils.getNetworkInfo(getApplicationContext());
        Notification.Builder notifbuild = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)// маленькая иконка
                .setWhen(System.currentTimeMillis())
                .setContentTitle("NET:Time:" + content)// Заголовок уведомления
                .setContentText("Host:" + (connected ? "Online" : "Offline")); // Текст уведомления
        notifbuild.setContentIntent(pendingIntent);
        return notifbuild.build();
    }

    private void updateNotification() {
        Notification notification = getServiceNotification();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void stopTimerService() {
        cancelTimer();
        stopForeground(true);
        stopSelf(startId);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class TimerOperation extends TimerTask {
        public void run() {
            Log.i(TimerOperation.class.getSimpleName(), "TimerOperation: pauseSec: " + pauseSec + " taskid: " + startId);
            updateInfo();
            pauseSec++;
        }
    }
}