package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.net.NetworkJSONLoader;
import com.arny.myapidemo.net.NetworkStringLoader;
import com.arny.myapidemo.utils.BaseUtils;

public class VolleyTestActivity extends AppCompatActivity {
    String urlString = "http://dev.aristos.pw:5055?id=913319&lat=37.421998333333335&lon=-122.08400000000002&timestamp=1488782859";
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
                String lat = String.valueOf(BaseUtils.randInt(15, 35) + ".08400000000002");
                String lon = String.valueOf(BaseUtils.randInt(15, 180) + ".08400000000002");
                String timestamp = String.valueOf(System.currentTimeMillis()/1000);
                String urlString1 = "http://dev.aristos.pw:5055?id=1115111&lat=" + lat + "&lon="+lon+"&timestamp=" + timestamp;
                Log.i(VolleyTestActivity.class.getSimpleName(), "onClick: urlString = " + urlString);
                Log.i(VolleyTestActivity.class.getSimpleName(), "onClick: urlString1 = " + urlString1);
                loader.requestString(urlString1);
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
