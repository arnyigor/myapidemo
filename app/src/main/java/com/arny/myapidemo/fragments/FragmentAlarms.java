package com.arny.myapidemo.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.arny.myapidemo.R;
import com.arny.myapidemo.helpers.Consts;
import com.arny.myapidemo.services.MyAlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentAlarms extends Fragment {
	private Button btnFragmAlarmStart,btnFragmAlarmEnd;
	private Context context;
	private ArrayList<Integer> ids;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_one, null);
		context = container.getContext();
		ids = new ArrayList<>();
		ids.add(3);
		ids.add(7);
		btnFragmAlarmStart = (Button) v.findViewById(R.id.btnFragmAlarmStart);
		btnFragmAlarmEnd = (Button) v.findViewById(R.id.btnFragmAlarmEnd);
		btnFragmAlarmStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				for (int id : ids) {
					scheduleAlarm(id);
				}
			}
		});
		btnFragmAlarmEnd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				for (int id : ids) {
					cancelAlarm(id);
				}
			}
		});
		return v;
	}

	// Setup a recurring alarm every half hour
	public void scheduleAlarm(int ID) {
		Log.i(FragmentAlarms.class.getSimpleName(), "scheduleAlarm: ID = " + ID);
		// Construct an intent that will execute the AlarmReceiver
		Intent intent = new Intent(context, MyAlarmReceiver.class);
		// Create a PendingIntent to be triggered when the alarm goes off
		PendingIntent pIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// Setup periodic alarm every 5 seconds
		long firstMillis = System.currentTimeMillis(); // alarm is set right away
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		// First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
		// Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
		alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis + (ID * DateUtils.SECOND_IN_MILLIS), (DateUtils.SECOND_IN_MILLIS*ID), pIntent);
//		alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (DateUtils.SECOND_IN_MILLIS*2*ID), pIntent);
	}

	public void cancelAlarm(int ID) {
		Log.i(FragmentAlarms.class.getSimpleName(), "cancelAlarm: ID = " + ID);
		Intent intent = new Intent(context, MyAlarmReceiver.class);
		final PendingIntent pIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarm.cancel(pIntent);
	}
}
