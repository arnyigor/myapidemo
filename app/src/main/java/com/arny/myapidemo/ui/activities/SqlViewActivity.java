package com.arny.myapidemo.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.arny.myapidemo.R;

public class SqlViewActivity extends Activity {
    TextView viewlist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllistview);
        setTitle("View list");
        viewlist = (TextView) findViewById(R.id.textView);

    }
}