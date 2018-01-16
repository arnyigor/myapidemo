package com.arny.myapidemo.weather.datalayer;

import android.app.Activity;
import com.github.florent37.rxgps.RxGps;
public class Loc {
	private static final Object LOCK = new Object();
	private static RxGps sInstance;

	public static synchronized RxGps getInstance(Activity activity) {
		if (sInstance == null) {
			synchronized (LOCK) {
				if (sInstance == null) {
					sInstance = new RxGps(activity);
				}
			}
		}
		return sInstance;
	}
}
