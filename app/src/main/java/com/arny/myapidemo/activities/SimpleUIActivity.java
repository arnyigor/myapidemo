package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.utils.BaseUtils;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SimpleUIActivity extends AppCompatActivity implements View.OnClickListener, NumberPickerDialogFragment.NumberPickerDialogHandlerV2, DatePickerDialogFragment.DatePickerDialogHandler, TimePickerDialogFragment.TimePickerDialogHandler {
	private ArrayList<String> arrayList;
	private ArrayAdapter<String> simpleSpinnerAdapter,simpleListAdapter;
	private ArrayAdapter<CharSequence> resourceAdapter;
	private ProgressBar progessBar;
	private TextView seekbattv,progressbartv,ratingtv,spinnertv,radiogrtv,autoCompltv,tvTaskName;
	private SeekBar seekBar;
	private RatingBar ratingBar;
	private Handler handler = new Handler();
	private Spinner spinner;
	private Switch switchspinneradapter;
	private AutoCompleteTextView autoComplit;
	private String[] spinnerarray = {"Spinner 01","Spinner 02","Spinner 03","Spinner 04"};
	private int progress = 0,spinnerPosition;
	private RadioGroup radioGroup;
	private EditText edtTaskName;
	private boolean modeEditor;
	private String editedParam;
	private Button btnEditingOk;
	private Button btnNumber;
	private Button btnDate;
	private Button btnTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleui);
		if	(getSupportActionBar()!=null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		initUI();
		initLists();
		initListeners();
		initProgressBar();
		initUIState();
	}

	private void initUIState() {
		setModeEditor(false);
		refreshEditingMode();
	}

	private void initProgressBar() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (progress <= 100) {
					handler.post(new Runnable() {
						@Override
						public void run() {
						progessBar.setProgress(progress);
						progressbartv.setText(String.format("Progress:%d",progress));
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

	private void initListeners() {
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
				ArrayAdapter<?> adapter1 = isChecked ? simpleSpinnerAdapter : resourceAdapter;
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
	}

	private void initLists() {
		arrayList = new ArrayList<>();
		arrayList.add("Android");
		arrayList.add("IOS");
		simpleSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerarray);
		simpleListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
		resourceAdapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
		simpleSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(simpleSpinnerAdapter);
		autoComplit.setAdapter(simpleListAdapter);
	}

	private void initUI() {
		progessBar = (ProgressBar) findViewById(R.id.progressBar1);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		spinner = (Spinner) findViewById(R.id.spinner);
		switchspinneradapter = (Switch) findViewById(R.id.switchspinneradapter);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		autoComplit = (AutoCompleteTextView) findViewById(R.id.autoComplit);
		spinnertv = (TextView) findViewById(R.id.spinnertv);
		seekbattv = (TextView) findViewById(R.id.seekbattv);
		progressbartv = (TextView) findViewById(R.id.progressbartv);
		ratingtv = (TextView) findViewById(R.id.ratingtv);
		radiogrtv = (TextView) findViewById(R.id.radiogrtv);
		autoCompltv = (TextView) findViewById(R.id.autoCompltv);
		tvTaskName = (TextView) findViewById(R.id.tvTaskName);
		edtTaskName = (EditText) findViewById(R.id.edtTaskName);
		btnEditingOk = (Button) findViewById(R.id.btnEditingOk);
		btnNumber = (Button) findViewById(R.id.btnNumber);
		btnDate = (Button) findViewById(R.id.btnDate);
		btnTime = (Button) findViewById(R.id.btnTime);
		btnEditingOk.setOnClickListener(this);
		btnNumber.setOnClickListener(this);
		btnTime.setOnClickListener(this);
		btnDate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.simple_ui_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				// ProjectsActivity is my 'home' activity
				super. onBackPressed();
				return true;
			case R.id.action_mode_edit:
				setModeEditor(true);
				refreshEditingMode();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void refreshEditingMode() {
		if (isModeEditor()){
			if (!BaseUtils.empty(editedParam)){
				edtTaskName.setText(editedParam);
			}
			edtTaskName.setVisibility(View.VISIBLE);
			tvTaskName.setVisibility(View.GONE);
			btnEditingOk.setVisibility(View.VISIBLE);
		}else{
			if (!BaseUtils.empty(edtTaskName.getText().toString())){
				editedParam = edtTaskName.getText().toString().trim();
			}else{
				editedParam = "no text";
			}
			tvTaskName.setText(editedParam);
			edtTaskName.setVisibility(View.GONE);
			tvTaskName.setVisibility(View.VISIBLE);
			btnEditingOk.setVisibility(View.GONE);
		}

	}

	public boolean isModeEditor() {
		return modeEditor;
	}

	public void setModeEditor(boolean modeEditor) {
		this.modeEditor = modeEditor;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btnEditingOk:
				setModeEditor(false);
				refreshEditingMode();
			break;
			case R.id.btnNumber:
				NumberPickerBuilder npb = new NumberPickerBuilder()
						.setFragmentManager(getSupportFragmentManager())
						.setStyleResId(R.style.BetterPickersDialogFragment_Light);
				npb.show();
			break;
			case R.id.btnDate:
				DatePickerBuilder dpb = new DatePickerBuilder()
						.setFragmentManager(getSupportFragmentManager())
						.setStyleResId(R.style.BetterPickersDialogFragment_Light);
				dpb.show();
			break;
			case R.id.btnTime:
				TimePickerBuilder tpb = new TimePickerBuilder()
						.setFragmentManager(getSupportFragmentManager())
						.setStyleResId(R.style.BetterPickersDialogFragment_Light);
				tpb.show();
			break;
		}
	}

	@Override
	public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogNumberSet: decimal = " + decimal);
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogNumberSet: isNegative = " + isNegative);
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogNumberSet: fullNumber = " + fullNumber.toString());
	}

	@Override
	public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogDateSet: year = " + year);
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogDateSet: monthOfYear = " + monthOfYear);
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogDateSet: dayOfMonth = " + dayOfMonth);
	}

	@Override
	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogTimeSet: hourOfDay = " + hourOfDay);
		Log.i(SimpleUIActivity.class.getSimpleName(), "onDialogTimeSet: minute = " + minute);
	}
}
