package com.arny.myapidemo.services;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by i.sedoy on 12.04.17.
 */

public class BackgroundOperations extends AbstractBackgroundIntentService {

    public BackgroundOperations() {
        super("backgroundOperation");
    }

    @Override
    protected String getSuccessResult(int operation) {
        return "getSuccessResult";
    }

    @Override
    protected String getFailResult(int operation) {
        return "getFailResult";
    }

    @Override
    protected void runOperation(int operation) {
        Log.i(BackgroundOperations.class.getSimpleName(), "runOperation: operation = " + operation);
        SystemClock.sleep(5000);
    }
}
