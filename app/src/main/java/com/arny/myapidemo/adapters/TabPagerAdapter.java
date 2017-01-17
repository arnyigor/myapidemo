package com.arny.myapidemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arny.myapidemo.fragments.FragmentOne;
import com.arny.myapidemo.fragments.FragmentTwo;
import com.arny.myapidemo.fragments.SplashFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
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
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="Fragment one";
                break;
            case 1:
                title="Fragment two";
                break;
            case 2:
                title="Fragment splash";
                break;
        }

        return title;
    }

    }