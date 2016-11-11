package com.arny.myapidemo.activities;

// imports start==========
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.arny.myapidemo.R;

//==============Activity start=========================
public class SqlViewActivity extends Activity {
    // =============Variables start================
    // =============Variables end================

    // ==============Forms variables start==============
    TextView viewlist;
    // ==============Forms variables end==============
    // ====================onCreate start=========================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllistview);
        setTitle("View list");

        // ================Forms Ids start=========================
        viewlist = (TextView) findViewById(R.id.textView);

    }
}