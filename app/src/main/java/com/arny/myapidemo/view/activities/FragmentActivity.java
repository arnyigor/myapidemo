package com.arny.myapidemo.view.activities;//Package name

// imports start==========

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.arny.myapidemo.R;

//==============Activity start=========================
public class FragmentActivity extends AppCompatActivity {
	Button btnGetAll;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentshow);

	}
}