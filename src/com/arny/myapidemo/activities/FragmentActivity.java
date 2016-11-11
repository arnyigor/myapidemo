package com.arny.myapidemo.activities;//Package name

// imports start==========

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.FragmentTwo;

//==============Activity start=========================
public class FragmentActivity extends AppCompatActivity {
	Button btnGetAll;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentshow);

	}
}