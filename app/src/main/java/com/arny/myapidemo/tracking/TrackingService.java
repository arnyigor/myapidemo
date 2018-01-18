package com.arny.myapidemo.tracking;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.arny.arnylib.utils.Config;
import com.arny.myapidemo.R;
import com.arny.myapidemo.weather.view.WeatherViewActivity;

public class TrackingService extends Service {

	private static final String TAG = TrackingService.class.getSimpleName();
	private static final int NOTIFICATION_ID = 1;
	private TrackingController trackingController;

	private static Notification createNotification(Context context) {
		Intent notificationIntent = new Intent(context, WeatherViewActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		Notification notification = new Notification();
		// Use new API
		Notification.Builder builder = new Notification.Builder(context)
				.setContentIntent(pendingIntent)
				.setSmallIcon(android.R.drawable.stat_notify_sync_noanim)
				.setContentTitle(context.getString(R.string.app_name));
		notification = builder.build();
		return notification;
	}

	public static class HideNotificationService extends Service {
		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onCreate() {
            startForeground(NOTIFICATION_ID, createNotification(this));
            stopForeground(true);
		}

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			stopSelfResult(startId);
			return START_NOT_STICKY;
		}
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "service create");
		if (Config.getBoolean("TRACKING_KEY_STATUS",false,getApplicationContext())){
			trackingController = new TrackingController(this);
			trackingController.start();
            startForeground(NOTIFICATION_ID, createNotification(this));
            startService(new Intent(this, HideNotificationService.class));
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			if (intent != null) {
				TrackingReceiver.completeWakefulIntent(intent);
				trackingController.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "service destroy");
		stopForeground(true);
		if (trackingController != null) {
			trackingController.stop();
		}
	}

}
