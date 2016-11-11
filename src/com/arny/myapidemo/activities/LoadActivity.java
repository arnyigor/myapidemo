package com.arny.myapidemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.arny.myapidemo.MainController;
import com.arny.myapidemo.R;

public class LoadActivity extends Activity {

    final int del = 2000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this, MainController.class);
                startActivity(intent);
                finish();
            }
        }, del);
    }
}