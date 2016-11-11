package com.arny.myapidemo.activities;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.net.TimeLoader;
import static com.arny.myapidemo.helpers.Consts.TAG;

public class MyLoaderActivity extends Activity implements LoaderCallbacks<String> {
    static final int LOADER_TIME_ID = 1;
    TextView tvTime;
    RadioGroup rgTimeFormat;
    static int lastCheckedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myloader);
        tvTime = (TextView) findViewById(R.id.tvTime);
        rgTimeFormat = (RadioGroup) findViewById(R.id.rgTimeFormat);
        Bundle bndl = new Bundle();
        bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());
        getLoaderManager().initLoader(LOADER_TIME_ID, bndl, this);
        lastCheckedId = rgTimeFormat.getCheckedRadioButtonId();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> loader = null;
        if (id == LOADER_TIME_ID) {
            loader = new TimeLoader(this, args);
            Log.i(TAG, "onCreateLoader: " + loader.hashCode());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String result) {
        Log.i(TAG, "onLoadFinished for loader " + loader.hashCode() + ", result = " + result);
        tvTime.setText(result);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.i(TAG, "onLoaderReset for loader " + loader.hashCode());
    }

    public void getTimeClick(View v) {
        Loader<String> loader;
        int id = rgTimeFormat.getCheckedRadioButtonId();
        if (id == lastCheckedId) {
            loader = getLoaderManager().getLoader(LOADER_TIME_ID);
        } else {
            Bundle bndl = new Bundle();
            bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());
            loader = getLoaderManager().restartLoader(LOADER_TIME_ID, bndl, this);
            lastCheckedId = id;
        }
        loader.forceLoad();
    }

    String getTimeFormat() {
        String result = TimeLoader.TIME_FORMAT_SHORT;
        switch (rgTimeFormat.getCheckedRadioButtonId()) {
            case R.id.rdShort:
                result = TimeLoader.TIME_FORMAT_SHORT;
                break;
            case R.id.rdLong:
                result = TimeLoader.TIME_FORMAT_LONG;
                break;
        }
        return result;
    }

    public void observerClick(View v) {
    }
}