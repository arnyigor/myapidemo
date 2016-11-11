package com.arny.myapidemo.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.Random;

public class RandomLoader extends AsyncTaskLoader<String> {

    public static final String TAG = "LOG_TAG";
    public static final String ARG_WORD = "word";
    public static final int RANDOM_STRING_LENGTH = 100;
    private String mWord;

    public RandomLoader(Context context, Bundle args) {
        super(context);
        if (args != null)
            mWord = args.getString(ARG_WORD);
    }

    @Override
    public String loadInBackground() {
        if (mWord == null) {
            return null;
        }
        Log.i(TAG, "loadInBackground");
        return generateString(mWord);
    }

    @Override
    public void forceLoad() {
        Log.i(TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.i(TAG, "onStopLoading");
    }

    @Override
    public void deliverResult(String data) {
        Log.i(TAG, "deliverResult");
        super.deliverResult(data);
    }


    private String generateString(String characters)
    {
        Random rand = new Random();
        char[] text = new char[RANDOM_STRING_LENGTH];
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            text[i] = characters.charAt(rand.nextInt(characters.length()));
        }
        return new String(text);
    }
}