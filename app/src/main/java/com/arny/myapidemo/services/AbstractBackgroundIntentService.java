package com.arny.myapidemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;

public abstract class AbstractBackgroundIntentService extends IntentService {
    /*Extras*/
    public static final String ACTION = "AbstractBackgroundIntentService.action";
    public static final String EXTRA_KEY_OPERATION_CODE = "AbstractBackgroundIntentService.operation.code";
    public static final String EXTRA_KEY_OPERATION_RESULT = "AbstractBackgroundIntentService.operation.result";
    public static final String EXTRA_KEY_FINISH = "AbstractBackgroundIntentService.operation.finish";
    public static final String EXTRA_KEY_FINISH_SUCCESS = "AbstractBackgroundIntentService.operation.success";
    public static final String EXTRA_KEY_OPERATION_DATA = "AbstractBackgroundIntentService.operation.data";
    /*other*/
    protected static int operation;
    protected boolean mIsSuccess;
    protected boolean mIsStopped;
    protected HashMap<String, String> hashMap;

    protected static int getOperation(){
        return operation;
    }

    protected void setOperation(int operation){
        AbstractBackgroundIntentService.operation = operation;
    }

    protected abstract void runOperation(int operation);

    public AbstractBackgroundIntentService(String name) {
        super(name);
        mIsSuccess = false;
        mIsStopped = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        onServiceDestroy();
        super.onDestroy();
    }

    private void onServiceDestroy() {
        mIsStopped = true;
        sendBroadcastIntent(getResultNotif());
    }

    protected abstract String getSuccessResult(int operation);
    protected abstract String getFailResult(int operation);

    private String getResultNotif() {
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
        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        return intent;
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        hashMap = new HashMap<>();
        setOperation(intent.getIntExtra(EXTRA_KEY_OPERATION_CODE, 0));
        runOperation(getOperation());
    }

    private void sendBroadcastIntent(String result) {
        Intent intent = initProadcastIntent();
        intent.putExtra(EXTRA_KEY_FINISH, mIsStopped);
        intent.putExtra(EXTRA_KEY_FINISH_SUCCESS, mIsSuccess);
        intent.putExtra(EXTRA_KEY_OPERATION_CODE, operation);
        intent.putExtra(EXTRA_KEY_OPERATION_RESULT, result);
        intent.putExtra(EXTRA_KEY_OPERATION_DATA, hashMap);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}