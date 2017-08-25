package com.arny.myapidemo.activities;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.database.DBLoader;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.MathUtils;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.SimpleRecyclerViewHolder;
import com.arny.myapidemo.adapters.SimpleViewHolder;
import com.arny.myapidemo.database.DB;
import com.arny.myapidemo.models.TestObject;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;

//import org.chalup.microorm.MicroOrm;

public class DBHelperActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{
    private ArrayList<String> data = new ArrayList<String>();
    private RecyclerView sqlList;
    private ArrayList<TestObject> objects;
    private Toolbar toolbar;
    private SimpleBindableAdapter<TestObject, SimpleRecyclerViewHolder> adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        initToolbar();
	    getLoaderManager().initLoader(R.id.db_loader, Bundle.EMPTY, this);
        sqlList = (RecyclerView) findViewById(R.id.sqlList);
        sqlList.setLayoutManager( new LinearLayoutManager(this));
        sqlList.setItemAnimator(new DefaultItemAnimator());
        adapter = new SimpleBindableAdapter<>(R.layout.recycler_item, SimpleRecyclerViewHolder.class);
        adapter.setActionListener(new SimpleViewHolder.SimpleActionListener() {
            @Override
            public void onMoveToTop(int position) {
                adapter.moveChildTo(position, 0);
            }

            @Override
            public void OnRemove(int position) {
                adapter.removeChild(position);
                DBProvider.deleteDB("test", "id = ?", new String[]{objects.get(position).getId()}, DBHelperActivity.this);
            }

            @Override
            public void OnUp(int position) {
                int toPosition = position - 1;
                adapter.moveChildTo(position, toPosition);
            }

            @Override
            public void OnDown(int position) {
                int toPosition = position + 1;
                adapter.moveChildTo(position, toPosition);
            }

            @Override
            public void OnItemClickListener(int position, Object Item) {
                Log.i(DBHelperActivity.class.getSimpleName(), "OnItemClickListener: position = " + position);
            }
        });
        sqlList.setAdapter(adapter);
		Button btnAddObject = (Button) findViewById(R.id.btnAddObject);
		btnAddObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TestObject test = new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test");
				DBProvider.saveObject(DBHelperActivity.this, "test", test);
				adapter.add(test);
			}
		});
		initList();
    }

	protected void initList() {
		getData();
        adapter.clear();
        adapter.addAll(objects);
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
        objects = DBProvider.getCursorObjectList(cursor, TestObject.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sql_menu, menu);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
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