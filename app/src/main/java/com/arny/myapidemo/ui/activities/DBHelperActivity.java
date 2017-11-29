package com.arny.myapidemo.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import com.arny.arnylib.adapters.SnappingLinearLayoutManager;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.Stopwatch;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.FilterExampleAdapter;
import com.arny.myapidemo.adapters.SimpleViewHolder;
import com.arny.myapidemo.database.AppDatabase;
import com.arny.myapidemo.database.DB;
import com.arny.myapidemo.models.TestObject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class DBHelperActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<TestObject> objects = new ArrayList<>();
    private EditText editText;
    private ProgressDialog pDialog;
    private Context context = DBHelperActivity.this;
    private boolean isDbLocked;
    private Stopwatch stopwatch;
    private FilterExampleAdapter adapter;
    private Button btnAddObject;
    private int count = 1;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        stopwatch = new Stopwatch();
        stopwatch.start();
        initUI();
        initAdapter();
        btnAddObject.setOnClickListener(v -> rxSave());
        initList();
        System.out.println("onCreate:" + stopwatch.getElapsedTimeMili() + "ms");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initUI() {
        initToolbar();
        pDialog = new ProgressDialog(DBHelperActivity.this);
        pDialog.setCancelable(false);
        recyclerView = findViewById(R.id.sqlList);
        editText = findViewById(R.id.cntObjects);
        recyclerView.setLayoutManager(new SnappingLinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnAddObject = findViewById(R.id.btnAddObject);
        findViewById(R.id.btnScroll).setOnClickListener(v -> {
            String str = editText.getText().toString();
            if (!Utility.empty(str)) {
                recyclerView.scrollToPosition(Integer.parseInt(str));
            }
        });
    }

    public void rxSave() {
	    String s = editText.getText().toString();
	    if (!Utility.empty(s)) {
		    count = Integer.parseInt(s);
	    }
	    Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        Observable.create(e -> new Thread(() -> {
            for (int i = 0; i < count; i++) {
                e.onNext(new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test"));
            }
            e.onComplete();
        }).start())
                .map(o -> (TestObject) o)
                .collect(ArrayList::new, (BiConsumer<ArrayList<TestObject>, TestObject>) ArrayList::add)
                .doOnSubscribe(disposable -> isDbLocked = true)
                .map(testObjects -> {
                    for (TestObject testObject : testObjects) {
                        stopwatch.restart();
	                    long row = AppDatabase.getDb(this).getTestDao().insert(testObject);
                        String x = "Сохранение " + row + ":" + stopwatch.getElapsedTimeMili() + " ms";
                        runOnUiThread(() -> setTitle(x));
                    }
                    return testObjects;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testObjects  -> {
                    isDbLocked = false;
                    updateAdapter(testObjects);
                    stopwatch.stop();
                }, Throwable::printStackTrace);
    }

    public void updateAdapter(ArrayList<TestObject> testObjects) {
        adapter.clear();
        objects = testObjects;
        adapter.addAll(testObjects);
        setTitle(objects.size() + " записей");
    }

    private void initAdapter() {
        adapter = new FilterExampleAdapter(this, R.layout.simple_example_item, new SimpleViewHolder.SimpleActionListener() {
            @Override
            public void onMoveToTop(int position) {
                if (isDbLocked) return;
                adapter.moveChildTo(position, 0);
            }

            @Override
            public void OnRemove(int position) {
                if (isDbLocked) return;
                String id = String.valueOf(objects.get(position).getDbId());
                Utility.mainThreadObservable(Observable.create(e -> {
                    e.onNext(AppDatabase.getDb(DBHelperActivity.this).getTestDao().delete(id));
                    e.onComplete();
                }).map(o -> (Integer)o)).subscribe(integer -> {
                    Log.i(DBHelperActivity.class.getSimpleName(), "OnRemove: row = " + integer);
                });
                adapter.removeChild(position);
                objects.remove(position);
                setTitle(objects.size() + " записей");
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
        adapter.setFilter((constraint, item) -> Utility.matcher("(?i).*" + constraint + ".*", item.getName()));
        recyclerView.setAdapter(adapter);
    }

    protected void initList() {
        if (isDbLocked) return;
        stopwatch.restart();
	    AppDatabase.getDb(DBHelperActivity.this).getTestDao().getObjects()
        .map(o -> (ArrayList<TestObject>)o)
        .subscribeOn(Schedulers.io())
			    .observeOn(AndroidSchedulers.mainThread())
			    .doOnSubscribe(disposable -> {
				    isDbLocked = true;
				    runOnUiThread(() -> DroidUtils.showProgress(pDialog, "Загрузка записей..."));
			    })
			    .subscribe(testObjects -> {
				    isDbLocked = false;
				    DroidUtils.hideProgress(pDialog);
				    setAdapterList(testObjects);
			    });
    }

    private void setAdapterList(ArrayList<TestObject> testObjects) {
        objects = new ArrayList<>(testObjects);
        adapter.addAll(testObjects);
        setTitle(objects.size() + " записей");
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.title_dbhelper));
        }
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

                return true;
            case R.id.action_clearall:
	            AppDatabase.getDb(DBHelperActivity.this).getTestDao().deleteAll();
                initList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}