package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import com.arny.myapidemo.R;

public class BrowserActivity extends AppCompatActivity {
	private  static final int TIME_INTERVAL = 1000; // # milliseconds, desired time passed between two back presses.
	WebView webView;
	EditText editText;
	Button button;
	private long mBackPressed;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		webView = (WebView) findViewById(R.id.webView);
		editText = (EditText) findViewById(R.id.webeditText);
		button = (Button) findViewById(R.id.webViewGo);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!editText.equals("")) {
					String url = "http://" + editText.getText().toString();
					webView.loadUrl(url);
					webView.setWebViewClient(new WebViewClient(){
						@Override
						public boolean shouldOverrideUrlLoading(WebView view, String url) {
							return super.shouldOverrideUrlLoading(view, url);
						}
					});
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				// ProjectsActivity is my 'home' activity
				super. onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
			super.onBackPressed();
			return;
		} else {
			webView.goBack();
		}
		mBackPressed = System.currentTimeMillis();
	}


}