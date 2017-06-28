package com.arny.myapidemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.androidnetworking.common.Method;
import com.arny.arnylib.network.AndroidNetworkService;
import com.arny.arnylib.network.OnStringRequestResult;
import com.arny.myapidemo.R;
import com.arny.myapidemo.services.Operations;
import org.json.JSONArray;

public class NetworkActivity extends AppCompatActivity  {
    Button stringLoaderBtn,jsonLoaderBtn;
    TextView tv;
    private Toolbar toolbar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netwok_activity);

        intent = new Intent(NetworkActivity.this, Operations.class);
        tv = (TextView) findViewById(R.id.textView1);
        stringLoaderBtn = (Button) findViewById(R.id.buttonLoadString);
        stringLoaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            AndroidNetworkService.apiBuildRequest("http://beta.json-generator.com/api/json/get/4J2sIi6Tf", Method.GET, null, new OnStringRequestResult() {
		            @Override
		            public void onSuccess(String result) {
			            try {
				            JSONArray array = new JSONArray(result);
				            Log.i(NetworkActivity.class.getSimpleName(), "onSuccess: array = " + array);
			            } catch (Exception e) {
				            e.printStackTrace();
			            }
		            }

		            @Override
		            public void onError(String error) {
			            Log.i(NetworkActivity.class.getSimpleName(), "onError: error = " + error);
		            }
	            });
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
}
