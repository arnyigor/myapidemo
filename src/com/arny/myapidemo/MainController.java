package com.arny.myapidemo;//Package name

// imports start==========

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.arny.myapidemo.activities.*;
import com.arny.myapidemo.fragments.SplashFragment;
import com.arny.myapidemo.net.NetActivity;
import com.arny.myapidemo.preferences.PreferenceHelper;
import com.arny.myapidemo.preferences.Preferences;

//==============Activity start=========================
public class MainController extends AppCompatActivity {
    private static final String TAG = "LOG_TAG";
    // =============Variables start================
    PreferenceHelper preferenceHelper;
    boolean CheckboxPreference;
    String ListPreference;
    String editTextPreference;
    String ringtonePreference;
    String secondEditTextPreference;
    String customPref;
    private Context context = this;
    String[] mActivities;
    private ListView listView;
    private Intent intent;
    // =============Variables end================

    // ==============Forms variables start==============
    FragmentManager fragmentManager;
    TextView mTextView;

    // ==============Forms variables end==============
    // ====================onCreate start=========================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.res_main_title));
        Log.d(TAG, "MainController: onCreate()");
        /*PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
		getPrefs();
		String theme = ListPreference;
		if (theme.equals("Black")) {
			getApplication().getTheme().applyStyle(R.style.AppTheme,true);
//			setTheme(R.style.AppTheme);
		 }else if(theme.equals("Light")){
//			 setTheme(R.style.AppThemeLight);
			 getApplication().getTheme().applyStyle(R.style.AppThemeLight,true);
        }
		Toast.makeText(this, "Theme has been reset to " + theme ,
                Toast.LENGTH_SHORT).show();*///TODO not working to app
        listView = (ListView) findViewById(R.id.main_list);
        mActivities = context.getResources().getStringArray(R.array.arrayActivities);
        try {
            ActivityInfo[] list = getActivityList();
            for (ActivityInfo aList : list) {
                Log.i(TAG, "onCreate List of running activities" + aList.name);

            }
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        runSplash();

        mTextView = (TextView) findViewById(R.id.textSelect);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mActivities);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onListItemClick);

    }

    private ActivityInfo[] getActivityList() throws PackageManager.NameNotFoundException {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = this.getPackageManager();

        PackageInfo info = pm.getPackageInfo("com.arny.myapidemo.activities", PackageManager.GET_ACTIVITIES);

        ApplicationInfo test = info.applicationInfo;
        ActivityInfo[] list = info.activities;
        return list;
    }

    AdapterView.OnItemClickListener onListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    intent = new Intent(MainController.this, TabsActivity.class);
                    break;
                case 1:
                    intent = new Intent(MainController.this, XmlActivity.class);
                    break;
                case 2:
                    intent = new Intent(MainController.this, ScrollViewActivity.class);
                    break;
                case 3:
                    intent = new Intent(MainController.this, DialogActivity.class);
                    break;
                case 4:
                    intent = new Intent(MainController.this, SqlLiteActivity.class);
                    break;
                case 5:
                    intent = new Intent(MainController.this, LayoutInflaterActivity.class);
                    break;
                case 6:
                    intent = new Intent(MainController.this, SimpleAdapterActivity.class);
                    break;
                case 7:
                    intent = new Intent(MainController.this, AnimActivity.class);
                    break;
                case 8:
                    intent = new Intent(MainController.this, FragmentActivity.class);
                    break;
                case 9:
                    intent = new Intent(MainController.this, AlertCustomActivity.class);
                    break;
                case 10:
                    intent = new Intent(MainController.this, NetActivity.class);
                    break;
                case 11:
                    intent = new Intent(MainController.this, DrawerActivity.class);
                    break;
                case 12:
                    intent = new Intent(MainController.this, SimpleUIActivity.class);
                    break;
                case 13:
                    intent = new Intent(MainController.this, RandomLoaderActivity.class);
                    break;
                case 14:
                    intent = new Intent(MainController.this, CoordinatorActivity.class);
                    break;
                case 15:
                    intent = new Intent(MainController.this, TimerTaskActivity.class);
                    break;
                case 16:
                    intent = new Intent(MainController.this, StartAlarmActivity.class);
                    break;
                case 17:
                    intent = new Intent(MainController.this, MyLoaderActivity.class);
                    break;
            }
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
//        if(!preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, splashFragment)
                    .addToBackStack(null)
                    .commit();
//        }

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainController: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainController: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainController: onResume()");
        getPrefs();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainController: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainController: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainController: onDestroy()");
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
                MainController.this);
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