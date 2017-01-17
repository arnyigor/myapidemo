package com.arny.myapidemo.model.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arny.myapidemo.R;
import com.arny.myapidemo.model.helpers.Consts;
import org.json.JSONObject;

public class AsyncTaskActivity extends Activity {
	private static final String TAG = "LOG_TAG";
	MyTask mt;
	TextView tv;
	private Context cnx = this;
	private ProgressDialog progress;
	private String JSON_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynctask_activity);
		Log.i(TAG, "create MainActivity: " + this.hashCode());
		tv = (TextView) findViewById(R.id.tvAsyncTask);
		progress = new ProgressDialog(this);
		progress.setTitle("Loading...");
		progress.setCancelable(false);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mt = (MyTask) getLastNonConfigurationInstance();
		if (mt == null) {
			mt = new MyTask();
			mt.execute();
		}
		// передаем в MyTask ссылку на текущее MainActivity
		mt.link(this);
		Log.i(TAG, "create MyTask: " + mt.hashCode());
	}

	public Object onRetainNonConfigurationInstance() {
		// удаляем из MyTask ссылку на старое MainActivity
		mt.unLink();
		return mt;
	}

	class MyTask extends AsyncTask<String, Integer, Void> {
		AsyncTaskActivity activity;
		// получаем ссылку на MainActivity
		void link(AsyncTaskActivity act) {
			activity = act;
		}

		// обнуляем ссылку
		void unLink() {
			activity = null;
		}

		@Override
		protected Void doInBackground(String... params) {
			VolleyGet("http://arnyjson.esy.es/jsonreq.php?json=get");
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			activity.tv.setText("i = " + values[0]);
		}
	}

	private String UrlInit(String town) {
		return JSON_BASE_URL + town + "&units=metric&APPID=" + Consts.OPEN_WEATHER_APIID;
	}

	private void VolleyGet(String url) {
		RequestQueue queue = Volley.newRequestQueue(cnx);
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
			new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.i(TAG, "onResponse response = " + String.valueOf(response));
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
				Toast.makeText(AsyncTaskActivity.this, R.string.str_eror_response, Toast.LENGTH_SHORT).show();
			}
		});
		jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(6000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(jsObjRequest);
	}
}