package com.arny.myapidemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractIntentService extends IntentService {
    /*Extras*/
    public static final String ACTION = "AbstractIntentService.action";
    public static final String EXTRA_KEY_OPERATION_CODE = "AbstractIntentService.operation.code";
    public static final String EXTRA_KEY_TYPE = "AbstractIntentService.type";
    public static final int EXTRA_KEY_TYPE_SYNC = 0;
    public static final int EXTRA_KEY_TYPE_ASYNC = 1;
    public static final String EXTRA_KEY_OPERATION_RESULT = "AbstractIntentService.operation.result";
    public static final String EXTRA_KEY_FINISH_SUCCESS = "AbstractIntentService.operation.success";
    public static final String EXTRA_KEY_OPERATION_DATA = "AbstractIntentService.operation.data";
    /*other*/
    protected static int operation;
    protected boolean mIsSuccess;
    protected HashMap<String, Object> resultData;
    private ArrayList<Integer> operationsQueue;

    protected abstract void runOperation(int operation);

    protected abstract String getSuccessResult(int operation);

    protected abstract String getFailResult(int operation);

    protected static int getOperation() {
        return operation;
    }

    protected void setOperation(int operation) {
        Log.i(AbstractIntentService.class.getSimpleName(), "setOperation: operation = " + operation);
        AbstractIntentService.operation = operation;
    }

    public AbstractIntentService() {
        super("AbstractIntentService");
        mIsSuccess = false;
        operationsQueue = new ArrayList<>();
        resultData = new HashMap<>();
    }


    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private final AtomicInteger tasks_left = new AtomicInteger(0);
    private final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);

    protected void tasksLeft(int tasks_left) {
    }

    private final ThreadPoolExecutor ThreadPoolExecutor = new ParallelThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    private class ParallelThreadPoolExecutor extends ThreadPoolExecutor {
        public ParallelThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            final int active_count = tasks_left.decrementAndGet();
            Log.i(ParallelThreadPoolExecutor.class.getSimpleName(), "afterExecute: active_count = " + active_count);
            if (active_count == 0) {
                Log.i(ParallelThreadPoolExecutor.class.getSimpleName(), "afterExecute: 1");
                onDestroy();
            } else {
                Log.i(ParallelThreadPoolExecutor.class.getSimpleName(), "afterExecute: 2");
                tasksLeft(active_count);
            }
        }
    }

    public class Task implements Runnable {
        private final Intent intent;

        public Task(final Intent intent) {
            this.intent = intent;
        }

        public void run() {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                executeOperation(extras.getInt(EXTRA_KEY_OPERATION_CODE));
            }
        }
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

    private String getResultNotif(int operation) {
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
            int type = extras.getInt(EXTRA_KEY_TYPE);
            if (type == EXTRA_KEY_TYPE_SYNC) {
                setQueue(extras.getInt(EXTRA_KEY_OPERATION_CODE));
            } else {
                ThreadPoolExecutor.execute(new Task(intent));
            }
        }
    }

    private void setQueue(int operation) {
        if (operationsQueue.isEmpty()) {
            operationsQueue.add(operation);
            restartOperation();
        } else {
            operationsQueue.add(operation);
        }
        Log.i(AbstractIntentService.class.getSimpleName(), "setQueue: operationsQueue = " + operationsQueue);
    }

    private void restartOperation() {
        if (!operationsQueue.isEmpty()) {
            executeOperation(operationsQueue.get(0));
            operationsQueue.remove(0);
            if (!operationsQueue.isEmpty()) {
                restartOperation();
            }
        }
    }

    private void executeOperation(int operation) {
        setOperation(operation);
        runOperation(getOperation());
        mIsSuccess = true;
        sendOperationResult(getResultNotif(operation), operation);
    }

    private void sendOperationResult(String result, int operation) {
        Log.i(AbstractIntentService.class.getSimpleName(), "sendOperationResult: ");
        Intent intent = initProadcastIntent();
        intent.putExtra(EXTRA_KEY_FINISH_SUCCESS, mIsSuccess);
        intent.putExtra(EXTRA_KEY_OPERATION_CODE, operation);
        intent.putExtra(EXTRA_KEY_OPERATION_RESULT, result);
        intent.putExtra(EXTRA_KEY_OPERATION_DATA, resultData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}