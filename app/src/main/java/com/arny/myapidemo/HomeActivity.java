package com.arny.myapidemo;//Package name

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.arny.arnylib.utils.Config;
import com.arny.myapidemo.mvp.auth.AuthActivity;
import com.arny.myapidemo.mvp.useredit.EditActivity;
import com.arny.myapidemo.ui.activities.*;
import com.arny.myapidemo.ui.fragments.SettingsFragment;
import com.arny.myapidemo.weather.view.WeatherViewActivity;

public class HomeActivity extends AppCompatActivity {
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private final Class[] sActivities = new Class[]{
            WeatherViewActivity.class,
            LiveDataActivity.class,
            JsonPlaceholderApiActivity.class,
		    FoldersCleanerActivity.class,
            AuthActivity.class,
            EditActivity.class,
            RxJavaActivity.class,
            TabsActivity.class,
            DBHelperActivity.class,
            NavDrawerActivity.class,
            MapsActivity.class,
            XmlActivity.class,
            ScrollViewActivity.class,
            DialogActivity.class,
            AnimActivity.class,
            FragmentsActivity.class,
            AlertCustomActivity.class,
            SimpleUIActivity.class,
            CoordinatorActivity.class,
            TimerTaskActivity.class,
            StartAlarmActivity.class,
            NetworkActivity.class,
            IntentServiceActivity.class,
            LocationActivity.class,
            RecyclerTestActivity.class,
            PermissionsActivity.class,
            LoaderActivity.class,
            GoogleOAuthActivity.class
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        initToolbar();

        ListView listView = findViewById(R.id.main_list);
        String[] classesNames = getClassesNames();

        if (classesNames.length > 0) {
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classesNames));
            listView.setOnItemClickListener((parent, view, position, id) ->
                    startActivity(new Intent(getBaseContext(), sActivities[position])));
        }
        String test = Config.getString(SettingsFragment.PREF_EDIT_TEST, this);
        if (BuildConfig.DEBUG) Log.i(HomeActivity.class.getSimpleName(), "onCreate: test = " + test);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle(getString(R.string.res_main_title));
        }
    }

    private String[] getClassesNames() {
        String[] names = new String[sActivities.length];
        for (int i = 0; i < sActivities.length; i++) {
            names[i] = sActivities[i].getSimpleName();
        }
        return names;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getBaseContext(), SettingsActivity.class));
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
                (dialog, id) -> finish());
        AlertDialog alert = quitDialog.create();
        alert.show();
    }
}