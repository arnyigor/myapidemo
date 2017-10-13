package com.arny.myapidemo.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Filter;
import com.arny.arnylib.adapters.SnappingLinearLayoutManager;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.Stopwatch;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.FilterExampleAdapter;
import com.arny.myapidemo.adapters.SimpleViewHolder;
import com.arny.myapidemo.database.DB;
import com.arny.myapidemo.models.TestObject;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class DBHelperActivity extends AppCompatActivity  implements Filter.FilterListener{
    private RecyclerView recyclerView;
    private ArrayList<TestObject> objects = new ArrayList<>();
    private EditText editText;
    private ProgressDialog pDialog;
    private Context context = DBHelperActivity.this;
    private boolean isDbLocked;
    private Stopwatch stopwatch;
    private int count;
    private FilterExampleAdapter adapter;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        initToolbar();
        stopwatch = new Stopwatch();
        stopwatch.start();

        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();

        System.out.println("onCreate:" + stopwatch.getElapsedTimeMili() + "ms");
        pDialog = new ProgressDialog(DBHelperActivity.this);
        pDialog.setCancelable(false);
        recyclerView = (RecyclerView) findViewById(R.id.sqlList);
        editText = (EditText) findViewById(R.id.cntObjects);
        recyclerView.setLayoutManager(new SnappingLinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initAdapter();
//        recyclerView.setAdapter(adapter);
        Button btnAddObject = (Button) findViewById(R.id.btnAddObject);
        findViewById(R.id.btnScroll).setOnClickListener(v -> {
            String str = editText.getText().toString();
            if (!Utility.empty(str)) {
                recyclerView.scrollToPosition(Integer.parseInt(str));
            }
        });

        btnAddObject.setOnClickListener(v -> {
        });
        initList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            System.out.println(filePath);
        }
    }

    public void saveItems() {
        String cntStr = editText.getText().toString();
        if (!Utility.empty(cntStr)) {
            if (isDbLocked) return;
            count = Integer.parseInt(cntStr);
            stopwatch.restart();
            rxSave();
        } else {
            TestObject test = new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test");
            DBProvider.saveObject(DBHelperActivity.this, "test", test);
            adapter.add(test);
            objects.add(test);
            setTitle(objects.size() + " записей");
        }
    }

    @Override
    public void onFilterComplete(int count) {
    }


    public void rxSave() {
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
                        long row = DBProvider.saveObject(context, "test", testObject);
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
        System.out.println("btnAddObject setOnClickListener:" + stopwatch.getElapsedTimeMili() + "ms");
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
                DBProvider.deleteDB("test", "id = ?", new String[]{objects.get(position).getId()}, DBHelperActivity.this);
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

    @SuppressLint("StaticFieldLeak")
    protected void initList() {
        if (isDbLocked) return;
        stopwatch.restart();
        new LoadList().execute();
    }

    private class LoadList extends AsyncTask<Void, Void, ArrayList<TestObject>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DroidUtils.showProgress(pDialog, "Загрузка записей...");
        }

        @Override
        protected ArrayList<TestObject> doInBackground(Void... voids) {
            return getData();
        }

        @Override
        protected void onPostExecute(ArrayList<TestObject> testObjects) {
            super.onPostExecute(testObjects);
            DroidUtils.hideProgress(pDialog);
            setAdapterList(testObjects);
        }
    }

    private void rxIniList() {
        Observable.create(e -> {
            e.onNext(getData());
            e.onComplete();
        }).map(o -> (ArrayList<TestObject>) o)
                .doOnSubscribe(disposable -> runOnUiThread(() -> {
                    isDbLocked = true;
                    runOnUiThread(() -> DroidUtils.showProgress(pDialog, "Загрузка записей..."));
                    System.out.println("doOnSubscribe:" + stopwatch.getElapsedTimeMili() + "ms");
                }))
                .doFinally(() -> {
                    System.out.println("doFinally:" + stopwatch.getElapsedTimeMili() + "ms");
                    isDbLocked = false;
                    DroidUtils.hideProgress(pDialog);
                })
                .subscribeOn(Schedulers.io())//где обрабатывать(бг поток)
                .observeOn(AndroidSchedulers.mainThread())//куда возвращать(главный)
                .subscribe(this::setAdapterList);
        System.out.println("initList:" + stopwatch.getElapsedTimeMili() + "ms");
    }

    private void setAdapterList(ArrayList<TestObject> testObjects) {
        System.out.println("subscribe:" + stopwatch.getElapsedTimeMili() + "ms");
        adapter.clear();
        objects = new ArrayList<>(testObjects);
        adapter.addAll(testObjects);
        setTitle(objects.size() + " записей");
        recyclerView.setAdapter(adapter);
        System.out.println("subscribe end:" + stopwatch.getElapsedTimeMili() + "ms");
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.title_dbhelper));
        }
    }

    private ArrayList<TestObject> getData() {
        Cursor cursor = DBProvider.selectDB("test", null, null, null, this);
        ArrayList<TestObject> cursorObjectList = DBProvider.getCursorObjectList(cursor, TestObject.class);
        return cursorObjectList;
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
                DBProvider.deleteDB("test", null, this);
                initList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}