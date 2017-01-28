package com.arny.myapidemo;//Package name

// imports start==========

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.arny.myapidemo.activities.LayoutInflaterActivity;
import com.arny.myapidemo.activities.NavigationActivity;
import com.arny.myapidemo.activities.RandomLoaderActivity;
import com.arny.myapidemo.activities.ScrollViewActivity;
import com.arny.myapidemo.activities.SimpleAdapterActivity;
import com.arny.myapidemo.activities.SimpleUIActivity;
import com.arny.myapidemo.activities.SqlLiteActivity;
import com.arny.myapidemo.activities.StartAlarmActivity;
import com.arny.myapidemo.activities.TabsActivity;
import com.arny.myapidemo.activities.TimerTaskActivity;
import com.arny.myapidemo.activities.VolleyTestActivity;
import com.arny.myapidemo.activities.XmlActivity;
import com.arny.myapidemo.fragments.SplashFragment;
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
            LayoutInflaterActivity.class,
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
    };
    boolean CheckboxPreference;
    String ListPreference;
    String editTextPreference;
    String ringtonePreference;
    String secondEditTextPreference;
    String customPref;
    // ==============Forms variables start==============
    FragmentManager fragmentManager;
    // =============Variables end================
    private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.res_main_title));
        runSplash();
        Log.d(TAG, "HomeActivity: onCreate()");
        listView = (ListView) findViewById(R.id.main_list);
        String[] titles = getResources().getStringArray(R.array.arrayActivities);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles));
        listView.setOnItemClickListener(onListItemClick);
    }

    AdapterView.OnItemClickListener onListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getBaseContext(), sActivities[position]);
            startActivity(intent);
        }
    };

    public void getPrefs() {
        // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        CheckboxPreference = prefs.getBoolean("checkboxPref", true);
        ListPreference = prefs.getString("listPref", "Black");
        editTextPreference = prefs.getString("editTextPref", "Nothing has been entered");
        ringtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");
        secondEditTextPreference = prefs.getString("SecondEditTextPref", "Nothing has been entered");
        // Get the custom preference
        SharedPreferences mySharedPreferences = getSharedPreferences( "myCustomSharedPrefs", Activity.MODE_PRIVATE);
        customPref = mySharedPreferences.getString("myCusomPref", "");
    }

    public void runSplash() {
            fragmentManager = getSupportFragmentManager();
            SplashFragment splashFragment = new SplashFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, splashFragment)
                    .addToBackStack(null)
                    .commit();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "HomeActivity: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "HomeActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "HomeActivity: onResume()");
        getPrefs();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "HomeActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "HomeActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "HomeActivity: onDestroy()");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem splashItem = menu.findItem(R.id.menu_action_splash);
//        splashItem.setChecked(preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
//        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settingsActivity = new Intent(getBaseContext(),
                        Preferences.class);
                startActivity(settingsActivity);
              /*  item.setChecked(!item.isChecked());
                preferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked());*/
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