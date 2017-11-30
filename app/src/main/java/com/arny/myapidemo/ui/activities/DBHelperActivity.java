package com.arny.myapidemo.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.arny.arnylib.adapters.SnappingLinearLayoutManager;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.FilterExampleAdapter;
import com.arny.myapidemo.adapters.SimpleViewHolder;
import com.arny.myapidemo.database.RoomDB;
import com.arny.myapidemo.database.TestDao;
import com.arny.myapidemo.models.Test;
import com.arny.myapidemo.models.TestObject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class DBHelperActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ArrayList<TestObject> objects = new ArrayList<>();
    private EditText editText;
    private ProgressDialog pDialog;
    private Context context = DBHelperActivity.this;
    private boolean isDbLocked;
    private Stopwatch stopwatch;
    private FilterExampleAdapter adapter;
    private int count = 1;
    private TestDao testDao;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        stopwatch = new Stopwatch();
        stopwatch.start();
        initUI();
        initAdapter();
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
        findViewById(R.id.btnAddObject).setOnClickListener(this);
        findViewById(R.id.btnAddroomTest).setOnClickListener(this);
        findViewById(R.id.btnScroll).setOnClickListener(this);
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
                    stopwatch.restart();
                    for (TestObject testObject : testObjects) {
                        long row = DBProvider.saveObject(context, "test", testObject);
                        String x = "Сохранение id:" + row + "-" + stopwatch.getElapsedTimeSecs(3) + " сек";
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
                TestObject testObject = objects.get(position);
                if (testObject != null) {
                    DBProvider.deleteDB("test", "_id = ?", new String[]{String.valueOf(testObject.getDbId())}, DBHelperActivity.this);
                }
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
        testDao = RoomDB.init(this).getTestDao();
    }

    protected void initList() {
        if (isDbLocked) return;
        stopwatch.restart();
        Utility.mainThreadObservable(DBProvider.getObjectsListRx(this,"test",null,null,null,null, TestObject.class)
        .map(testObjects -> {
            List<Test> listTest = testDao.getListTest();
            for (Test test : listTest) {
                TestObject testObject = new TestObject();
                testObject.setId(test.getGuid());
                testObject.setName(String.valueOf(test.getIndex()));
                testObject.setDbId(Long.valueOf(test.getId()));
                testObjects.add(testObject);
            }
            return testObjects;
        }))
			    .doOnSubscribe(disposable -> {
				    isDbLocked = true;
                    DroidUtils.showProgress(pDialog, "Загрузка записей...");
                })
			    .subscribe(testObjects -> {
				    isDbLocked = false;
				    DroidUtils.hideProgress(pDialog);
				    setAdapterList(testObjects);
			    },throwable -> {
                    isDbLocked = false;
                    DroidUtils.hideProgress(pDialog);
                    ToastMaker.toastError(this,throwable.getMessage());
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
                DBProvider.deleteDB("test", null, this);
                initList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnScroll:
                String str = editText.getText().toString();
                if (!Utility.empty(str)) {
                    recyclerView.scrollToPosition(Integer.parseInt(str));
                }
                break;
            case R.id.btnAddObject:
                rxSave();
                break;
            case R.id.btnAddroomTest:
                Test test = new Test();
                test.setActive(false);
                test.setGuid("27364208374");
                test.setIndex(7326483);
                Utility.mainThreadObservable(Observable.just(1).doOnSubscribe(disposable -> testDao.insert(test))).subscribe(integer -> initList(), Throwable::printStackTrace);
                break;
        }
    }
}