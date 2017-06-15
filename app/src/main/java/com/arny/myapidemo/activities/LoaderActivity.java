package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.arny.arnylib.loaders.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.utils.ToastMaker;

public class LoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
	private static final String TAG = "LoaderActivity";
	public static final int MY_ID_LOADER = 1;
	private LoaderActivity instance;
	private boolean loaderRunning = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loader);
		instance = this;
		Button bnt = (Button) findViewById(R.id.btnLoad);
		Button bntCanc = (Button) findViewById(R.id.btnCancel);
		initLoaderManager();
		bntCanc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loaderRunning = AbstractLoader.checkLoaderRun(getSupportLoaderManager(), MY_ID_LOADER);
				if (loaderRunning) {
					getSupportLoaderManager().destroyLoader(MY_ID_LOADER);
					loaderRunning = false;
				}
				Log.i(LoaderActivity.class.getSimpleName(), "onClick: loaderRunning = " + loaderRunning);
			}
		});
		bnt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doShowProgress();
			}
		});
	}

	private void initLoaderManager() {
		Log.i(LoaderActivity.class.getSimpleName(), "initLoaderManager:" );
		// ---- magic lines starting here -----
		// call this to re-connect with an existing
		// loader (after screen configuration changes for e.g!)
		LoaderManager lm = getSupportLoaderManager();
		if (lm.getLoader(MY_ID_LOADER) != null) {
			loaderRunning = AbstractLoader.checkLoaderRun(lm, MY_ID_LOADER);
			if (loaderRunning) {
				ToastMaker.toastInfo(instance,"Лоадер работает");
			}
			lm.initLoader(MY_ID_LOADER, null, this);
		}
		// ----- end magic lines -----
	}

	public void doShowProgress() {
		if (!loaderRunning) {
			restartLoader();
		}
	}

	private void restartLoader() {
		// --------- the other magic lines ----------
		// call restart because we want the background work to be executed
		// again
		Log.d(TAG, "restartLoading(): re-starting loader");
		LoaderManager lm = getSupportLoaderManager();
		lm.restartLoader(MY_ID_LOADER, null, instance);
		loaderRunning = AbstractLoader.checkLoaderRun(lm, MY_ID_LOADER);
		// --------- end the other magic lines --------
	}

	@Override
	public Loader<String> onCreateLoader(int id, Bundle args) {
		return new MyLoader(instance,"init params");
	}

	@Override
	public void onLoadFinished(Loader<String> loader, String data) {
		Log.i(LoaderActivity.class.getSimpleName(), "onLoadFinished: data = " + data);
		loaderRunning = false;
		ToastMaker.toastInfo(LoaderActivity.this,"onLoadFinished "+data);
	}

	@Override
	public void onLoaderReset(Loader<String> loader) {
		Log.i(LoaderActivity.class.getSimpleName(), "onLoaderReset: loader = " + loader);
	}


}