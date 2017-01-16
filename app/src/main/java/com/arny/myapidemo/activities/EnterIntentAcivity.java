package com.arny.myapidemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.helpers.Consts;

public class EnterIntentAcivity extends AppCompatActivity {
	TextView textView;
	Intent intent;
	Button btnOkResult;
	EditText edtIntentResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enterintent);
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		textView = (TextView) findViewById(R.id.intenttv);
		textView.setText(getIntent().getStringExtra(Consts.INTENT_NAME_TEST));
		edtIntentResult = (EditText) findViewById(R.id.edtIntentResult);
		btnOkResult = (Button) findViewById(R.id.btnOkResult);
		btnOkResult.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("name", edtIntentResult.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}