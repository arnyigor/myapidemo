package com.arny.myapidemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.arny.myapidemo.R;

import pw.aristos.arnylib.network.MyResultReceiver;
import com.arny.myapidemo.services.Operations;

import static android.app.DownloadManager.*;

public class NetworkActivity extends AppCompatActivity  implements MyResultReceiver.Receiver {
    Button stringLoaderBtn,jsonLoaderBtn;
    TextView tv;
    private Toolbar toolbar;
    private MyResultReceiver mReceiver;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netwok_activity);

        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        intent = new Intent(NetworkActivity.this, Operations.class);
        tv = (TextView) findViewById(R.id.textView1);
        stringLoaderBtn = (Button) findViewById(R.id.buttonLoadString);
        stringLoaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Operations.onStartOperation(NetworkActivity.this,Operations.EXTRA_KEY_TYPE_SYNC,2,null);
            }
        });
        jsonLoaderBtn = (Button) findViewById(R.id.buttonJSONRequest);
        jsonLoaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();
//        IntentFilter filter = new IntentFilter(Operations.ACTION);
//        filter.addCategory(Intent.CATEGORY_DEFAULT);
//        LocalBroadcastManager.getInstance(this).registerReceiver(updateReciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mReceiver.setReceiver(null); // clear receiver so no leaks.
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReciever);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.title_tabs));
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Log.d(NetworkActivity.class.getSimpleName(), "onReceiveResult: code = " +resultCode + " resultData = " + resultData.get("result") );
        switch (resultCode) {
            case STATUS_RUNNING:
                //show progress
                break;
            case STATUS_SUCCESSFUL:
                break;
            case STATUS_FAILED:
                // handle the error;
                break;
        }
    }
}
