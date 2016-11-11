package com.arny.myapidemo.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arny.myapidemo.R;
import com.arny.myapidemo.helpers.Funcs;
import com.arny.myapidemo.net.DataLoader;

import static com.arny.myapidemo.helpers.Consts.INTENT_NAME_TEST;
import static com.arny.myapidemo.helpers.Consts.TAG;
import static com.arny.myapidemo.helpers.Consts.TEST_STRING_DATA;

public class MyNewLoader extends Activity implements LoaderManager.LoaderCallbacks<String> {
    private static final int LOADER_ID = 101;
    TextView loaderRes;
    Button btnDataLoader;
    private String dataRes = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mynewloader_layout);
        loaderRes = (TextView) findViewById(R.id.loaderRes);
        btnDataLoader = (Button) findViewById(R.id.btnDataLoader);
        btnDataLoader.setOnClickListener(onBtnListener);
        Bundle bndl = new Bundle();
        bndl.putString(INTENT_NAME_TEST, TEST_STRING_DATA);
        getLoaderManager().initLoader(LOADER_ID, bndl, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: dataRes = " + dataRes);
        if (dataRes!=null) {
            loaderRes.setText(dataRes);
        }
    }

    View.OnClickListener onBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                Loader<String> loader;
//            loader = getLoaderManager().getLoader(LOADER_ID);
                Bundle bndl = new Bundle();
                bndl.putString(INTENT_NAME_TEST, TEST_STRING_DATA);
                loader = getLoaderManager().restartLoader(LOADER_ID, bndl, MyNewLoader.this);
                loader.forceLoad();
                Log.i(TAG, "onClick: loader started datetime = " + Funcs.getInstance().getCurrentDateTimeSec());
                Toast.makeText(MyNewLoader.this, loader.hashCode() + " loader started datetime = " + Funcs.getInstance().getCurrentDateTimeSec(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> loader = null;
        if (id == LOADER_ID) {
            loader = new DataLoader(this, args);
            Log.i(TAG, "onCreateLoader: " + loader.hashCode());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.i(TAG, "onLoadFinished for loader " + loader.hashCode() + ", result = " + data);
        dataRes = data;
        loaderRes.setText(dataRes);
        Toast.makeText(this, loader.hashCode() + " loader finished datetime = " + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.i(TAG, "onLoaderReset for loader " + loader.hashCode());
        Toast.makeText(this, loader.hashCode() + " onLoaderReset", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onLoaderReset: dataRes = " + dataRes);
    }
}