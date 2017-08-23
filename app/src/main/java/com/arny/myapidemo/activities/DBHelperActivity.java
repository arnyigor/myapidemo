package com.arny.myapidemo.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.arny.arnylib.database.DBLoader;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.MathUtils;
import com.arny.myapidemo.R;
import com.arny.myapidemo.database.DB;
import com.arny.myapidemo.models.TestObject;
//import org.chalup.microorm.MicroOrm;

import java.util.ArrayList;

public class DBHelperActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>  {
    ArrayList<String> data = new ArrayList<String>();
    ListView sqlList;
    ArrayList<TestObject> objects;
    private Toolbar toolbar;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        initToolbar();
	    getLoaderManager().initLoader(R.id.db_loader, Bundle.EMPTY, this);
        sqlList = (ListView) findViewById(R.id.sqlList);
		Button btnAddObject = (Button) findViewById(R.id.btnAddObject);
		btnAddObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TestObject test = new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test");
				DBProvider.saveObject(DBHelperActivity.this, "test", test);
				initList();
			}
		});
		initList();
    }

	protected void initList() {
		getData();
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
		sqlList.setAdapter(adapter);
	}

	private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.title_dbhelper));
        }
    }

    private void getData() {
	    Cursor cursor =  DBProvider.selectDB("test", null, null, null, this);
	    data.clear();
	    objects = new ArrayList<>();
        objects = DBProvider.getCursorObjectList(cursor, TestObject.class);
        for (TestObject object : objects) {
		    data.add(object.getName());
	    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sql_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_additem:
                return true;
            case R.id.menu_action_get_object_fields:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                         DB.testDb(DBHelperActivity.this);
                        return null;
                    }
                }.execute();

                return true;
            case R.id.action_clearall:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
			case R.id.db_loader:
				return new DBLoader(DBHelperActivity.this);
			default:
				return null;
		}
	}


	@Override
	public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {

	}

	@Override
	public void onLoaderReset(android.content.Loader<Cursor> loader) {

	}


}