package com.arny.myapidemo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.arny.arnylib.loaders.ITaskLoaderListener;
import com.arny.arnylib.loaders.SampleTask;
import com.arny.myapidemo.R;

public class LoaderActivity extends AppCompatActivity implements ITaskLoaderListener {
	private static final String TAG = "LoaderActivity";
	private static final int TASK_ID = 1;
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
		SampleTask.execute(this, this);
	}

	@Override
	public void onCancelLoad() {
		Log.d(TAG, "task canceled");
	}

	@Override
	public void onLoadFinished(Object data) {
		if(data!=null && data instanceof String){
			Log.d(TAG, "task result: " + data);
		}
	}
}