package com.arny.myapidemo.activities;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.loaders.MyLoader;

public class LoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,View.OnClickListener {
    public static final int LOADER_LOADTASK_ID = 100;
    public static final int LOADER_CALC_ID = 101;
    private static final String TAG = LoaderActivity.class.getSimpleName();
    private TextView mResultTxt;
    private Button btnTask;
    private Bundle mBundle;
    private Loader<String> mLoader;
    private Context context;
    private ProgressDialog pDialog;
    private Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_activity);
        context = this;
        Log.i(TAG, "onCreate: context = " + context);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loaderTask = loaderManager.getLoader(LOADER_LOADTASK_ID);
        if (loaderTask != null) {
            loaderManager.initLoader(LOADER_LOADTASK_ID, null, this);
        }
        if (loaderTask != null) {
            Log.i(LoaderActivity.class.getSimpleName(), "onResume: loaderTask = " + loaderTask.hashCode() + " isStarted = " + loaderTask.isStarted());
            Log.i(LoaderActivity.class.getSimpleName(), "onResume: loaderTask = " + loaderTask.hashCode() + " isReset = " + loaderTask.isReset());
            Log.i(LoaderActivity.class.getSimpleName(), "onResume: loaderTask = " + loaderTask.hashCode() + " isAbandoned = " + loaderTask.isAbandoned());
        }
        Loader<String> loaderCalc = loaderManager.getLoader(LOADER_CALC_ID);
        if (loaderCalc != null) {
            loaderManager.initLoader(LOADER_CALC_ID, null, this);
        }
        if (loaderCalc != null) {
            Log.i(LoaderActivity.class.getSimpleName(), "onResume: loaderCalc = " + loaderCalc.hashCode() + " isStarted = " + loaderCalc.isStarted());
            Log.i(LoaderActivity.class.getSimpleName(), "onResume: loaderCalc = " + loaderCalc.hashCode() + " isReset = " + loaderCalc.isReset());
            Log.i(LoaderActivity.class.getSimpleName(), "onResume: loaderCalc = " + loaderCalc.hashCode() + " isAbandoned = " + loaderCalc.isAbandoned());
        }
    }

    private void initUI() {
        mResultTxt = (TextView) findViewById(R.id.resultLoad);
        btnTask = (Button) findViewById(R.id.loadTask);
        btnCalc = (Button) findViewById(R.id.calcTask);
        btnTask.setOnClickListener(this);
        btnCalc.setOnClickListener(this);
        initDialog();
    }

    private void initDialog() {
        pDialog = new ProgressDialog(this);
    }


    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MyLoader(this, args);
    }

    @Override
    protected void onDestroy() {
        hideProgress();
        super.onDestroy();
    }

    public void onLoadFinished(Loader<String> loader, String data) {
        Log.i(TAG, "onLoadFinished");
        mResultTxt.setText(data);
        hideProgress();
    }

    private void hideProgress() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public void onLoaderReset(Loader<String> loader) {
        Log.i(TAG, "onLoaderReset");
    }

    public void startLoad() {
        Log.i(TAG, "startLoad");
        showProgress("Загрузка...");
        mLoader.onContentChanged();
    }


    private void showProgress(String message) {
        Log.i(LoaderActivity.class.getSimpleName(), "showProgress: pdialog = " + pDialog);
        if (pDialog == null) {
            initDialog();
        }
        pDialog.setMessage(message);
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loadTask:
                initLoader(LOADER_LOADTASK_ID, MyLoader.LoaderOperation.load);
                break;
            case R.id.calcTask:
                initLoader(LOADER_CALC_ID, MyLoader.LoaderOperation.calc);
                break;
        }
        startLoad();
    }

    private void initLoader(int id,MyLoader.LoaderOperation operation) {
        Log.i(LoaderActivity.class.getSimpleName(), "initLoader: mLoader = " + mLoader);
        mBundle = new Bundle();
        mBundle.putSerializable(MyLoader.EXTRA_LOADER_OPERATION, operation);
        mLoader = getLoaderManager().initLoader(id, mBundle, this);
    }
}