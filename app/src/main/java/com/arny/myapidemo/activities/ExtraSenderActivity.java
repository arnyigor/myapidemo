package com.arny.myapidemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.arny.myapidemo.R;
import com.arny.myapidemo.utils.Consts;

public class ExtraSenderActivity extends AppCompatActivity {


	int activityResult;
	Intent intent;
	EditText edtIntentText;
	Button btnSendIntent, btnGetResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intentactivity);
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		edtIntentText = (EditText) findViewById(R.id.edtIntentText);
		btnSendIntent = (Button) findViewById(R.id.btnSendIntent);
		btnGetResult = (Button) findViewById(R.id.btnGetResult);
		btnSendIntent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), EnterIntentAcivity.class);
				intent.putExtra(Consts.INTENT_NAME_TEST, edtIntentText.getText().toString());
				startActivity(intent);
			}
		});
		btnGetResult.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), EnterIntentAcivity.class);
				startActivityForResult(intent, Consts.PICK_CONTACT_REQUEST);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			edtIntentText.setText("data is null");
			activityResult = 1;
			return;
		}
		activityResult =0;
		String name = data.getStringExtra("name");
		edtIntentText.setText("Your name is " + name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.uimenu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.findItem(R.id.action_new);
		menuItem.setVisible(activityResult == 0);
		return super.onPrepareOptionsMenu(menu);
	}
}