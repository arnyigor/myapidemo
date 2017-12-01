package com.arny.myapidemo.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import com.arny.arnylib.adapters.SnappingLinearLayoutManager;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.FilterExampleAdapter;
import com.arny.myapidemo.adapters.SimpleViewHolder;
import com.arny.myapidemo.database.RoomDB;
import com.arny.myapidemo.models.Test;
import com.arny.myapidemo.models.TestObject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class DBHelperActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ArrayList<TestObject> objects = new ArrayList<>();
    private EditText editText;
    private ProgressDialog pDialog;
    private Context context = DBHelperActivity.this;
    private boolean isDbLocked;
    private Stopwatch totalTime;
    private FilterExampleAdapter adapter;
    private int count = 1;
    private TextView tvInfo;
    private ArrayList<Double> doubles;
    private Switch switchDb;
    private boolean dbRoom = false;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dbhelper);
        totalTime = new Stopwatch();
        totalTime.start();
        initUI();
        initAdapter();
        initList();
        System.out.println("onCreate:" + totalTime.getElapsedTimeMili() + "ms");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initUI() {
        initToolbar();
        pDialog = new ProgressDialog(DBHelperActivity.this);
        pDialog.setCancelable(false);
        tvInfo = findViewById(R.id.tvInfo);
        switchDb = findViewById(R.id.switchDb);
        dbRoom = Config.getBoolean("dbroom", false, this);
        switchDb.setChecked(dbRoom);
        switchDb.setText(dbRoom ? "Room" : "Sqlite");
        recyclerView = findViewById(R.id.sqlList);
        editText = findViewById(R.id.cntObjects);
        recyclerView.setLayoutManager(new SnappingLinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        findViewById(R.id.btnAddObject).setOnClickListener(this);
        findViewById(R.id.btnScroll).setOnClickListener(this);
    }

    public void rxSave() {
	    String s = editText.getText().toString();
	    if (!Utility.empty(s)) {
		    count = Integer.parseInt(s);
	    }
        Stopwatch saveTime = new Stopwatch();
        saveTime.start();
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
                    totalTime.restart();
                    doubles = new ArrayList<>();
                    long iter = 0;
                    int size = testObjects.size();
                    for (TestObject testObject : testObjects) {
                        iter++;
                        saveTime.restart();
                        long row;
                        if (dbRoom) {
                            Test test = new Test();
                            test.setTitle(testObject.getName());
                            test.setGuid(testObject.getId());
                            row = RoomDB.getDb(this).getTestDao().insert(test);
                        } else {
                            ContentValues cv = new ContentValues();
                            cv.put("id",testObject.getId());
                            cv.put("title",testObject.getName());
                            row = DBProvider.insertDB("test", cv,context);
                        }
                        double st = saveTime.getElapsedTimeSecs(3);
                        double average = MathUtils.getAverage(MathUtils.fillAverage(st, 10, this.doubles));
                        String updateText = "Сохранение id:" + row + " [" + iter + "/" + size + "] Среднее время:" + MathUtils.round(average, 3) + " сек Общее:" + totalTime.getElapsedTimeSecs(3) + " сек";
                        Log.i(DBHelperActivity.class.getSimpleName(), "rxSave: updateText:" + updateText);
                        runOnUiThread(() -> tvInfo.setText(updateText));
                    }
                    return testObjects;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testObjects  -> {
                    isDbLocked = false;
                    updateAdapter(testObjects);
                    totalTime.stop();
                }, Throwable::printStackTrace);
    }

    @SuppressLint("DefaultLocale")
    public void updateAdapter(ArrayList<TestObject> testObjects) {
        adapter.clear();
        objects = testObjects;
        adapter.addAll(testObjects);
    }

    private void initAdapter() {
        adapter = new FilterExampleAdapter(this, R.layout.simple_example_item, new SimpleViewHolder.SimpleActionListener() {
            @Override
            public void onMoveToTop(int position) {
                if (isDbLocked) return;
                adapter.moveChildTo(position, 0);
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void OnRemove(int position) {
                if (isDbLocked) return;
                TestObject testObject = objects.get(position);
//                if (testObject != null) {
//                    DBProvider.deleteDB("test", "_id = ?", new String[]{String.valueOf(testObject.getDbId())}, DBHelperActivity.this);
//                }
                adapter.removeChild(position);
                objects.remove(position);
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
        switchDb.setOnCheckedChangeListener((compoundButton, b) -> {
            this.dbRoom = b;
            Config.setBoolean("dbroom", b, this);
            switchDb.setText(b ? "Room" : "Sqlite");
            initList();
        });
    }

    private TestObject getFromTest(Test test) {
        TestObject testObject = new TestObject();
        testObject.setDbId(test.getId());
        testObject.setName(test.getTitle());
        testObject.setId(test.getGuid());
        return testObject;
    }

    @SuppressLint("DefaultLocale")
    protected void initList() {
        if (isDbLocked) return;
        totalTime.restart();
        if (dbRoom) {
            Utility.mainThreadObservable(Observable.create(e -> {
                e.onNext(RoomDB.getDb(this).getTestDao().getListTest());
                e.onComplete();
            })
                    .map(o -> (ArrayList<Test>) o).map(tests -> {
                        ArrayList<TestObject> testObjects = new ArrayList<>();
                        for (Test test : tests) {
                            testObjects.add(getFromTest(test));
                        }
                        return testObjects;
                    }))
                    .subscribe(testObjects -> {
                        isDbLocked = false;
                        DroidUtils.hideProgress(pDialog);
                        setAdapterList(testObjects);
                        tvInfo.setText(String.format("%d записей Время загрузки:%.3f сек", objects.size(), totalTime.getElapsedTimeSecs(3)));
                    }, throwable -> {
                        isDbLocked = false;
                        DroidUtils.hideProgress(pDialog);
                        ToastMaker.toastError(this, throwable.getMessage());
                    });
        } else {
            Utility.mainThreadObservable(DBProvider.getObjectsListRx(this, "test", null, null, null, null, TestObject.class)
                    .doOnSubscribe(disposable -> {
                        isDbLocked = true;
                        runOnUiThread(() -> DroidUtils.showProgress(pDialog, "Загрузка записей..."));
                    })
            )
                    .subscribe(testObjects -> {
                        isDbLocked = false;
                        DroidUtils.hideProgress(pDialog);
                        setAdapterList(testObjects);
                        tvInfo.setText(String.format("%d записей Время загрузки:%.3f сек", objects.size(), totalTime.getElapsedTimeSecs(3)));
                    }, throwable -> {
                        isDbLocked = false;
                        DroidUtils.hideProgress(pDialog);
                        ToastMaker.toastError(this, throwable.getMessage());
                    });
        }
    }

    @SuppressLint("DefaultLocale")
    private void setAdapterList(ArrayList<TestObject> testObjects) {
        objects = new ArrayList<>(testObjects);
        adapter.addAll(testObjects);
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
                if (dbRoom) {
                    Utility.mainThreadObservable(Observable.just(1).doOnSubscribe(disposable -> {
                        RoomDB.getDb(this).getTestDao().delete();
                    })).subscribe(integer -> {
                        Log.i(DBHelperActivity.class.getSimpleName(), "onOptionsItemSelected: integer:" + integer);
                    }, Throwable::printStackTrace);
                } else {
                    DBProvider.deleteDB("test", null, this);
                }
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
        }
    }
}