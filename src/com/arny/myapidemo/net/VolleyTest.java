package com.arny.myapidemo.net;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arny.myapidemo.R;
import com.arny.myapidemo.helpers.Consts;
import org.json.JSONException;
import org.json.JSONObject;
public class VolleyTest extends AppCompatActivity {
	private String name = "ay";
	private int pageNum = 1;
	private int pageSize = 25;
	private String pageParam = "&page=" + pageNum + "&per_page=" + pageSize;
	private String JSON_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	String url = JSON_BASE_URL + "&units=metric&APPID=" + Consts.OPEN_WEATHER_APIID;
	private String JSON_URL = JSON_BASE_URL + name + pageParam;
	private Button buttonGet;
	private Context cnx = this;
	private ProgressDialog progress;
	private EditText edtWeatherTown;
	private TextView tvCoordLong, tvCoordLat,tvPlaceName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volley_activity);
		buttonGet = (Button) findViewById(R.id.buttonGet);
		edtWeatherTown = (EditText) findViewById(R.id.edtWeatherTown);
		tvCoordLong = (TextView) findViewById(R.id.tvCoordLong);
		tvCoordLat = (TextView) findViewById(R.id.tvCoordLat);
		tvPlaceName = (TextView) findViewById(R.id.tvPlaceName);
//		volleyLv = (ListView) findViewById(R.id.volleyLv);
		progress = new ProgressDialog(this);
		progress.setTitle("Loading...");
//		progress.setMessage("Wait!!");
		progress.setCancelable(false);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		buttonGet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(Consts.TAG, "onClick url = " + url);
				String town = edtWeatherTown.getText().toString();
				VolleyGet(UrlInit(town));
			}
		});

		/*volleyLv.setOnScrollListener(new AbsListView.OnScrollListener() {
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
		});*/
	}

	private String UrlInit(String town) {
		return JSON_BASE_URL + town + "&units=metric&APPID=" + Consts.OPEN_WEATHER_APIID;
	}

	private void VolleyGet(String url) {
		progress.show();
		RequestQueue queue = Volley.newRequestQueue(cnx);
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				showJSON(response);
				progress.dismiss();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				progress.dismiss();
				error.printStackTrace();
				Toast.makeText(VolleyTest.this, R.string.str_eror_response, Toast.LENGTH_SHORT).show();
			}
		});
		queue.add(jsObjRequest);
	}

	private void showJSON(JSONObject json) {
		Log.i(Consts.TAG, "onResponse json" + json.toString());
		try {
			String cod = json.getString("cod");
			if (cod.equals("404")) {
				String message = json.getString("message");
				Toast.makeText(VolleyTest.this, message, Toast.LENGTH_SHORT).show();
			} else {
				JSONObject jsonCoord = json.getJSONObject("coord");
				String placeName = json.getString("name");
				double lon = jsonCoord.getDouble("lon");
				double lat = jsonCoord.getDouble("lat");
				Log.i(Consts.TAG, "onResponse lon = " + lon);
				Log.i(Consts.TAG, "onResponse lat = " + lat);
				Log.i(Consts.TAG, "onResponse placeName = " + placeName);
				tvCoordLong.setText(String.valueOf(lon));
				tvCoordLat.setText(String.valueOf(lat));
				tvPlaceName.setText(placeName);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		Log.i(Consts.TAG, "onResume ");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i(Consts.TAG, "onPause ");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.i(Consts.TAG, "onRestart ");
		super.onRestart();
	}
}
