package com.arny.myapidemo.activities;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.loaders.RandomLoader;
import static com.arny.myapidemo.helpers.Consts.TAG;

public class RandomLoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {


    private TextView mResultTxt;
    private Bundle mBundle;
    public static final int LOADER_RANDOM_ID = 1;
    private Loader<String> mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_activity);

        mResultTxt = (TextView) findViewById(R.id.resultTxt);

        mBundle = new Bundle();
        mBundle.putString(RandomLoader.ARG_WORD, "test");
        mLoader = getSupportLoaderManager().initLoader(LOADER_RANDOM_ID, mBundle, this);
        //getLoaderManager().initLoader(LOADER_RANDOM_ID, mBundle, this);
    }

    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> mLoader = null;
        // условие можно убрать, если вы используете только один загрузчик
        if (id == LOADER_RANDOM_ID) {
            mLoader = new RandomLoader(this, args);
            Log.i(TAG, "onCreateLoader");
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

    public void startLoad(View v) {
        Log.i(TAG, "startLoad");
        mLoader.onContentChanged();
    }
}