package com.arny.myapidemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.MyOperation;
import com.redmadrobot.chronos.gui.fragment.dialog.ChronosSupportDialogFragment;

public class MyChronosFragment extends ChronosSupportDialogFragment {
    // a key by which the activity saves and restores already loaded data
    private final static String KEY_DATA = "data";
    // a tag which represents a group of operations that can't run simultaneously
    private final static String TAG_DATA_LOADING = "data_loading";
    final String TAG = "LOG_TAG";
    // a data that has to be loaded
    private String mData = null;
    private Context context;
    private Button button;
    private TextView chrTv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chronos, null);
        context = container.getContext();
        initUI(v);
        initListeners();
        if (savedInstanceState != null) {
            //first of all, the activity tries to restored already loaded data
            mData = savedInstanceState.getString(KEY_DATA);
        }
        return v;
    }

    private void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "Button click in MyChronosFragment");
            }
        });
    }

    private void initUI(View v) {
        button = (Button) v.findViewById(R.id.chrBtn);
        chrTv = (TextView) v.findViewById(R.id.chrTv);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mData == null) {// if it is still no data
            // The activity launches a loading operations with a tag
            // so that if it comes to this point once again and the data is not loaded yet,
            // the next launch will be ignored.
            // That means, no matter now often user rotates the device,
            // only one operation with a given tag may be pending in a single moment of time.
            runOperation(new MyOperation(""), TAG_DATA_LOADING);
        } else {
            // If there is a data already, just show it;
            showData();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        // It's important to manually save loaded data, as for now Chronos doesn't have an built-in cache
        outState.putString(KEY_DATA, mData);
    }

    private void showData() {
        chrTv.setText("Data is '" + mData + "'");
    }

    public void onOperationFinished(final MyOperation.Result result) {
        if (result.isSuccessful()) {
            // After the activity got the data, it is being saved to a local variable.
            // The programmer should take care of saving it during activity destroy-recreation process.
            mData = result.getOutput();
            showData();
        } else {
            // Here the negative result is not stored, so it
            chrTv.setText(result.getErrorMessage());
        }
    }
}