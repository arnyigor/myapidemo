package com.arny.myapidemo.activities;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.loaders.MyLoader;
import static com.arny.myapidemo.helpers.Consts.TAG;

public class RandomLoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private TextView mResultTxt;
    private Button initLoader;
    private Bundle mBundle;
    public static final int LOADER_RANDOM_ID = 1;
    private Loader<String> mLoader;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_activity);
        mResultTxt = (TextView) findViewById(R.id.resultTxt);
        initLoader = (Button) findViewById(R.id.initLoader);
        mBundle = new Bundle();
        mBundle.putString(MyLoader.ARG_WORD, "test");
        mLoader = getSupportLoaderManager().initLoader(LOADER_RANDOM_ID, mBundle, new RandomLoaderActivity());
        Log.i(TAG, "onCreate: ");
        initLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");

            }
        });
    }

    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader: id = " + id + " args = " + args.toString());
        // условие можно убрать, если вы используете только один загрузчик
            if (id == LOADER_RANDOM_ID) {
                return  new MyLoader(this);
            }
        return null;
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