package com.arny.myapidemo.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.*;
import android.util.Log;
import com.arny.myapidemo.R;


/**
 * shows the settings option for choosing the movie categories in ListPreference.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PREF_CATEGORIES = "categoriesKey";
    public static final String PREF_EDIT_TEST = "test_key";
    public static final String PREF_EDIT_2_TEST = "test_2_key";
    public static final String PREF_CHBX_TEST = "test_3_key";
    public static final String PREF_MULTI_SEL = "multi_select_list_preference";
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
//        //add xml
        addPreferencesFromResource(R.xml.preferences);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            listPrefListener(sharedPreferences, preference, key);
        } else if (preference instanceof CheckBoxPreference) {
            checkboxListener(preference,key);
        } else if (preference instanceof EditTextPreference) {
            edttextPrefListener(preference,key);
        }
    }

    private void edttextPrefListener(Preference preference, String key) {
        EditTextPreference editTextPreference = (EditTextPreference) preference;
        switch (key) {
            case PREF_EDIT_TEST:
                Log.i(SettingsFragment.class.getSimpleName(), "onSharedPreferenceChanged: editTextPreference.getText() = " + editTextPreference.getText());
                break;
            case PREF_EDIT_2_TEST:
                Log.i(SettingsFragment.class.getSimpleName(), "onSharedPreferenceChanged: editTextPreference.getText() = " + editTextPreference.getText());
                break;
        }
    }

    private void checkboxListener(Preference preference,String key) {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
        switch (key) {
            case PREF_CHBX_TEST:
                Log.i(SettingsFragment.class.getSimpleName(), "onSharedPreferenceChanged: checkBoxPreference.isChecked() = " + checkBoxPreference.isChecked());
                break;
        }
    }

    private void listPrefListener(SharedPreferences sharedPreferences, Preference preference, String key) {
        switch (key) {
            case PREF_CATEGORIES:
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
                Log.i(SettingsFragment.class.getSimpleName(), "onSharedPreferenceChanged: prefIndex = " + prefIndex);
                break;
        }
    }
}