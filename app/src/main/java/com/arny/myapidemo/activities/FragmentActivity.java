package com.arny.myapidemo.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.FragmentAlarms;

public class FragmentActivity extends AppCompatActivity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentshow);
        initToolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new FragmentAlarms())
                .commit();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle(getString(R.string.title_fragments));
        }
    }
}