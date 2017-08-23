package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidnetworking.common.Method;
import com.arny.arnylib.network.*;
import com.arny.arnylib.interfaces.OnStringRequestResult;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.Test;
import com.google.gson.JsonArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener  {

	private JSONObject params,headers;
	private String tesla_url = "http://is.rtest.tesla.aristos.pw/";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netwok_activity);
	    initUI();
    }

	private void initUI() {
		initToolbar();
		Button btnGET, btnPOST,btnPUT;
		btnGET = (Button) findViewById(R.id.btnGETRequest);
		btnGET.setOnClickListener(this);
		btnPOST = (Button) findViewById(R.id.btnPOSTRequest);
		btnPOST.setOnClickListener(this);
		btnPUT = (Button) findViewById(R.id.btnPUTRequest);
		btnPUT.setOnClickListener(this);
		try {
			params = new JSONObject();
			headers = new JSONObject();
			params.put("email","test@test.ru");
			params.put("password","123456");
			headers.put("Accept","application/json");
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
			case R.id.btnPOSTRequest:
				NetworkService.apiRequest(NetworkActivity.this,Method.POST, tesla_url, params,headers, new OnStringRequestResult() {
					@Override
					public void onSuccess(String result) {
					}

					@Override
					public void onError(String error) {
						Log.i(NetworkActivity.class.getSimpleName(), "onError: error = " + error);
					}
				});
				break;
			case R.id.btnGETRequest:
                String url = "http://beta.json-generator.com/api/json/get/VyCG5xB_7";
                ApiRequest.apiResponse(this, url, null, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object response) {
                        Test post  = ApiUtils.getResponse(response, Test.class);
//                        ArrayList<Test> tests = ApiUtils.convertArray(posts, Test.class);
//                        for (Test post : tests) {
                            Log.i(NetworkActivity.class.getSimpleName(), "onResponse:"+post.getClass().getSimpleName()+" post = " + post );
//                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(NetworkActivity.class.getSimpleName(), "onErrorResponse: " + ApiUtils.getVolleyError(error));
                    }
                });
                break;
			case R.id.btnPUTRequest:
				NetworkService.apiRequest(NetworkActivity.this,Method.PUT, tesla_url,  params,headers, new OnStringRequestResult() {
					@Override
					public void onSuccess(String result) {
					}

					@Override
					public void onError(String error) {
						Log.i(NetworkActivity.class.getSimpleName(), "onError: error = " + error);
					}
				});
				break;
		}
	}

}
