package com.arny.myapidemo.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arny.myapidemo.utils.BaseUtils;
import com.redmadrobot.chronos.ChronosOperation;
import com.redmadrobot.chronos.ChronosOperationResult;

public final class SimpleOperation extends ChronosOperation<String> {
    private static final String TAG = "LOG_TAG";
    private final String mAction;

    public SimpleOperation(@NonNull final String action) {
        mAction = action;
    }

    @Nullable
    @Override
    public String run() {
        Log.i(TAG, "run: mAction = " + mAction);
        try {
            for (int i = 0; i < 15; i++) {
                Thread.sleep(1000);
                Log.i(TAG, "run: i =" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run: mAction = ends");
        String result = "ChronosOperation ends in " + BaseUtils.getDateTime(0, null);
        Log.i(TAG, "run: result = " + result);
        return result;
    }

    @NonNull
    @Override
    public Class<? extends ChronosOperationResult<String>> getResultClass() {
        return Result.class;
    }

    public final static class Result extends ChronosOperationResult<String> {
    }
}