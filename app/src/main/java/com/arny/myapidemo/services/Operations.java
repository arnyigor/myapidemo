package com.arny.myapidemo.services;

import android.os.SystemClock;
import android.util.Log;
import com.arny.myapidemo.utils.BaseUtils;

import java.util.HashMap;

public class Operations extends AbstractIntentService {
    public Operations() {
        super();
    }

    @Override
    protected void runOperation(OperationProvider provider,OnOperationResult operationResult) {
        Log.i(Operations.class.getSimpleName(), "runOperation: provider = " + provider.getId());
        switch (provider.getId()) {
            case 1:
                Log.i(Operations.class.getSimpleName(), "runOperation:1 time = " + BaseUtils.getDateTime() + " startData = " + provider.getOperationData());
                long start = System.currentTimeMillis();
                SystemClock.sleep(5000);
                try {
                    double res = 10 / 0;
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("key2", res);
                    String finish = String.valueOf((System.currentTimeMillis() - start));
                    data.put("key3", finish);

                    provider.setSuccess(true);
                    provider.setOperationData(data);
                    operationResult.resultSuccess(provider);
                } catch (Exception e) {
                    provider.setSuccess(false);
                    provider.setResult(e.getMessage());
                    operationResult.resultFail(provider);
                    e.printStackTrace();
                }
                break;
            case 2:
                Log.i(Operations.class.getSimpleName(), "runOperation:2 time = " + BaseUtils.getDateTime() + " startData = " + provider.getOperationData());
                long start2 = System.currentTimeMillis();
                SystemClock.sleep(10000);
                HashMap<String, Object> data = new HashMap<>();
                data.put("key1", 2);
                String finish2 = String.valueOf((System.currentTimeMillis() - start2));
                data.put("key2", finish2);

                provider.setSuccess(true);
                provider.setOperationData(data);
                operationResult.resultSuccess(provider);
                break;
            case 3:
                Log.i(Operations.class.getSimpleName(), "runOperation:3 time = " + BaseUtils.getDateTime() + " startData = " + provider.getOperationData());
                long start3 = System.currentTimeMillis();
                SystemClock.sleep(15000);
                HashMap<String, Object> data2 = new HashMap<>();
                data2.put("key1", 3);
                String finish3 = String.valueOf((System.currentTimeMillis() - start3));
                data2.put("key2", finish3);
                operationResult.resultSuccess(provider);

                provider.setSuccess(true);
                provider.setOperationData(data2);
                operationResult.resultSuccess(provider);
                break;
        }
    }
}
