package com.arny.myapidemo.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.utils.DB;

//==============Activity start=========================
public class SimpleAdapterActivity extends ListActivity {

	// =============Variables start================
	private static final int CM_DELETE_ID = 101;
	final Context context = this;
	Dialog dialog;
	ListView lvData;
	DB db;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;
	int DIALOG_DATE = 1;
	int myYear = 2011;
	int myMonth = 02;
	int myDay = 03;
	private String dialtxt;
	// =============Variables end================

	// ==============Forms variables start==============
	// ==============Forms variables end==============
	// ====================onCreate start=========================
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_adapter_main);

		// ================Forms Ids start=========================
		// ================Forms Ids end=========================

		// ==================onCreateCode start=========================
		db = new DB(this);
		db.open();

		cursor = db.getAllData();
		startManagingCursor(cursor);

		String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_TXT };
		int[] to = new int[] { R.id.ivImg, R.id.tvText };
		scAdapter = new SimpleCursorAdapter(this, R.layout.simple_adapter_item, cursor, from, to);
		ListView lvData = (ListView) findViewById(android.R.id.list);
		lvData.setAdapter(scAdapter);
		registerForContextMenu(lvData);
		// ==================onCreateCode end=========================

	}// ============onCreate end====================
		// ====================CustomCode
		// start=====================================

/*	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_DATE) {
			DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
			return tpd;
		}
		return super.onCreateDialog(id);
	}

	OnDateSetListener myCallBack = new OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			myYear = year;
			myMonth = monthOfYear;
			myDay = dayOfMonth;
			tvDate.setText("Today is " + myDay + "/" + myMonth + "/" + myYear);
		}
	};*/

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try {
			final long pos = l.getAdapter().getItemId(position);
			String str = cursor.getString(cursor.getColumnIndexOrThrow(DB.COLUMN_TXT));
			LayoutInflater li = LayoutInflater.from(context);
			View dialogView = li.inflate(R.layout.sqllitedialog, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setView(dialogView);
			final EditText userInput = (EditText) dialogView.findViewById(R.id.editSqlLitetext);
			userInput.setText(str);
			// set dialog message
			alertDialogBuilder.setCancelable(false).setTitle("Edit item")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@SuppressWarnings("deprecation")
						public void onClick(DialogInterface dialog, int id) {
							dialtxt = userInput.getText().toString();
							db.edtRec(pos, dialtxt);
							cursor.requery();
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sql_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_additem:

			LayoutInflater li = LayoutInflater.from(context);
			View dialogView = li.inflate(R.layout.sqllitedialog, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setView(dialogView);
			final EditText userInput = (EditText) dialogView.findViewById(R.id.editSqlLitetext);
			// set dialog message
			alertDialogBuilder.setCancelable(false).setTitle("Add item")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@SuppressWarnings("deprecation")
						public void onClick(DialogInterface dialog, int id) {
							dialtxt = userInput.getText().toString();
							db.addRec(dialtxt, R.drawable.ic_launcher);
							cursor.requery();
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();
			break;
		case R.id.action_clearall:
			AlertDialog.Builder quitDialog = new AlertDialog.Builder(SimpleAdapterActivity.this);
			quitDialog.setTitle(getString(R.string.clearall) + "?");
			quitDialog.setNegativeButton(getString(R.string.no_answer), null);
			quitDialog.setPositiveButton(getString(R.string.yes_answer), new DialogInterface.OnClickListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(DialogInterface dialog, int id) {
					db.clrRec();
					cursor.requery();// ��������� ������
				}
			});
			AlertDialog alert = quitDialog.create();
			alert.show();
			break;
		default:

			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
	}

	public boolean onContextItemSelected(MenuItem item) {
		final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int id = item.getItemId();
		switch (id) {
		case CM_DELETE_ID:
			AlertDialog.Builder quitDialog = new AlertDialog.Builder(SimpleAdapterActivity.this);
			quitDialog.setTitle(getString(R.string.delete_record) + "?");
			quitDialog.setNegativeButton(getString(R.string.no_answer), null);
			quitDialog.setPositiveButton(getString(R.string.yes_answer), new DialogInterface.OnClickListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(DialogInterface dialog, int id) {
					db.delRec(acmi.id);// ��������� id ������ � �������
										// ��������������� ������ � ��
					cursor.requery();// ��������� ������
				}
			});
			AlertDialog alert = quitDialog.create();
			alert.show();
			break;
		default:

			break;
		}

		return super.onContextItemSelected(item);
	}

	protected void onDestroy() {
		super.onDestroy();
		// ��������� ����������� ��� ������
		db.close();
	}

	// ====================CustomCode end======================================

	// ====================OnClicks======================================
	// ======================Exit dialog==============================
	// ======================Exit dialog end==============================
	// ====================OnClicks end======================================
}// ===================Activity end==================================
	// ===================SimpleActivity==================================