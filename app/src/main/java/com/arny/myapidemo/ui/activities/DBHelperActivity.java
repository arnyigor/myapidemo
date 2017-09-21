package com.arny.myapidemo.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.arny.arnylib.adapters.SimpleBindableAdapter;
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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import android.widget.Filter;
import java.util.ArrayList;
import java.util.Arrays;

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqllist);
        initToolbar();
        stopwatch = new Stopwatch();
        stopwatch.start();
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

        });
        initList();
    }

    @Override
    public void onFilterComplete(int count) {
    }

    private class SaveList extends AsyncTask<Void, Integer, ArrayList<TestObject>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("SaveList onPreExecute " + stopwatch.getElapsedTimeMili() + "ms");
            DroidUtils.showProgress(pDialog, "Генерируем данные...");
        }

        @Override
        protected ArrayList<TestObject> doInBackground(Void... voids) {
            ArrayList<TestObject> list = new ArrayList<>();
            System.out.println("SaveList doInBackground " + stopwatch.getElapsedTimeMili() + "ms");
            for (int i = 0; i < count; i++) {
                TestObject test = new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test");
                DBProvider.saveObject(context, "test", test);
                list.add(test);
                onProgressUpdate(i);
            }
            System.out.println("SaveList doInBackground end" + stopwatch.getElapsedTimeMili() + "ms");
            return list;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            DroidUtils.showProgress(pDialog, "Генерируем данные..." + Arrays.toString(values));
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<TestObject> testObjects) {
            super.onPostExecute(testObjects);
            System.out.println("SaveList onPostExecute" + stopwatch.getElapsedTimeMili() + "ms");
            DroidUtils.hideProgress(pDialog);
            updateAdapter(testObjects);
        }
    }

    public void rxSave() {
        Observable.create(e -> {
            for (int i = 0; i < count; i++) {
                e.onNext(new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test"));
            }
            e.onComplete();
        }).map(o -> (TestObject) o)
                .doOnNext(testObject -> {
                    DBProvider.saveObject(context, "test", testObject);
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(testObjects -> runOnUiThread(() -> DroidUtils.showProgress(pDialog, "Генерируем данные...")))
                .doOnSubscribe(disposable -> {
                    isDbLocked = true;
                    runOnUiThread(() -> {
                        DroidUtils.showProgress(pDialog, "Генерируем данные...");
                    });
                    System.out.println("doOnSubscribe setOnClickListener:" + stopwatch.getElapsedTimeMili() + "ms");
                })
                .doFinally(() -> {
                    System.out.println("doFinally setOnClickListener:" + stopwatch.getElapsedTimeMili() + "ms");
                    isDbLocked = false;
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                        DroidUtils.hideProgress(pDialog);
                    });
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        System.out.println("btnAddObject setOnClickListener:" + stopwatch.getElapsedTimeMili() + "ms");
    }

    public void updateAdapter(ArrayList<TestObject> testObjects) {
        adapter.addAll(testObjects);
        objects.addAll(testObjects);
        setTitle(objects.size() + " записей");
    }

    @NonNull
    public static ArrayList<TestObject> getTestObjects(int cn, Context context) {
        ArrayList<TestObject> list = new ArrayList<>();
        for (int i = 0; i < cn; i++) {
            TestObject test = new TestObject(String.valueOf(MathUtils.randInt(0, 10)), "Test");
            DBProvider.saveObject(context, "test", test);
            list.add(test);
        }
        return list;
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