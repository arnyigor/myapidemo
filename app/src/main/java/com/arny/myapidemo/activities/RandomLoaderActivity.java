package com.arny.myapidemo.activities;

import android.app.LoaderManager.LoaderCallbacks;
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

public class RandomLoaderActivity extends AppCompatActivity implements LoaderCallbacks<String> {
    public static final int LOADER_RANDOM_ID = 1;
    private static final String TAG = "LOG_TAG";
    private TextView mResultTxt;
    private Button initLoader;
    private Bundle mBundle;
    private Loader<String> mLoader;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_activity);
        mResultTxt = (TextView) findViewById(R.id.resultTxt);
        initLoader = (Button) findViewById(R.id.initLoader);
        mBundle = new Bundle();
        mBundle.putString(MyLoader.EXTRA_LOADER_STRING, "test");
        mLoader = getLoaderManager().initLoader(LOADER_RANDOM_ID, mBundle, this);
        Log.i(TAG, "onCreate: ");
        initLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");

            }
        });
    }

    public Loader<String> onCreateLoader(int id, Bundle argsb) {
        // условие можно убрать, если вы используете только один загрузчик
        if (id == LOADER_RANDOM_ID) {
            mLoader = new MyLoader(this, argsb);
            Log.d(TAG, "onCreateLoader");
        }
        return mLoader;
    }

    public void onLoadFinished(Loader<String> loader, String data) {
        Log.i(TAG, "onLoadFinished");
        mResultTxt.setText(data);
    }

    public void onLoaderReset(Loader<String> loader) {
        Log.i(TAG, "onLoaderReset");
    }
}