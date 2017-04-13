package com.arny.myapidemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractIntentService extends IntentService {
    /*Extras*/
    public static final String ACTION = "AbstractIntentService.action";
    public static final String EXTRA_KEY_OPERATION_CODE = "AbstractIntentService.operation.code";
    public static final String EXTRA_KEY_OPERATION_RESULT = "AbstractIntentService.operation.result";
    public static final String EXTRA_KEY_FINISH_SUCCESS = "AbstractIntentService.operation.success";
    public static final String EXTRA_KEY_OPERATION_DATA = "AbstractIntentService.operation.data";
    /*other*/
    protected static int operation;
    protected boolean mIsSuccess;
    protected HashMap<String,Object> resultData;
    private ArrayList<Integer> operationsQueue;

    protected abstract void runOperation(int operation);
    protected abstract String getSuccessResult(int operation);
    protected abstract String getFailResult(int operation);

    protected static int getOperation(){
        return operation;
    }

    protected void setOperation(int operation){
        Log.i(AbstractIntentService.class.getSimpleName(), "setOperation: operation = " + operation);
        AbstractIntentService.operation = operation;
    }

    public AbstractIntentService() {
        super("AbstractIntentService");
        mIsSuccess = false;
        operationsQueue = new ArrayList<>();
        resultData = new HashMap<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(AbstractIntentService.class.getSimpleName(), "onCreate: ");
    }

    @Override
    public void onDestroy() {
        Log.i(AbstractIntentService.class.getSimpleName(), "onDestroy: ");
        super.onDestroy();
    }

    private String getResultNotif() {
        Log.i(AbstractIntentService.class.getSimpleName(), "getResultNotif: ");
        String notice;
        if (mIsSuccess) {
            notice = getSuccessResult(operation);
        } else {
            notice = getFailResult(operation);
        }
        return notice;
    }

    @NonNull
    private Intent initProadcastIntent() {
        Log.i(AbstractIntentService.class.getSimpleName(), "initProadcastIntent: ");
        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            setQueue(extras.getInt(EXTRA_KEY_OPERATION_CODE));
        }
    }

    private void setQueue(int operation) {
        if (operationsQueue.isEmpty()) {
            operationsQueue.add(operation);
            restartOperation();
        }else{
            operationsQueue.add(operation);
        }
        Log.i(AbstractIntentService.class.getSimpleName(), "setQueue: operationsQueue = " + operationsQueue);
    }

    private void restartOperation() {
        if (!operationsQueue.isEmpty()) {
            setOperation(operationsQueue.get(0));
            runOperation(getOperation());
            mIsSuccess = true;
            sendOperationResult(getResultNotif(),operation);
            operationsQueue.remove(0);
            if (!operationsQueue.isEmpty()) {
                restartOperation();
            }
        }
    }

    private void sendOperationResult(String result,int operation) {
        Log.i(AbstractIntentService.class.getSimpleName(), "sendOperationResult: ");
        Intent intent = initProadcastIntent();
        intent.putExtra(EXTRA_KEY_FINISH_SUCCESS, mIsSuccess);
        intent.putExtra(EXTRA_KEY_OPERATION_CODE, operation);
        intent.putExtra(EXTRA_KEY_OPERATION_RESULT, result);
        intent.putExtra(EXTRA_KEY_OPERATION_DATA, resultData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}