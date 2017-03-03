package com.arny.myapidemo.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.services.MyIntentService;

import static com.arny.myapidemo.models.Consts.TAG;

public class MyServicefragment extends Fragment {
    private static final String DATA_SAVE_STATE = "data_save_update";
    private Context context;
    private Button btnServStart;
    private TextView tvServInfo;
    private int update;
    private boolean finish;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);
        context = container.getContext();
        initUI(rootView);
        initListeners();
        if (savedInstanceState == null) {
            update = 0;
        } else {
            update = savedInstanceState.getInt(DATA_SAVE_STATE, 0);
        }
        tvServInfo.setText(String.valueOf(update));
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DATA_SAVE_STATE,update);
    }


    // Launching the service
    public void onStartService(View v) {
        Log.i(TAG, "onStartService: ");
        Intent i = new Intent(context, MyIntentService.class);
        context.startService(i.putExtra(MyIntentService.EXTRA_KEY_UPDATE_TOTAL, 50));
    }

    private void initUI(View rootView) {
        Log.i(TAG, "initUI: ");
        btnServStart = (Button) rootView.findViewById(R.id.btnServStart);
        tvServInfo = (TextView) rootView.findViewById(R.id.tvServInfo);
        pDialog = new ProgressDialog(context);
    }

    private void initListeners() {
        Log.i(TAG, "initListeners: ");
        btnServStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartService(view);
            }
        });
    }

    private void showDialog(String result) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(result);
        dlg.setNegativeButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = dlg.create();
        alert.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        IntentFilter filter = new IntentFilter(MyIntentService.ACTION_UPDATE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(context).registerReceiver(updateReciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        LocalBroadcastManager.getInstance(context).unregisterReceiver(updateReciever);
    }


    private BroadcastReceiver updateReciever = new BroadcastReceiver() {
        public boolean success;
        public int percent;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: intent = " + intent.toString());
            Log.i(TAG, "onReceive: pDialog = " + pDialog);
            try {
                update = intent.getIntExtra(MyIntentService.EXTRA_KEY_UPDATE, 1);
                finish = intent.getBooleanExtra(MyIntentService.EXTRA_KEY_FINISH, false);
                success = intent.getBooleanExtra(MyIntentService.EXTRA_KEY_FINISH_SUCCESS, false);
                percent = intent.getIntExtra(MyIntentService.EXTRA_KEY_UPDATE_PROGRESS, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!finish){
                String strProgress ="Loading... " + String.valueOf(percent) + " %";
                pDialog.setMessage(strProgress);
                pDialog.setCancelable(false);
                pDialog.show();
            }else{
                pDialog.dismiss();
                showDialog("Операция завершена");
            }
            tvServInfo.setText(String.valueOf(update));
        }
    };
}