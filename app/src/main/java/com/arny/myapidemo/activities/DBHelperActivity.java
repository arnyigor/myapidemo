package com.arny.myapidemo.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.adapters.SnappingLinearLayoutManager;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.SimpleViewHolder;
import com.arny.myapidemo.database.DB;
import com.arny.myapidemo.models.TestObject;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;

public class DBHelperActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<TestObject> objects;
	private SimpleBindableAdapter<TestObject, SimpleViewHolder> adapter;
	private EditText editText;
	private ProgressDialog pDialog;
	private int count;
	private Context context = DBHelperActivity.this;
    private boolean isDbLocked;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        initToolbar();
	    pDialog = new ProgressDialog(DBHelperActivity.this);
	    pDialog.setCancelable(false);
	    recyclerView = (RecyclerView) findViewById(R.id.sqlList);
	    editText = (EditText) findViewById(R.id.cntObjects);
	    recyclerView.setLayoutManager(new SnappingLinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
	    initAdapter();
        recyclerView.setAdapter(adapter);
		Button btnAddObject = (Button) findViewById(R.id.btnAddObject);
	    findViewById(R.id.btnScroll).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    String str = editText.getText().toString();
			    if (!Utility.empty(str)) {
				    recyclerView.scrollToPosition(Integer.parseInt(str));
			    }
		    }
	    });
		btnAddObject.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("StaticFieldLeak")
			@Override
			public void onClick(View v) {
				String cntStr = editText.getText().toString();
				if (!Utility.empty(cntStr)) {
                    if (isDbLocked) return;
					count = Integer.parseInt(cntStr);
					new AsyncTask<Void, Integer, ArrayList<TestObject>>() {
						@Override
						protected void onPreExecute() {
							super.onPreExecute();
							ToastMaker.toastInfo(context,"Добавляем "+count+" записей...");
						}

						@Override
						protected ArrayList<TestObject> doInBackground(Void... voids) {
                            isDbLocked = true;
                            ArrayList<TestObject> objects1 = new ArrayList<>();
							for (int i = 0; i < count; i++) {
								TestObject test = new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test");
								DBProvider.saveObject(context, "test", test);
								objects.add(test);
								objects1.add(test);
							}
							return objects1;
						}

						@Override
						protected void onProgressUpdate(Integer... values) {
							super.onProgressUpdate(values);
						}

						@Override
						protected void onPostExecute(ArrayList<TestObject> testObjects) {
							super.onPostExecute(testObjects);
                            adapter.addAll(testObjects);
                            setTitle( objects.size()+ " записей");
                            ToastMaker.toastSuccess(context,"Добавлено "+testObjects.size()+" записей...");
                            isDbLocked = false;
                        }
					}.execute();
				}else{
					TestObject test = new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test");
					DBProvider.saveObject(DBHelperActivity.this, "test", test);
					adapter.add(test);
					objects.add(test);
					setTitle(objects.size()+ " записей");
				}

			}
		});
		initList();
    }

	private void initAdapter() {
		adapter = new SimpleBindableAdapter<>(DBHelperActivity.this, R.layout.simple_example_item, SimpleViewHolder.class);
		adapter.setActionListener(new SimpleViewHolder.SimpleActionListener() {
		    @Override
		    public void onMoveToTop(int position) {
                if (isDbLocked) return;
		        adapter.moveChildTo(position, 0);
		    }

		    @Override
		    public void OnRemove(int position) {
		        if (isDbLocked) return;
		        DBProvider.deleteDB("test", "id = ?", new String[]{objects.get(position).getId()}, DBHelperActivity.this);
		        adapter.removeChild(position);
		        objects.remove(position);
			    setTitle( objects.size()+ " записей");
		    }

		    @Override
		    public void OnUp(int position) {
                if (isDbLocked) return;
		        int toPosition = position - 1;
		        adapter.moveChildTo(position, toPosition);
		    }

		    @Override
		    public void OnDown(int position) {
                if (isDbLocked) return;
		        int toPosition = position + 1;
		        adapter.moveChildTo(position, toPosition);
		    }

		    @Override
		    public void OnItemClickListener(int position, Object Item) {
		        Log.i(DBHelperActivity.class.getSimpleName(), "OnItemClickListener: position = " + position);
		    }
		});
	}

	@SuppressLint("StaticFieldLeak")
	protected void initList() {
        if (isDbLocked) return;
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				DroidUtils.showProgress(pDialog,"Загрузка записей...");
			}

			@Override
			protected Void doInBackground(Void... voids) {
                isDbLocked = true;
                getData();
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				adapter.clear();
				adapter.addAll(objects);
				setTitle(objects.size()+ " записей");
				recyclerView.setAdapter(adapter);
                isDbLocked = false;
                DroidUtils.hideProgress(pDialog);
			}
		}.execute();

	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        switch (item.getItemId()) {
	        case android.R.id.home:
		        super.onBackPressed();
		        return true;
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

}