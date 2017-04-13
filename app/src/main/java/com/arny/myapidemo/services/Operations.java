package com.arny.myapidemo.services;

import android.os.SystemClock;
import android.util.Log;
import com.arny.myapidemo.utils.BaseUtils;

public class Operations extends AbstractIntentService {

    public Operations() {
        super();
    }

    @Override
    protected String getSuccessResult(int operation) {
        Log.i(Operations.class.getSimpleName(), "getSuccessResult: operation = " + operation);
        return "getSuccessResult";
    }

    @Override
    protected String getFailResult(int operation) {
        Log.i(Operations.class.getSimpleName(), "getFailResult: operation = " + operation);
        return "getFailResult";
    }

    @Override
    protected void runOperation(int operation) {
        switch (operation) {
            case 1:
                Log.i(Operations.class.getSimpleName(), "runOperation:1 time = " + BaseUtils.getDateTime());
                SystemClock.sleep(5000);
                resultData.put("key1", 1);
                resultData.put("key2", "ok 1");
                break;
            case 2:
                Log.i(Operations.class.getSimpleName(), "runOperation:2 time = " + BaseUtils.getDateTime());
                SystemClock.sleep(10000);
                resultData.put("key1", 2);
                resultData.put("key2", "ok 2");
                break;
            case 3:
                Log.i(Operations.class.getSimpleName(), "runOperation:3 time = " + BaseUtils.getDateTime());
                SystemClock.sleep(3000);
                resultData.put("key1", 3);
                resultData.put("key2", "ok 3");
                break;
        }
    }
}
