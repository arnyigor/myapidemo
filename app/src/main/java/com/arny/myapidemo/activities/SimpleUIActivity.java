package com.arny.myapidemo.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.arny.myapidemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleUIActivity extends AppCompatActivity {
	List<String> arrayList = new ArrayList<String>();
	ProgressBar progessBar;
	TextView seekbattv,progressbartv,ratingtv,spinnertv,radiogrtv,autoCompltv;
	SeekBar seekBar;
	RatingBar ratingBar;
	Handler handler = new Handler();
	Spinner spinner;
	Switch switchspinneradapter;
	AutoCompleteTextView autoComplit;
	String[] spinnerarray = {"Spinner 01","Spinner 02","Spinner 03","Spinner 04"};
	int progress = 0,spinnerPosition;
	RadioGroup radioGroup;
	ActionBar actionBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleui);
		try {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//IDs
		progessBar = (ProgressBar) findViewById(R.id.progressBar1);
		seekbattv = (TextView) findViewById(R.id.seekbattv);
		progressbartv = (TextView) findViewById(R.id.progressbartv);
		ratingtv = (TextView) findViewById(R.id.ratingtv);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		spinnertv = (TextView) findViewById(R.id.spinnertv);
		spinner = (Spinner) findViewById(R.id.spinner);
		switchspinneradapter = (Switch) findViewById(R.id.switchspinneradapter);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radiogrtv = (TextView) findViewById(R.id.radiogrtv);
		autoComplit = (AutoCompleteTextView) findViewById(R.id.autoComplit);
		autoCompltv = (TextView) findViewById(R.id.autoCompltv);
		//init
		arrayList.add("Android");
		arrayList.add("IOS");
		final ArrayAdapter<?> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_spinner_dropdown_item, spinnerarray);
		ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
		final ArrayAdapter<?> resourceAdapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		autoComplit.setAdapter(adapterList);
		//listeners
		autoComplit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				autoCompltv.setText(autoComplit.getText());
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.radioButton1:
						radiogrtv.setText("RadioButton 1");
					break;
					case R.id.radioButton2:
						radiogrtv.setText("RadioButton 2");
						break;
				}
			}
		});
		switchspinneradapter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ArrayAdapter<?> adapter1 = isChecked ? adapter : resourceAdapter;
				spinner.setAdapter(adapter1);
				adapter1.notifyDataSetChanged();
				String selected = String.valueOf(spinner.getSelectedItem());
				spinnertv.setText("Spinner text:" + selected + " id:" +spinnerPosition );
			}
		});
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selected = String.valueOf(spinner.getSelectedItem());
				spinnerPosition = position;
				spinnertv.setText("Spinner text:" + selected + " id:" +spinnerPosition );
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				ratingBar.setRating(rating);
				ratingtv.setText("rating:" + String.valueOf(rating));
			}
		});
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				seekbattv.setText("seekbar progress: " + String.valueOf(seekBar.getProgress()));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (progress <= 100) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							progessBar.setProgress(progress);
							progressbartv.setText("ProgressBar progress:" + String.valueOf(progress));
						}
					});
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progress++;
					if (progress == 100) {
						progress=0;
					}
				}
			}
		}).start();
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
}
