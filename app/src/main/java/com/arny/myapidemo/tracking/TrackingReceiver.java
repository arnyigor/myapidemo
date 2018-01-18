package com.arny.myapidemo.tracking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.arny.arnylib.utils.Config;
public class TrackingReceiver extends AbstractWakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TrackingReceiver.class.getSimpleName(), "onReceive: TRACKING_KEY_STATUS = " + Config.getBoolean("TRACKING_KEY_STATUS", false, context));
		if (Config.getBoolean("TRACKING_KEY_STATUS", false, context)) {
			startWakefulService(context, new Intent(context, TrackingService.class));
		}
	}

}
