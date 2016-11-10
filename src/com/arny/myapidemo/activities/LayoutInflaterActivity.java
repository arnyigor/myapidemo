package com.arny.myapidemo.activities;


// imports start==========
// imports end==========


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.arny.myapidemo.R;

//==============Activity start=========================
public class LayoutInflaterActivity extends Activity
{
    // =============Variables start================
    String[] name = { "sad", "asd", "asd", "asd", "asd", "asd",
            "asd", "asd" };
    String[] position = { "asd", "asd", "asd",
            "asd", "asd", "asd", "asd", "asd" };
    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000 };

  int[] colors = new int[2];

    // =============Variables end================

    // ==============Forms variables start==============
    // ==============Forms variables end==============
// ====================onCreate start=========================
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutinflater);

        // ================Forms Ids start=========================
         LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        // ================Forms Ids end=========================

        // ==================onCreateCode start=========================
       
    LayoutInflater ltInflater = getLayoutInflater();

        for (int i = 0; i < name.length; i++) {
            Log.d("myLogs", "i = " + i);
            View item = ltInflater.inflate(R.layout.layoutitem, linLayout, false);
            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            tvName.setText(name[i]);
            TextView tvPosition = (TextView) item.findViewById(R.id.tvPosition);
            tvPosition.setText("asd " + position[i]);
            TextView tvSalary = (TextView) item.findViewById(R.id.tvSalary);
            tvSalary.setText("asd " + String.valueOf(salary[i]));
            item.getLayoutParams().width = LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);
            linLayout.addView(item);
      }
        // ==================onCreateCode end=========================

    }//============onCreate end====================



    // ====================CustomCode start======================================
    // ====================CustomCode end======================================

    // ====================OnClicks======================================

    // ====================OnClicks end======================================
}// ===================Activity end==================================
// ===================SimpleActivity==================================