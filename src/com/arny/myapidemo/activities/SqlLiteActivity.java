package com.arny.myapidemo.activities;//Package name

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.arny.myapidemo.R;

import java.util.ArrayList;
import java.util.Random;


//==============Activity start=========================
public class SqlLiteActivity extends AppCompatActivity {
    // =============Variables start================
    DBHelper dbHelper;
    private static final String TABLE_NAME = "myDB";
    final Random random = new Random();
    // =============Variables end================

    // ==============Forms variables start==============
    Button btnAdd, btnRead, btnClear, btnUpd, btnDel, btnCreate, btnDrop;
    EditText etText, etID;
    TextView dbgTxt;
    ListView listMain;
    ArrayList<Object> data = new ArrayList<Object>();

    // ==============Forms variables end==============
//====================onCreate start=========================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllite);

        // ================Forms Ids start=========================
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(onClickListener);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(onClickListener);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(onClickListener);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(onClickListener);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(onClickListener);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(onClickListener);

        btnDrop = (Button) findViewById(R.id.btnDrop);
        btnDrop.setOnClickListener(onClickListener);

        etText = (EditText) findViewById(R.id.etText);
        etID = (EditText) findViewById(R.id.etID);
        dbgTxt = (TextView) findViewById(R.id.debugtext);
        // ================Forms Ids end=========================
        dbHelper = new DBHelper(this);

        // ==================onCreateCode start=========================


        // ==================onCreateCode end=========================

    }//============onCreate end====================

    // ====================CustomCode start======================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
// ====================CustomCode end======================================

    // ====================OnClicks======================================
    public void onViewDataClick(MenuItem item) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_action_viewdata:
                Intent intent = new Intent(SqlLiteActivity.this, SqlListActivity.class);
                startActivity(intent);
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues cv = new ContentValues();
            String TEXT = etText.getText().toString();
            int rand_int = 0;
            String id = etID.getText().toString();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sqlCreateif = "create table IF NOT EXISTS " + TABLE_NAME + " ("
                    + "id integer primary key autoincrement,"
                    + "TEXT text);";
            switch (v.getId()) {

                case R.id.btnAdd:

                    try {
                        dbgTxt.setText("--- onCreate " + TABLE_NAME + " ---\n");
                        db.execSQL(sqlCreateif);
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    dbgTxt.append("--- Insert in " + TABLE_NAME + ": ---\n");
                    if (TEXT.length() == 0) {
                        rand_int = random.nextInt(1000) + 1;
                        TEXT = String.valueOf(rand_int);
                        Toast.makeText(getApplication(), TEXT + " random and =" + String.valueOf(rand_int), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), TEXT + " not random", Toast.LENGTH_LONG).show();
                    }

                    cv.put("TEXT", TEXT);
                    long rowID = db.insert(TABLE_NAME, null, cv);
                    dbgTxt.append("row inserted, ID = " + rowID);
                    break;

                case R.id.btnRead:

                    try {
                        dbgTxt.setText("--- onCreate " + TABLE_NAME + " ---\n");
                        db.execSQL(sqlCreateif);
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    try {
                        dbgTxt.setText("--- Rows in " + TABLE_NAME + ": ---\n");
                        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
                        if (c.moveToFirst()) {
                            int idColIndex = c.getColumnIndex("id");
                            int textColIndex = c.getColumnIndex("TEXT");
                            do {
                                dbgTxt.append("ID = " + c.getInt(idColIndex) + ", TEXT = "
                                        + c.getString(textColIndex) + "\n");
                            } while (c.moveToNext());
                        } else
                            dbgTxt.append("0 rows");
                        c.close();
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                        dbgTxt.append("--- " + TABLE_NAME + " is not EXISTS: ---\n");
                    }
                    break;

                case R.id.btnClear:

                    try {
                        dbgTxt.setText("--- onClear " + TABLE_NAME + " ---\n");
                        db.execSQL(sqlCreateif);
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    try {
                        dbgTxt.setText("--- Clear " + TABLE_NAME + ": ---\n");
                        int clearCount = db.delete(TABLE_NAME, null, null);
                        dbgTxt.append("deleted rows count = " + clearCount + "\n");
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                        dbgTxt.append("--- " + TABLE_NAME + " is not EXISTS: ---\n");
                    }
                    break;

                case R.id.btnUpd:

                    try {
                        dbgTxt.setText("--- onCreate " + TABLE_NAME + " ---\n");
                        db.execSQL(sqlCreateif);
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    if (id.equalsIgnoreCase("")) {
                        break;
                    }
                    dbgTxt.setText("--- Update " + TABLE_NAME + ": ---\n");
                    cv.put("TEXT", TEXT);
                    int updCount = db.update(TABLE_NAME, cv, "id = ?",
                            new String[]{id});
                    dbgTxt.append("updated rows count = " + updCount + "\n");
                    break;

                case R.id.btnDel:

                    try {
                        dbgTxt.setText("--- onCreate " + TABLE_NAME + " ---\n");
                        db.execSQL(sqlCreateif);
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    if (id.equalsIgnoreCase("")) {
                        break;
                    }
                    dbgTxt.setText("--- Delete from " + TABLE_NAME + ": ---\n");
                    int delCount = db.delete(TABLE_NAME, "id = " + id, null);
                    dbgTxt.append("deleted rows count = " + delCount + "\n");
                    break;

                case R.id.btnCreate:
                    dbgTxt.setText("--- onCreate " + TABLE_NAME + " ---\n");
                    try {
                        db.execSQL(sqlCreateif);
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    break;

                case R.id.btnDrop:
                    try {
                        dbgTxt.setText("--- onCreate " + TABLE_NAME + " ---\n");
                        db.execSQL(sqlCreateif);
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    String sqlprop = "drop table " + TABLE_NAME + "";
                    try {
                        db.execSQL(sqlprop);
                        dbgTxt.setText("--- onDrop database ---\n");
                    } catch (SQLException e) {
                        dbgTxt.append(e.toString());
                    }
                    break;

            }

            dbHelper.close();
        }
    };


    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, TABLE_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            dbgTxt.append("--- onCreate " + TABLE_NAME + " ---\n");
            db.execSQL("create table " + TABLE_NAME + " ("
                    + "id integer primary key autoincrement,"
                    + "TEXT text);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    // ====================CustomCode end======================================

    // ====================OnClicks======================================
    //======================Exit dialog==============================
    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(SqlLiteActivity.this);
        quitDialog.setTitle("Exit?");
        quitDialog.setNegativeButton("yes", null);
        quitDialog.setPositiveButton("no",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = quitDialog.create();
        alert.show();
    }
    //======================Exit dialog end==============================
    // ====================OnClicks end======================================


}// ===================Activity end==================================
//===================SimpleActivity==================================
