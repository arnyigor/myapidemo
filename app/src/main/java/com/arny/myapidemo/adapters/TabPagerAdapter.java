package com.arny.myapidemo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.arny.myapidemo.ui.fragments.*;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private String[] tabsFragments = {
            "Fragment alarms",
            "Fragment two",
            "Fragment splash",
            "Fragment service",
            "Fragment Map OsmAnd",
    };
//    private String[] tabsFragmentsResourses = {
//            context.getResources().getString(R.string.action_settings),
//    };

    public TabPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new FragmentAlarms();
            case 1:
                return new FragmentClearFolders();
            case 2:
                return new SplashFragment();
            case 3:
                return new MyServicefragment();
            case 4:
                return new OsmandMapFragment();
        }
        return null;

    }

    @Override
    public int getCount() {
        return tabsFragments.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabsFragments[position];
    }

}