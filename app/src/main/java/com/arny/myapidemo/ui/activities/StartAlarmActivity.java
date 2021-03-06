package com.arny.myapidemo.ui.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.arny.myapidemo.R;
import com.arny.myapidemo.services.BootBroadcastReceiver;
import com.arny.myapidemo.services.MyAlarmReceiver;

public class StartAlarmActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_alarm_activity);
        Button buttonStart = (Button) findViewById(R.id.btnStartAlarm);
        Button buttonStop = (Button) findViewById(R.id.btnStopAlarm);
        buttonStart.setOnClickListener(v -> scheduleAlarm());

        buttonStop.setOnClickListener(v -> cancelAlarm());
    }


    // Setup a recurring alarm every half hour
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), BootBroadcastReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        intent.putExtra("test",333);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        Log.i(StartAlarmActivity.class.getSimpleName(), "scheduleAlarm: SystemClock.elapsedRealtime() = " + SystemClock.elapsedRealtime());
        Log.i(StartAlarmActivity.class.getSimpleName(), "scheduleAlarm: System.currentTimeMillis() = " + System.currentTimeMillis());
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 60000, pIntent);
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, pIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), BootBroadcastReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
}