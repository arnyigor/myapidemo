package com.arny.myapidemo.view.activities;//Package name

import android.app.Activity;
import android.os.Bundle;
import com.arny.myapidemo.R;

//==============Activity start=========================
public class ScrollViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scrollview);
		setTitle("scrollview");
	}
}