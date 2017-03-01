package com.arny.myapidemo;//Package name

// imports start==========

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arny.myapidemo.activities.AlertCustomActivity;
import com.arny.myapidemo.activities.AnimActivity;
import com.arny.myapidemo.activities.ChronosTestActivity;
import com.arny.myapidemo.activities.CoordinatorActivity;
import com.arny.myapidemo.activities.DialogActivity;
import com.arny.myapidemo.activities.FragmentActivity;
import com.arny.myapidemo.activities.IntentServiceActivity;
import com.arny.myapidemo.activities.NavigationActivity;
import com.arny.myapidemo.activities.RandomLoaderActivity;
import com.arny.myapidemo.activities.ScrollViewActivity;
import com.arny.myapidemo.activities.SettingsActivity;
import com.arny.myapidemo.activities.SimpleAdapterActivity;
import com.arny.myapidemo.activities.SimpleUIActivity;
import com.arny.myapidemo.activities.SqlLiteActivity;
import com.arny.myapidemo.activities.StartAlarmActivity;
import com.arny.myapidemo.activities.TabsActivity;
import com.arny.myapidemo.activities.TimerTaskActivity;
import com.arny.myapidemo.activities.VolleyTestActivity;
import com.arny.myapidemo.activities.XmlActivity;
import com.arny.myapidemo.preferences.Preferences;

//==============Activity start=========================
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "LOG_TAG";
    private final Class[] sActivities = new Class[] {
            TabsActivity.class,
            XmlActivity.class,
            ScrollViewActivity.class,
            DialogActivity.class,
            SqlLiteActivity.class,
            SimpleAdapterActivity.class,
            AnimActivity.class,
            FragmentActivity.class,
            AlertCustomActivity.class,
            NavigationActivity.class,
            SimpleUIActivity.class,
            RandomLoaderActivity.class,
            CoordinatorActivity.class,
            TimerTaskActivity.class,
            StartAlarmActivity.class,
            VolleyTestActivity.class,
            IntentServiceActivity.class,
            ChronosTestActivity.class,
            NavigationActivity.class,
            SettingsActivity.class,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.res_main_title));
        ListView listView = (ListView) findViewById(R.id.main_list);
        String[] classesNames = getClassesNames();
        if (classesNames.length>0){
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classesNames));
            listView.setOnItemClickListener(onListItemClick);
        }
    }

    private String[] getClassesNames() {
        String[] names = new String[sActivities.length];
        for (int i = 0; i < sActivities.length; i++) {
            names[i] = sActivities[i].getSimpleName();
        }
        return names;
    }

    AdapterView.OnItemClickListener onListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(getBaseContext(), sActivities[position]));
        }
    };

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(getBaseContext(), Preferences.class));
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                HomeActivity.this);
        quitDialog.setTitle(getString(R.string.exit_question));
        quitDialog.setNegativeButton(getString(R.string.no_answer), null);
        quitDialog.setPositiveButton(getString(R.string.yes_answer),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = quitDialog.create();
        alert.show();
    }

}