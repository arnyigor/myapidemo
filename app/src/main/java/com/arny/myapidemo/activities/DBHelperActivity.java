package com.arny.myapidemo.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.database.DB;
import com.arny.myapidemo.models.CarFuel;
import com.arny.myapidemo.models.Refills;
import com.arny.myapidemo.models.TestObject;

import java.util.ArrayList;

import pw.aristos.arnylib.database.DBHelper;
import pw.aristos.arnylib.database.DBProvider;

public class DBHelperActivity extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<String>();
    TextView mTextView;
    ListView sqlList;
    ArrayList<TestObject> objects;
    private Context context = this;
    private TestObject testObject;
    private Toolbar toolbar;
    private ArrayList<Refills> refillses;
    private ArrayList<CarFuel> carfuels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        initToolbar();
        sqlList = (ListView) findViewById(R.id.sqlList);
        getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        sqlList.setAdapter(adapter);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.title_dbhelper));
        }
    }

    private void getData() {
        testObject = new TestObject();
        testObject.setTitle("tit1");
         data.add("tit1");
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
            case R.id.menu_action_get_object_fields:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                         DB.testDb(DBHelperActivity.this);
                        return null;
                    }
                }.execute();

                return true;
            case R.id.action_clearall:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}