package com.arny.myapidemo.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.arny.arnylib.loaders.RotationWeakAsyncTask;
import com.arny.arnylib.network.NetworkService;
import com.arny.arnylib.network.OnJSONRequestResult;
import com.arny.myapidemo.R;
import org.json.JSONException;
import org.json.JSONObject;

public class LoaderActivity extends AppCompatActivity {
	private static final String TAG = "LoaderActivity";
	private Context context;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
	    context = this;
	    Button bnt = (Button) findViewById(R.id.btnLoad);
        bnt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				doShowProgress();
			}
		});
        
    }


	protected void doShowProgress() {
		RotationWeakAsyncTask.startNewTask(new RotationWeakAsyncTask(this, "res") {
			public void run() {
				SystemClock.sleep(10000);
				NetworkService.apiRequest(LoaderActivity.this, "http://dev.aristos.pw:5055?id=test&timestamp=1497539544&lat=55.68143089&lon=37.58964666&speed=0.0&bearing=0.0&altitude=210.0&batt=100.0&emei=353498080599854", new JSONObject(), new OnJSONRequestResult() {
					@Override
					public void onResult(JSONObject object) {
						Log.i(LoaderActivity.class.getSimpleName(), "onResult: object = " + object);
					}

					@Override
					public void onError(String error) {
						Log.i(LoaderActivity.class.getSimpleName(), "onError: error = " + error);
					}
				});
			}
		}, new RotationWeakAsyncTask.OnTaskFinishedListener() {
			@Override
			public void onTaskFinished(int i) {
				Log.i(LoaderActivity.class.getSimpleName(), "onTaskFinished: i = " + i);
			}
		}, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		RotationWeakAsyncTask.setContext(this, null);
	}

	public void onPause() {
		super.onPause();
		RotationWeakAsyncTask.resetContext();
	}


}