package com.arny.myapidemo.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.MyServicefragment;
import com.arny.myapidemo.services.Operations;
import com.arny.myapidemo.utils.BaseUtils;
import com.arny.myapidemo.utils.ToastMaker;
import org.json.JSONObject;
import pw.aristos.arnylib.network.API;
import pw.aristos.arnylib.network.ApiService;
import pw.aristos.arnylib.network.MyResultReceiver;
import pw.aristos.arnylib.network.QueryService;
import pw.aristos.arnylib.service.OperationProvider;

import java.util.List;

import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;
import static android.app.DownloadManager.STATUS_FAILED;

public class NetworkActivity extends AppCompatActivity  implements MyResultReceiver.Receiver {
    Button stringLoaderBtn,jsonLoaderBtn;
    TextView tv;
    private Toolbar toolbar;

    private String test_url ="https://pik.ru/luberecky/singlepage?data=ChessPlan&id=2b3ecc9b-bfad-e611-9fbe-001ec9d5643c&private_key=1&format=json&domain=pik.ru";
    private Intent apiServiceIntent;
    private MyResultReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netwok_activity);

        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        apiServiceIntent = new Intent(this, ApiService.class);
        tv = (TextView) findViewById(R.id.textView1);
        stringLoaderBtn = (Button) findViewById(R.id.buttonLoadString);
        stringLoaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SYNC, null, NetworkActivity.this, QueryService.class);
                intent.putExtra("receiver", mReceiver);
                intent.putExtra("command", "query");
                startService(intent);
//                API.requestJSON(apiServiceIntent, NetworkActivity.this, Request.Method.GET, test_url, new JSONObject());
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

    private BroadcastReceiver updateReciever = new BroadcastReceiver() {
        public boolean success,operationFinished;
        public int operation;
        public String operationResult = "";
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                OperationProvider provider = (OperationProvider) extras.get(ApiService.EXTRA_KEY_OPERATION);
                operationFinished = provider.isFinished();
                success = provider.isSuccess();
                operation = provider.getId();
                operationResult = provider.getResult();
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operation= " + operation);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operationFinished= " + operationFinished);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras success= " + success);
//                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras getOperationData= " + provider.getOperationData());
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operationResult= " + operationResult);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: time = " + BaseUtils.getDateTime());
            }
            if (operationFinished) {
                ToastMaker.toast(context,"Результат операции " + operation + ":"+ operationResult, success);
            }

        }
    };

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
        switch (resultCode) {
            case STATUS_RUNNING:
                //show progress
                break;
            case STATUS_SUCCESSFUL:
                byte[] results = resultData.getByteArray("results");
                if (results != null) {
                    String result = new String(results);
                    Log.i(NetworkActivity.class.getSimpleName(), "onReceiveResult: result = " + result);
                }
                break;
            case STATUS_FAILED:
                // handle the error;
                break;
        }
    }
}
