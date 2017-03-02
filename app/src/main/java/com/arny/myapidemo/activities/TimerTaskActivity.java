package com.arny.myapidemo.activities;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.arny.myapidemo.R;
import com.arny.myapidemo.utils.Consts;
import com.arny.myapidemo.services.MyTimerService;

public class TimerTaskActivity extends AppCompatActivity {
    public static final String BROADCAST_ACTION = "com.arny.myapidemo.activities.servicebackbroadcast";
    public static final String INTENT_PARAM_TIME = "serviceparamtime";
    public static final String INTENT_PARAM_HIDE = "serviceparamhide";
    private static final String TAG = "LOG_TAG";
    TextView tvInterval;
    Intent intent;
    private Button btnStart, btnStop;
    private boolean bolBroacastRegistred = false,stels = false;
    private EditText edtTime;
    private CheckBox chbStels;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: ");
            updateUI(intent);
        }
    };
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStart:
                    onBtnStartService();
                    break;
                case R.id.btnStop:
                    onBtnStopService();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate " + TimerTaskActivity.this.hashCode());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_task_activity);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStart.setOnClickListener(listener);
        btnStop.setOnClickListener(listener);
        chbStels = (CheckBox) findViewById(R.id.chbStels);
        chbStels.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stels = isChecked;
            }
        });
        tvInterval = (TextView) findViewById(R.id.tvInterval);
        edtTime = (EditText) findViewById(R.id.edtTime);
        intent = new Intent(TimerTaskActivity.this, MyTimerService.class);
    }

    @Override
    protected void onResume() {
        try {
            Log.i(TAG, "onResume " + this.hashCode());
            Log.i(TAG, "onPause broadcastReceiver " + broadcastReceiver.hashCode());
            checkOnResumeService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Log.i(TAG, "onPause activity " + this.hashCode());
            Log.i(TAG, "onPause broadcastReceiver " + broadcastReceiver.hashCode());
            checkOnPauseService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy activity "+ this.hashCode());
        super.onDestroy();
    }

    private void onBtnStopService() {
        try {
            if (isMyServiceRunning(MyTimerService.class)){
                stopService(intent);
                unregisterReceiver(broadcastReceiver);
                bolBroacastRegistred = false;
                Log.i(TAG, "onClick unregisterReceiver " + broadcastReceiver.hashCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onBtnStartService() {
        try {
            if (intent==null){
                intent = new Intent(TimerTaskActivity.this, MyTimerService.class);
            }
           // int min = Integer.parseInt((edtTime.getText().toString()));
            Log.i(TAG, "onBtnStartService: min = " + 1);
            Log.i(TAG, "onBtnStartService: stels = " + stels);
            Log.i(TAG, "onBtnStartService intent = " + intent.hashCode());
            intent.putExtra(INTENT_PARAM_TIME, 1*Consts.ONE_MINUTE);
            intent.putExtra(INTENT_PARAM_HIDE, stels);
            if (!isMyServiceRunning(MyTimerService.class)){
                startService(intent);
                registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ACTION));
                bolBroacastRegistred = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI(Intent intent) {
        try {
            if (intent != null){
                String time =  intent.getStringExtra(MyTimerService.MY_TIMER_SERVICE_TIME);
                if (time != null){
                    Log.i(TAG, time);
                    tvInterval.setText(time);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkOnResumeService() {
        if (!bolBroacastRegistred && isMyServiceRunning(MyTimerService.class)) {
            registerReceiver(broadcastReceiver, new IntentFilter(MyTimerService.MY_TIMER_SERVICE_BROADCAST));
            bolBroacastRegistred = true;
            updateUI(intent);
        }
    }

    private void checkOnPauseService() {
        if (bolBroacastRegistred && isMyServiceRunning(MyTimerService.class)) {
            unregisterReceiver(broadcastReceiver);
            bolBroacastRegistred = false;
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}