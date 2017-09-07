package com.arny.myapidemo.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.services.MyIntentService;

public class IntentServiceActivity extends AppCompatActivity {

    private TextView mInfoTextView;
    private ProgressBar mProgressBar;
    private Button startButton, stopButton;
    private EditText edtCnt;
    private MyBroadcastReceiver mMyBroadcastReceiver;
    private UpdateBroadcastReceiver mUpdateBroadcastReceiver;
    private Intent mMyServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        mInfoTextView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        startButton = (Button) findViewById(R.id.buttonStart);
        stopButton = (Button) findViewById(R.id.buttonStop);
        edtCnt = (EditText) findViewById(R.id.edtCnt);
        mMyServiceIntent = new Intent(IntentServiceActivity.this, MyIntentService.class);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Запускаем свой IntentService
                if (mMyServiceIntent == null){
                    mMyServiceIntent = new Intent(IntentServiceActivity.this,
                            MyIntentService.class);
                }
                int updateTotal = 1;
                try {
                    updateTotal = Integer.parseInt(edtCnt.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startService(mMyServiceIntent.putExtra(MyIntentService.EXTRA_KEY_UPDATE_TOTAL, updateTotal));
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyServiceIntent != null) {
                    stopService(mMyServiceIntent);
                    mMyServiceIntent = null;
                }
            }
        });

        mMyBroadcastReceiver = new MyBroadcastReceiver();
        mUpdateBroadcastReceiver = new UpdateBroadcastReceiver();

        // регистрируем BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(
                MyIntentService.ACTION_MYINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);

        // Регистрируем второй приёмник
        IntentFilter updateIntentFilter = new IntentFilter(
                MyIntentService.ACTION_UPDATE);
        updateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mUpdateBroadcastReceiver, updateIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
        unregisterReceiver(mUpdateBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String  result = intent
                    .getStringExtra(MyIntentService.EXTRA_KEY_OUT);
            mInfoTextView.setText(result);
        }
    }

    public class UpdateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int percent = intent.getIntExtra(MyIntentService.EXTRA_KEY_UPDATE_PROGRESS, 0);
            int update = intent.getIntExtra(MyIntentService.EXTRA_KEY_UPDATE, 0);
            int total = intent.getIntExtra(MyIntentService.EXTRA_KEY_UPDATE_TOTAL, 0);
            String strProgress =String.valueOf(percent) + " % " + update + " элементов из " + total + " элементов";
            mInfoTextView.setText(strProgress);
            mProgressBar.setProgress(percent);
        }
    }
}