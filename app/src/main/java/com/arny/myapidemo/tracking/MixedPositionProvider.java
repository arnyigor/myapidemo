package com.arny.myapidemo.tracking;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.arny.myapidemo.BuildConfig;

@SuppressWarnings("MissingPermission")
public class MixedPositionProvider extends PositionProvider implements LocationListener, GpsStatus.Listener {

	private LocationListener backupListener;
	private long lastFixTime;

	public MixedPositionProvider(Context context, PositionListener listener) {
		super(context, listener);
	}

	public void startUpdates() {
		if (BuildConfig.DEBUG) {
			return;
		}
		lastFixTime = System.currentTimeMillis();
		locationManager.addGpsStatusListener(this);
		try {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, period, 1.0f, this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void stopUpdates() {
		locationManager.removeUpdates(this);
		locationManager.removeGpsStatusListener(this);
		stopBackupProvider();
	}

	private void startBackupProvider() {
		Log.d(MixedPositionProvider.class.getSimpleName(), "startBackupProvider: ");
		if (backupListener == null) {
			backupListener = new LocationListener() {
				@Override
				public void onLocationChanged(Location location) {
					System.out.println("backup provider location");
					updateLocation(location);
				}

				@Override
				public void onStatusChanged(String s, int i, Bundle bundle) {
				}

				@Override
				public void onProviderEnabled(String s) {
				}

				@Override
				public void onProviderDisabled(String s) {
				}
			};

			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, period, 0, backupListener);
		}
	}

	private void stopBackupProvider() {
//        Log.d(TAG, "backup provider stop");
		if (backupListener != null) {
			locationManager.removeUpdates(backupListener);
			backupListener = null;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
//        Log.d(TAG, "provider location");
		stopBackupProvider();
		lastFixTime = System.currentTimeMillis();
		updateLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
//        Log.d(TAG, "provider enabled");
		stopBackupProvider();
	}

	@Override
	public void onProviderDisabled(String provider) {
//        Log.d(TAG, "provider disabled");
		startBackupProvider();
	}

	@Override
	public void onGpsStatusChanged(int event) {
		if (backupListener == null && System.currentTimeMillis() - (lastFixTime + period) > 10000) {
			startBackupProvider();
		}
	}

}