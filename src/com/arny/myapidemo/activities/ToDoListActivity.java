package com.arny.myapidemo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.arny.myapidemo.R;

import java.util.ArrayList;

//==============Activity start=========================
public class ToDoListActivity extends Activity
{
   // =============Variables start================

    // =============Variables end================

    // ==============Forms variables start==============

    // ==============Forms variables end==============
// ====================onCreate start=========================
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);

        // ================Forms Ids start=========================
        ListView myListView = (ListView)findViewById(R.id.myListView);
        final EditText myEditText = (EditText)findViewById(R.id.myEditText);
        // ================Forms Ids end=========================

        // ==================onCreateCode start=========================
        // Создайте массив для хранения списка задач
        final ArrayList<String> todoItems = new ArrayList<String>();
        // Создайте ArrayAdapter, чтобы привязать массив к ListView
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                todoItems);
    // Привяжите массив к ListView.
        myListView.setAdapter(aa);


        myEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
                        todoItems.add(0, myEditText.getText().toString());
                        aa.notifyDataSetChanged();
                        myEditText.setText("");
                        return true;
                    }
                return false;
            }
        });
        // ==================onCreateCode end=========================

    }//============onCreate end====================


    // ====================CustomCode start======================================

    // ====================CustomCode end======================================

    // ====================OnClicks======================================

    // ====================OnClicks end======================================
}// ===================Activity end==================================
// ===================SimpleActivity==================================