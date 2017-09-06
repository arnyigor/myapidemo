package com.arny.myapidemo.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.MyServicefragment;
import com.arny.myapidemo.services.NetworkStateService;
import com.arny.myapidemo.services.Operations;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private JSONObject params, headers;
    private String tesla_url = "http://is.rtest.tesla.aristos.pw/";
    private Intent intent;
    private TextView tvNetworkStatus;
    private boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netwok_activity);
        intent = new Intent(this, NetworkStateService.class);
        Log.i(NetworkActivity.class.getSimpleName(), "onCreate: intent = " +intent);
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(NetworkStateService.ACTION_UPDATE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(updateReciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReciever);
    }

    private BroadcastReceiver updateReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(NetworkActivity.class.getSimpleName(), "onReceive: intent = " + intent);
            Log.i(NetworkActivity.class.getSimpleName(), "onReceive: intent = " + DroidUtils.dumpIntent(intent));
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String info = extras.getString(NetworkStateService.EXTRA_KEY_NETINFO) ;
                tvNetworkStatus.setText(info);
            }
        }
    };

    private void initUI() {
        initToolbar();
        Button btnGET, btnPOST, btnPUT;
        btnGET = (Button) findViewById(R.id.btnGETRequest);
        btnGET.setOnClickListener(this);
        btnPOST = (Button) findViewById(R.id.btnPOSTRequest);
        btnPOST.setOnClickListener(this);
        btnPUT = (Button) findViewById(R.id.btnPUTRequest);
        btnPUT.setOnClickListener(this);
        tvNetworkStatus = (TextView) findViewById(R.id.tv_network_status);
        findViewById(R.id.btn_start_network_service).setOnClickListener(this);
        findViewById(R.id.btn_stop_network_service).setOnClickListener(this);
        try {
            params = new JSONObject();
            headers = new JSONObject();
            params.put("email", "test@test.ru");
            params.put("password", "123456");
            headers.put("Accept", "application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewNetInfo();
    }

    private void viewNetInfo() {
        Context context = this;
        String text = DroidUtils.getNetworkInfo(context);
        tvNetworkStatus.setText(text);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.title_tabs));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_network_service:
                startService(intent);
                viewNetInfo();
                break;
            case R.id.btn_stop_network_service:
                stopService(intent);
                viewNetInfo();
                break;
            case R.id.btnGETRequest:
//                String url = "http://beta.json-generator.com/api/json/get/VyCG5xB_7";
//                ApiRequest.apiResponse(this, url, null, new Response.Listener<Object>() {
//                    @Override
//                    public void onResponse(Object response) {
//                        JsonArray posts  = ApiUtils.getResponse(response, JsonArray.class);
//                        ArrayList<Test> tests = ApiUtils.convertArray(posts, Test.class);
//                        for (Test test : tests) {
//                            Log.i(NetworkActivity.class.getSimpleName(), "onResponse:"+test.getClass().getSimpleName()+" post = " + test );
//                        }
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(NetworkActivity.class.getSimpleName(), "onErrorResponse: " + ApiUtils.getVolleyError(error));
//                    }
//                });
                break;
            case R.id.btnPUTRequest:
//				NetworkStateService.apiRequest(NetworkActivity.this,Method.PUT, tesla_url,  params,headers, new OnStringRequestResult() {
//					@Override
//					public void onSuccess(String result) {
//					}
//
//					@Override
//					public void onError(String error) {
//						Log.i(NetworkActivity.class.getSimpleName(), "onError: error = " + error);
//					}
//				});
                break;
        }
    }

}
