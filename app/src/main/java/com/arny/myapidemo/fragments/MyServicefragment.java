package com.arny.myapidemo.fragments;

import android.app.AlertDialog;
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
import pw.aristos.arnylib.service.AbstractIntentService;
import pw.aristos.arnylib.service.OperationProvider;
import com.arny.myapidemo.services.Operations;
import com.arny.myapidemo.utils.BaseUtils;
import com.arny.myapidemo.utils.ToastMaker;

import java.util.HashMap;


public class MyServicefragment extends Fragment implements View.OnClickListener {

    private static final String DATA_SAVE_STATE = "data_save_update";
    private static final String TAG = MyServicefragment.class.getSimpleName();
    private Context context;
    private Button btnOper1;
    private TextView tvInfo1;
    private Button btnOper2;
    private Button btnOper3;
    private TextView tvInfo2;
    private TextView tvInfo3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);
        context = container.getContext();
        initUI(rootView);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void initUI(View rootView) {
        Log.i(TAG, "initUI: ");
        btnOper1 = (Button) rootView.findViewById(R.id.btnOper1);
        btnOper2 = (Button) rootView.findViewById(R.id.btnOper2);
        btnOper3 = (Button) rootView.findViewById(R.id.btnOper3);
        btnOper1.setOnClickListener(this);
        btnOper2.setOnClickListener(this);
        btnOper3.setOnClickListener(this);
        tvInfo1 = (TextView) rootView.findViewById(R.id.tvInfo1);
        tvInfo2 = (TextView) rootView.findViewById(R.id.tvInfo2);
        tvInfo3 = (TextView) rootView.findViewById(R.id.tvInfo3);
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
        IntentFilter filter = new IntentFilter(Operations.ACTION);
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
        public boolean success,operationFinished;
        public int operation;
        public String operationResult = "";
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                OperationProvider provider = new OperationProvider(extras);
                operationFinished = provider.isFinished();
                success = provider.isSuccess();
                operation = provider.getId();
                operationResult = provider.getResult();
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operation= " + operation);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operationFinished= " + operationFinished);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras success= " + success);
//                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras getOperationData= " + provider.getOperationData());
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operationResult= " + operationResult);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: time = " + BaseUtils.getDateTime());
            }
            if (operationFinished) {
                ToastMaker.toast(context,"Результат операции " + operation + ":"+ operationResult, success);
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOper1:
                Log.i(MyServicefragment.class.getSimpleName(), "onClick:1 time = " + BaseUtils.getDateTime());
                AbstractIntentService.onStartOperation(context,Operations.EXTRA_KEY_TYPE_SYNC,1,null);
                break;
            case R.id.btnOper2:
                Log.i(MyServicefragment.class.getSimpleName(), "onClick:2 time = " + BaseUtils.getDateTime());
                AbstractIntentService.onStartOperation(context,Operations.EXTRA_KEY_TYPE_SYNC,2,null);
                break;
            case R.id.btnOper3:
                Log.i(MyServicefragment.class.getSimpleName(), "onClick:3 time = " + BaseUtils.getDateTime());
                HashMap<String, Object> map3 = new HashMap<>();
                map3.put("key05", 4);
                AbstractIntentService.onStartOperation(context,Operations.EXTRA_KEY_TYPE_ASYNC,3,map3);
                break;
        }
    }
}