package com.arny.myapidemo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.FragmentOne;
import com.arny.myapidemo.fragments.FragmentTwo;
import com.arny.myapidemo.fragments.MyServicefragment;
import com.arny.myapidemo.fragments.SplashFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private String[] tabsFragments = {
            "Fragment one",
            "Fragment two",
            "Fragment splash",
            "Fragment service",
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
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new SplashFragment();
            case 3:
                return new MyServicefragment();
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