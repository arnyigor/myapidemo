package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.ActivityCommunicator;
import com.arny.myapidemo.fragments.FirstFragment;
import com.arny.myapidemo.fragments.FragmentCommunicator;
import com.arny.myapidemo.fragments.SecondFragment;

import java.util.List;

public class FragmentsActivity extends AppCompatActivity  implements ActivityCommunicator {

	public FragmentCommunicator fragmentCommunicator;
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentshow);
        initToolbar();
	    getSupportFragmentManager().beginTransaction()
			    .replace(R.id.fragFirst, new FirstFragment())
			    .commit();
	    getSupportFragmentManager().beginTransaction()
			    .replace(R.id.fragContainer, new SecondFragment())
			    .commit();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        setTitle(getString(R.string.title_fragments));
        }
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				super. onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void passDataToActivity(String someValue) {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments) {
			if (fragment instanceof FirstFragment) {
				FirstFragment fragment1 = (FirstFragment) fragment;
				fragment1.passDataToFragment(someValue);
			}else if(fragment instanceof SecondFragment) {
				SecondFragment fragment1 = (SecondFragment) fragment;
				fragment1.passDataToFragment(someValue);}
		}
	}
}