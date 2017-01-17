package com.arny.myapidemo.activities;

//imports start==========
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.arny.myapidemo.R;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
//imports end==========

//==============Activity start=========================
public class AlertCustomActivity extends Activity {
	// =============Variables start================
	final int DIALOG = 1;
	// =============Variables end================
	// ==============Forms variables start==============

	int btn;
	LinearLayout view;
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
	TextView tvCount;
	ArrayList<TextView> textViews;

	// ==============Forms variables end==============
	// ====================onCreate start=========================
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertcustom);

		// ================Forms Ids start=========================
		// ================Forms Ids end=========================

		// ==================onCreateCode start=========================
		textViews = new ArrayList<TextView>(10);
		// ==================onCreateCode end=========================

	}// ============onCreate end====================

	// ====================CustomCode
	// start======================================
	// ====================CustomCode end======================================

	// ====================OnClicks======================================

	public void onclick(View v) {
		btn = v.getId();
		showDialog(DIALOG);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Custom dialog");
		view = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dialogcustom, null);
		adb.setView(view);
		tvCount = (TextView) view.findViewById(R.id.tvCount);
		return adb.create();
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		if (id == DIALOG) {
			TextView tvTime = (TextView) dialog.getWindow().findViewById(
					R.id.tvTime);
			Date curDate = new Date(System.currentTimeMillis());
			tvTime.setText(formatter.format(curDate));
			if (btn == R.id.btnAdd) {
				TextView tv = new TextView(this);
				view.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				tv.setText("TextView " + (textViews.size() + 1));
				textViews.add(tv);
			} else {
				if (textViews.size() > 0) {
					TextView tv = textViews.get(textViews.size() - 1);
					view.removeView(tv);
					textViews.remove(tv);
				}
			}
			tvCount.setText(" TextView = " + textViews.size());
		}
	}

	// ====================OnClicks end======================================
}// ===================Activity end==================================
// ===================SimpleActivity==================================