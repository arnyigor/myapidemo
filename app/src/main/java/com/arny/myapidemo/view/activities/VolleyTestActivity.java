package com.arny.myapidemo.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.net.NetworkJSONLoader;
import com.arny.myapidemo.net.NetworkStringLoader;

public class VolleyTestActivity extends AppCompatActivity {
    String urlString = "http://androidsrc.net/api/sample_files/sampdle.html";
    String urlJSON = "http://androidsrc.net/api/sample_files/sample.json";
    Button stringLoaderBtn, jsonLoaderBtn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_activity);
        tv = (TextView) findViewById(R.id.textView1);
        stringLoaderBtn = (Button) findViewById(R.id.buttonLoadString);
        stringLoaderBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NetworkStringLoader loader = new NetworkStringLoader(
                        VolleyTestActivity.this, tv);
                loader.requestString(urlString);
            }
        });
        jsonLoaderBtn = (Button) findViewById(R.id.buttonJSONRequest);
        jsonLoaderBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NetworkJSONLoader j = new NetworkJSONLoader(VolleyTestActivity.this,
                        tv);
                j.requestJSON(urlJSON);
            }
        });

    }
}
