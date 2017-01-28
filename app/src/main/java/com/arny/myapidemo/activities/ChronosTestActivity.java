package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.helpers.SimpleOperation;
import com.redmadrobot.chronos.ChronosConnector;
import com.redmadrobot.chronos.gui.activity.ChronosAppCompatActivity;

public class ChronosTestActivity extends ChronosAppCompatActivity {
    private static final String TAG = "LOG_TAG";
    private static final String KEY_DATA = "KEY_DATA";
    private static final String EXTRA_DATA = "EXTRA_DATA";
    private final static String TAG_DATA_LOADING = "data_loading";
    private final ChronosConnector mConnector = new ChronosConnector();
    private TextView mTextOutput;
    private Button btnRunOper;
    private String mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronos_test);
        if (savedInstanceState != null) {
            mData = savedInstanceState.getString(KEY_DATA);
        }
        btnRunOper = (Button) findViewById(R.id.btnRunOper);
        mTextOutput = (TextView) findViewById(R.id.mTextOutput);
        btnRunOper.setOnClickListener(btnStartListener);
    }

    View.OnClickListener btnStartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "onClick: btnStartListener");
            runOperationBroadcast(new SimpleOperation(EXTRA_DATA),TAG_DATA_LOADING);
//            final int firstRunId = runOperation(new SimpleOperation(EXTRA_DATA),TAG_DATA_LOADING);
        }
    };

    public void onOperationFinished(final SimpleOperation.Result result) {
        Log.i(TAG, "onOperationFinished: result = " + result);
        if (result.isSuccessful()) {
            mData = result.getOutput();
            Log.i(TAG, "onOperationFinished: isSuccessful mData = " + mData);
            showData(mData);
        } else {
            Log.i(TAG, "onOperationFinished: getErrorMessage getOutput = " + result.getOutput());
            mTextOutput.setText(result.getErrorMessage());
        }
    }

    //    @Override
        //    public void onBackPressed() {
        //        Log.i(TAG, "onBackPressed: ");
        //        cancelOperation(TAG_DATA_LOADING);
        //        finish();
        //    }


    private void showData(String data) {
        mTextOutput.setText("Data is '" + data + "'");
    }

    private void showDataLoadError(Exception exception) {
        Log.i(TAG, "showDataLoadError: ");
        exception.printStackTrace();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: mData = " + mData);
        if (mData == null) {
//            runOperation(new SimpleOperation(EXTRA_DATA),TAG_DATA_LOADING);
            showData("Данных нет");
        } else {
            showData(mData);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
        outState.putString(KEY_DATA, mData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: savedInstanceState = " + savedInstanceState);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }
}
