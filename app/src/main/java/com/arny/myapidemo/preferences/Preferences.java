package com.arny.myapidemo.preferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;
import com.arny.myapidemo.R;
import com.arny.myapidemo.helpers.ToastMaker;

public class Preferences extends PreferenceActivity {
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        // Get the custom preference
        Preference customPref = findPreference("customPref");
        customPref .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

                    public boolean onPreferenceClick(Preference preference) {
                        ToastMaker.toast(getBaseContext(),"The custom preference has been clicked");
                        SharedPreferences customSharedPreference = getSharedPreferences( "myCustomSharedPrefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = customSharedPreference .edit();
                        editor.putString("myCustomPref", "The preference has been clicked");
                        editor.apply();
                        return true;
                    }

                });
    }
}