package com.arny.myapidemo.ui.activities;


import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.arny.arnylib.utils.BasePermissions;
import com.arny.arnylib.utils.RuntimePermissionsActivity;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.TabPagerAdapter;
import com.arny.myapidemo.ui.fragments.ActivityCommunicator;
import com.arny.myapidemo.ui.fragments.FragmentCommunicator;

import java.util.List;

public class TabsActivity extends RuntimePermissionsActivity implements ActivityCommunicator {

	public FragmentCommunicator fragmentCommunicator;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        initToolbar();
        initTabs();
        initTabsWork();
        if (!BasePermissions.isStoragePermissonGranted(this)) {
            TabsActivity.super.requestAppPermissions(new
                            String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, R.string.storage_permission_denied
                    , BasePermissions.REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        onResume();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.title_tabs));
        }
    }

    private void initTabsWork() {
        // Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
        FragmentManager manager=getSupportFragmentManager();
        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
        TabPagerAdapter adapter=new TabPagerAdapter(manager,this);
        //set Adapter to view pager
        viewPager.setAdapter(adapter);
        //set tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);
        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Setting tabs from adpater
    }

    private void initTabs() {
        viewPager= findViewById(R.id.viewPager);
        tabLayout= findViewById(R.id.tabLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void passDataToActivity(String someValue) {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		Log.i(TabsActivity.class.getSimpleName(), "passDataToActivity: fragments = " + fragments);
		fragmentCommunicator.passDataToFragment(someValue);
	}
}