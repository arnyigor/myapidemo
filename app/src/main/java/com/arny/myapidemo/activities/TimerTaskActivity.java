package com.arny.myapidemo.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.services.MyTimerService;
import com.arny.myapidemo.utils.BaseUtils;


public class TimerTaskActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView tvInterval;
    private Intent intent;
    private Button btnStart, btnStop;
    private boolean stels = false;
    private CheckBox chbStels;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_task_activity);
        initToolbar();
        initUI();
        intent = new Intent(this, MyTimerService.class);
    }


    private void initUI() {
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        chbStels = (CheckBox) findViewById(R.id.chbStels);
        chbStels.setOnCheckedChangeListener(this);
        tvInterval = (TextView) findViewById(R.id.tvInterval);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle(getString(R.string.title_timer_service));
        }
    }

    private void onBtnStopService() {
        stopService(intent);
    }

    private void onBtnStartService() {
        //TODO проверять по операции,а не только запущенную
        if (!BaseUtils.isMyServiceRunning(MyTimerService.class, this)){
            intent.putExtra(MyTimerService.EXTRA_KEY_TIME, 35);
            intent.putExtra(MyTimerService.EXTRA_KEY_STELS, stels);
            Log.i(TimerTaskActivity.class.getSimpleName(), "onBtnStartService: intent = " + intent.hashCode());
            startService(intent);
        }
    }

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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        stels = isChecked;
    }

    @Override
    public void onResume() {
        super.onResume();
            onRegisterReciever();
    }

    @Override
    public void onPause() {
        super.onPause();
        onUnregisterReciever();
    }


    private void updateUI(Bundle extras) {
        String time = extras.getString(MyTimerService.EXTRA_KEY_TIME);
        if (time != null) {
            tvInterval.setText(time);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent.getExtras());
        }
    };


    private void onRegisterReciever() {
        try {
            registerReceiver(broadcastReceiver, new IntentFilter(MyTimerService.ACTION_UPDATE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onUnregisterReciever() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}