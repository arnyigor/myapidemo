package com.arny.myapidemo.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.arny.myapidemo.livedata.LiveDataTimerViewModel;
import com.arny.myapidemo.R;

public class LiveDataActivity extends AppCompatActivity {

    private LiveDataTimerViewModel mLiveDataTimerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        mLiveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);
        subscribe();
    }

    private void subscribe() {
        final Observer<Long> elapsedTimeObserver = aLong -> {
            String newText = LiveDataActivity.this.getResources().getString(R.string.seconds, aLong);
            ((TextView) findViewById(R.id.timer_textview)).setText(newText);
            Log.i(LiveDataActivity.class.getSimpleName(), "subscribe: " + "Updating timer:");
        };

        mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver);
    }
}