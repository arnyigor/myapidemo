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
import com.arny.myapidemo.ui.activities.NetworkActivity;

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
    private Messenger outMessenger;
    private Timer timer = new Timer();
    private int startId;
    private long pauseSec;
    private boolean connected;
	private NotificationManager notifyManager;
	private Notification.Builder notifyBuilder;

	private Notification.Builder getNotifyBuilder() {
		if (notifyBuilder == null) {
			createNotification(getApplicationContext());
		}
		return notifyBuilder;
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
        startForeground(NOTIFICATION_ID, createNotification(getApplicationContext()));
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

	private Notification createNotification(Context context) {
		notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		isHostAvailable("www.google.com", 80, 1000);
		String content = DateTimeUtils.getDateTime("HH:mm:ss") + " Net:" + DroidUtils.getNetworkInfo(getApplicationContext());
		notifyBuilder = new Notification.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)// маленькая иконка
				.setContentTitle("Host:" + (connected ? "Online" : "Offline"))// Заголовок уведомления
				.setContentText("NET:Time:" + content); // Текст уведомления
		return notifyBuilder.build();
	}

    private void updateNotification() {
	    String content = DateTimeUtils.getDateTime("HH:mm:ss") + " Net:" + DroidUtils.getNetworkInfo(getApplicationContext());
	    getNotifyBuilder()
			    .setContentTitle("Host:" + (connected ? "Online" : "Offline"))
			    .setContentText("NET:Time:" + content);
	    notifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
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