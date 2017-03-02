package com.arny.myapidemo.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.models.DatabaseHelper;
import com.arny.myapidemo.models.TestObject;

import java.util.ArrayList;

//==============Activity start=========================
public class SqlListActivity extends Activity {
    DatabaseHelper databaseHelper;
    ArrayList<String> data = new ArrayList<String>();
    TextView mTextView;
    ListView sqlList;
    ArrayList<TestObject> objects;
    private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        databaseHelper = DatabaseHelper.getInstance(context);
        sqlList = (ListView) findViewById(R.id.sqlList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        sqlList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sql_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_additem:
                return true;
            case R.id.action_clearall:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}