package com.arny.myapidemo.tracking;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import com.arny.arnylib.utils.Config;

public abstract class PositionProvider {

	public interface PositionListener {
		void onPositionUpdate(Position position);
	}

	private final PositionListener listener;
	private final Context context;
	protected final LocationManager locationManager;
	protected String type;
	protected final int period;

	public abstract void startUpdates();

	private long lastUpdateTime;

	public abstract void stopUpdates();

	public PositionProvider(Context context, PositionListener listener) {
		this.context = context;
		this.listener = listener;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		period = 300000;
		type = Config.getString("TRACKING_KEY_PROVIDER", context, "gps");
	}

	protected void updateLocation(Location location) {
		if (location != null) {
			if (location.getTime() - lastUpdateTime >= period) {
				lastUpdateTime = location.getTime();
				listener.onPositionUpdate(new Position(location, getBatteryLevel(context)));
			}
		}
	}

	public static double getBatteryLevel(Context context) {
		Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		if (batteryIntent != null) {
			int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);
			return (level * 100.0) / scale;
		}
		return 0;
	}
}
