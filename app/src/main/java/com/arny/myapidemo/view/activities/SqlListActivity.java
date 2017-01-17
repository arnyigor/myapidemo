package com.arny.myapidemo.view.activities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.arny.myapidemo.R;

import java.util.ArrayList;


//==============Activity start=========================
public class SqlListActivity extends Activity {
    // =============Variables start================
    DBHelper dbHelper;
    private static final String TABLE_NAME = "myDB";
    ArrayList<String> data = new ArrayList<String>();
    // =============Variables end================

    // ==============Forms variables start==============
    TextView mTextView;

    // ==============Forms variables end==============
//====================onCreate start=========================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);

        // ================Forms Ids start=========================
        mTextView = (TextView) findViewById(R.id.textSelect);
        // ================Forms Ids end=========================

        // ==================onCreateCode start=========================
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int textColIndex = c.getColumnIndex("TEXT");
                do {
                    data.add(c.getString(textColIndex));
                } while (c.moveToNext());
            } else
                c.close();
        } catch (SQLException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
        }
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        lvMain.setAdapter(adapter);
        // ==================onCreateCode end=========================
    }//============onCreate end====================

    // ====================CustomCode start======================================
    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, TABLE_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " ("
                    + "id integer primary key autoincrement,"
                    + "TEXT text);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }


}