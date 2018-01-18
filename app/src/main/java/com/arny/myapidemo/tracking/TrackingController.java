package com.arny.myapidemo.tracking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import com.android.volley.Request;
import org.json.JSONObject;

import static android.content.Context.ALARM_SERVICE;

public class TrackingController implements PositionProvider.PositionListener {

	private boolean isWaiting;
	private Context context;
	private Handler handler;
	private PositionProvider positionProvider;
	private PowerManager.WakeLock wakeLock;
	private Intent intentTracking;
	private Intent intentTrackingService;
	private AlarmManager alarmManager;
	private PendingIntent pendingIntent;

	private void lock() {
		wakeLock.acquire(30000);
	}

	private void unlock() {
		if (wakeLock.isHeld()) {
			wakeLock.release();
		}
	}

	public TrackingController(Context context) {
		this.context = context;
		handler = new Handler();
		positionProvider = new MixedPositionProvider(context, this);
		PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		if (powerManager != null) {
			wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
		}
		initIntents();
	}

	public void start() {
		try {
			positionProvider.startUpdates();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			positionProvider.stopUpdates();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		handler.removeCallbacksAndMessages(null);
	}

	@Override
	public void onPositionUpdate(Position position) {
        Log.d(TrackingController.class.getSimpleName(), "onPositionUpdate: position: " + position);
	}

	private void log(String action, Position position) {
		if (position != null) {
			action += " (" +
					"id:" + position.getId() +
					" time:" + position.getTime().getTime() / 1000 +
					" lat:" + position.getLatitude() +
					" lon:" + position.getLongitude() + ")";
		}
		Log.d(TrackingController.class.getSimpleName(), "log: action = " + action);
	}

	private void stopTrackingService() {
		try {
			alarmManager.cancel(pendingIntent);
			context.stopService(new Intent(context, TrackingService.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initIntents() {
		if (intentTracking == null) {
			intentTracking = new Intent(context, TrackingReceiver.class);
		}
		if (intentTrackingService == null) {
			intentTrackingService = new Intent(context, TrackingService.class);
		}
		if (alarmManager == null) {
			alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
		}
		if (pendingIntent == null) {
			pendingIntent = PendingIntent.getBroadcast(context, 0, intentTracking, PendingIntent.FLAG_UPDATE_CURRENT);
		}
	}

}
