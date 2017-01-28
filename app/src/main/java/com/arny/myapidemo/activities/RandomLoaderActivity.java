package com.arny.myapidemo.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.loaders.MyLoader;

public class RandomLoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final int LOADER_RANDOM_ID = 1;
    private static final String TAG = "LOG_TAG";
    private static final String KEY_DATA = "KEY_DATA";
    private TextView mResultTxt;
    private Button btnInitLoader;
    private Bundle mBundle;
    private Loader<String> mLoader;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_activity);
        context = this;
        Log.i(TAG, "onCreate: context = " + context);
        initUI();
        initListeners();
    }

    private void initUI() {
        mResultTxt = (TextView) findViewById(R.id.resultTxt);
        btnInitLoader = (Button) findViewById(R.id.initLoader);
    }

    private void initListeners() {
        btnInitLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: mLoader = " + mLoader);
                if (mLoader == null) {
                    mBundle = new Bundle();
                    mBundle.putString(MyLoader.EXTRA_LOADER_STRING, "test");
                    mLoader = getLoaderManager().initLoader(LOADER_RANDOM_ID, mBundle, RandomLoaderActivity.this);
                } else {
                    mLoader.onContentChanged();
                }
            }
        });
    }

    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> mLoader = null;
        Log.i(TAG, "onCreateLoader: mLoader = " + mLoader);
        switch (id) {
            case LOADER_RANDOM_ID:
                mLoader = new MyLoader(this, args);
                break;
        }
        Log.i(TAG, "onCreateLoader: mLoader = " + mLoader);
        return mLoader;
    }

    public void onLoadFinished(Loader<String> loader, String data) {
        Log.i(TAG, "onLoadFinished");
        mResultTxt.setText(data);
    }

    public void onLoaderReset(Loader<String> loader) {
        Log.i(TAG, "onLoaderReset");
    }

    public void startLoad(View v) {
        Log.i(TAG, "startLoad");
        mLoader.onContentChanged();
    }
}