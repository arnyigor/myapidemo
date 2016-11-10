package com.arny.myapidemo.net;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.ParseJSON;
import com.arny.myapidemo.adapters.VolleyList;
import com.arny.myapidemo.helpers.Consts;
import org.json.JSONObject;

public class GitHubActivity extends AppCompatActivity {
	private String name = "ay";
	private int pageNum = 1;
	private int pageSize = 25;
	private String pageParam = "&page="+pageNum+"&per_page=" + pageSize;
	private String JSON_BASE_URL = "https://api.github.com/search/users?q=";
	private String JSON_URL = JSON_BASE_URL+name+pageParam;
	String url = "http://api.openweathermap.org/data/2.5/weather?q=Moscow&units=metric&APPID=" + Consts.OPEN_WEATHER_APIID;
	private Button buttonGet;
	private Context cnx = this;
	private ProgressDialog progress;
	private ListView volleyLv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.github_activity);
		buttonGet = (Button) findViewById(R.id.buttonGet);
		volleyLv = (ListView) findViewById(R.id.listView);
		progress = new ProgressDialog(this);
//		progress.setTitle("Please Wait!!");
		progress.setMessage(getResources().getString(R.string.str_progress_wait));
		progress.setCancelable(false);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		buttonGet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(Consts.TAG, "onClick url = " + url);
				VolleyGet(UrlInit(1));
			}
		});

		volleyLv.setOnScrollListener(new AbsListView.OnScrollListener() {
			private int mtotalItemCount;
			private int mfirstVisibleItem = 1;
			private int mLastFirstVisibleItem = 0;
			private int mvisibleItemCount = pageSize;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				Log.i(Consts.TAG, "onScrollStateChanged mfirstVisibleItem = " + mfirstVisibleItem);
				Log.i(Consts.TAG, "onScrollStateChanged mvisibleItemCount = " + mvisibleItemCount);
				Log.i(Consts.TAG, "onScrollStateChanged mtotalItemCount = " + mtotalItemCount);
				Log.i(Consts.TAG, "onScrollStateChanged mLastFirstVisibleItem = " + mLastFirstVisibleItem);

				if (mfirstVisibleItem== mLastFirstVisibleItem){
					Log.i(Consts.TAG,"SCROLLING DOWN");
					int sum = mfirstVisibleItem + mvisibleItemCount;
					Log.i(Consts.TAG, "onScrollStateChanged sum = " + sum);
					Log.i(Consts.TAG, "onScrollStateChanged pageSize = " + pageSize);
					if (sum >=pageSize){
						pageNum++;
						Log.i(Consts.TAG, "onClick JSON_URL = " + UrlInit(pageNum));
						VolleyGet(UrlInit(pageNum));
					}
				}
				if(mLastFirstVisibleItem<mfirstVisibleItem)
				{
					Log.i(Consts.TAG,"SCROLLING down");
				}
				if(mLastFirstVisibleItem>mfirstVisibleItem)
				{
					Log.i(Consts.TAG,"SCROLLING UP");
				}
				mLastFirstVisibleItem=mfirstVisibleItem;
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				mfirstVisibleItem = firstVisibleItem;
				mvisibleItemCount = visibleItemCount;
				mtotalItemCount = totalItemCount;
			}
		});
	}

	private String UrlInit(int pNum){
		String pParam = "&page="+pNum+"&per_page=" + pageSize;
		return JSON_BASE_URL+name+pParam;
	}

	private void VolleyGet(String url){
		progress.show();
		RequestQueue queue = Volley.newRequestQueue(cnx);
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
//				Log.i(Consts.TAG, "onResponse response" + response.toString());
				showJSON(response.toString());
				progress.dismiss();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				progress.dismiss();
				error.printStackTrace();
				Toast.makeText(GitHubActivity.this, R.string.str_eror_response, Toast.LENGTH_SHORT).show();
			}
		});
		queue.add(jsObjRequest);
	}

	private void showJSON(String json){
		ParseJSON pj = new ParseJSON(json);
		pj.parseJSON();
		VolleyList cl = new VolleyList(this, ParseJSON.ids,ParseJSON.logins,ParseJSON.repos);
		volleyLv.setAdapter(cl);
	}

}
