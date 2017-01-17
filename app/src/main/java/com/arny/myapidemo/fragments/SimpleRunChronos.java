package com.arny.myapidemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.MyOperation;
import com.redmadrobot.chronos.gui.fragment.dialog.ChronosSupportDialogFragment;

public class SimpleRunChronos extends ChronosSupportDialogFragment {
    private Context context;
    private Button button;
    private TextView chrTv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chronos_run, null);
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
    }

    private void initUI(View v) {
        button = (Button) v.findViewById(R.id.chrBtn);
        chrTv = (TextView) v.findViewById(R.id.chrTv);
    }

    public void onOperationFinished(final MyOperation.Result result) {
        //Here you process the result
        if (result
                .isSuccessful()) { // this case happens when no exception was thrown during the operation run
            chrTv.setText(result.getOutput());
        } else { // this happens if there was an exception
            chrTv.setText(result.getErrorMessage());
        }
    }
}
