package com.arny.myapidemo.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.utils.*;
import com.arny.arnylib.utils.generators.Generator;
import com.arny.myapidemo.R;
import com.arny.myapidemo.api.API;
import com.arny.myapidemo.api.AristorService;
import com.arny.myapidemo.api.Auth;
import com.arny.myapidemo.models.GoodItem;
import com.arny.myapidemo.utils.Local;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt;
    private Stopwatch stopwatch;
    private Observable<ArrayList<GoodItem>> obs = rx2();
    private Button btn;
    private AristorService aristos;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        btn = (Button) findViewById(R.id.btn_login);
        btn.setOnClickListener(this);
        edt = (EditText) findViewById(R.id.editText);
        findViewById(R.id.btnTest).setOnClickListener(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
	    findViewById(R.id.btnGetTransfers).setOnClickListener(this);
        aristos = ApiFactory.getInstance().createService(AristorService.class, API.BASE_URL_ARISTOS);
        getRxEdit()
                .map(s -> s.length() >= 3)
                .subscribe(res -> btn.setEnabled(res));
    }

    private Observable<String> getRxEdit() {
        return RxTextView.textChanges(edt)
                .map(CharSequence::toString).skip(1)
                .doOnNext(s -> {
                    boolean shortPass = s.length() < 3;
                    btn.setEnabled(!shortPass);
                    if (shortPass) {
                        runOnUiThread(() -> {
                            btn.setEnabled(false);
                            edt.setError("Короткий пароль");
                        });
                    }
                })
                .debounce(1, TimeUnit.SECONDS)
                .doOnNext(System.out::println)
                .onErrorReturn(throwable -> null)
                .subscribeOn(Schedulers.io())//где обрабатывать(бг поток)
                .observeOn(AndroidSchedulers.mainThread());//куда возвращать(главный)
    }

    private List<Integer> generateList() {
        List<Integer> integers = new ArrayList<>();
        int rand = MathUtils.randInt(50, 200);
        System.out.println("Generating integers\nrand:" + rand);
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return integers;
            }
            System.out.println(Thread.currentThread().getName() + " thread " + " iteration:" + i + " " + stopwatch.getElapsedTimeSecs(3) + " sec");
            if (rand == i) {
                try {
                    throw new Exception("rand is found:" + i);
                } catch (Exception e) {
                    e.printStackTrace();
                    return integers;
                }
            }
            integers.add(i);
        }
        return integers;
    }

    private boolean savetest(List<Integer> integers){
        for (Integer integer : integers) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "thread save integer:" + integer + " time:" + stopwatch.getElapsedTimeSecs(3) + " sec");
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTest:
                stopwatch = new Stopwatch();
                stopwatch.start();
                Utility.mainThreadObservable(
                        Observable.create(e -> {
                            e.onNext(generateList());
                            e.onComplete();
                        }).map(o -> (ArrayList<Integer>)o)
                                .map(integers -> {
                                    ArrayList<Long> objects = new ArrayList<>(integers.size());
                                    for (Integer integer : integers) {
                                        objects.add(Long.valueOf(integer));
                                    }
                                    return objects;
                                })
                                .map(unsortedList -> {//сортировка списка
                                    List<Long> sortedList = new ArrayList<>(unsortedList.size());
                                    Collections.copy(sortedList, unsortedList);
                                    Collections.sort(sortedList, (o1, o2) -> Long.compare(o1, o2));
                                    return sortedList;
                                })
                        .map(longs -> {
                            ArrayList<Integer> objects = new ArrayList<>(longs.size());
                            for (Long object : longs) {
                                objects.add(Integer.parseInt(String.valueOf(object)));
                            }
                            return objects;
                        })
                ).subscribe(integers -> {
                    Utility.mainThreadObservable(
                            Observable.create(e -> {
                                e.onNext(savetest(integers));
                                e.onComplete();
                            }).map(o ->(Boolean) o)
                    ).subscribe(System.out::println, Throwable::printStackTrace);
                    System.out.println("res "+integers+" time:" + stopwatch.getElapsedTimeSecs(3) + " sec");
                }, Throwable::printStackTrace);
                break;
            case R.id.btnGetTransfers:
                API.getTransfer()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object -> {
			                Log.i(RxJavaActivity.class.getSimpleName(), "onClick: object.getSuccess() = " + object.getSuccess());
			                System.out.println(object);
		                }, Throwable::printStackTrace);
		        break;
	        case R.id.btn_login:
                aristos.login(getPostHashMap())
                        .doOnSubscribe(disposable -> runOnUiThread(() -> DroidUtils.showProgress(pDialog, "Вход...")))
                        .doFinally(() -> runOnUiThread(() -> DroidUtils.hideProgress(pDialog)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(auth -> {
	                        System.out.println(auth);
	                        if (auth.getSuccess()) {
		                        if (auth.getSession() != null) {
			                        API.setSession(auth.getSession());
		                        }
	                        }else{
		                        ToastMaker.toastError(RxJavaActivity.this,auth.getError());
	                        }
                        },throwable -> {
	                        ToastMaker.toastError(RxJavaActivity.this, throwable.getMessage());
                        });
                break;
        }
    }

    @NonNull
    private HashMap<String, Object> getPostHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
	    JSONObject ctx = API.getAristosPostCtx();
        hashMap.put("login", "test");
        hashMap.put("pass", edt.getText().toString());
        hashMap.put("context", ctx);
        return hashMap;
    }

	@NonNull
    private HashMap<String, String> getAuthSession(Auth auth) {
        String session = auth.getSession();
        HashMap<String, String> map = new HashMap<>();
        map.put("session1", session);
        return map;
    }

    private void rx1() {
        Observable.just(1, 2, 4, 8, 16, 32, 64)
                .filter(integer -> integer > 11)
                .map(String::valueOf)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> Log.i(RxJavaActivity.class.getSimpleName(), integer));
    }

    private String getTimeString(String s) {
        if (s.length() >= 3) {
            return s.substring(0, 2) + ":" + s.substring(2, s.length());
        }
        return s;
    }

    private Observable<ArrayList<GoodItem>> rx2() {
        stopwatch = new Stopwatch();
        stopwatch.start();
        return Observable.create(e -> {
            ArrayList<GoodItem> list = Local.generateItems(10, 20, 0);
            e.onNext(list);
            e.onComplete();
        })
                .onErrorReturn(throwable -> new ArrayList<GoodItem>())//при ошибке
                .map(o -> (ArrayList<GoodItem>) o)//преобразуем
                .map(goodItems -> {
                    for (GoodItem goodItem : goodItems) {
                        goodItem.setImage(Generator.getImageUrl(150, 150, Generator.GENERATOR_TYPE_IMG_PLACEIMG));
                    }
                    return goodItems;
                })//преобразуем
                .doOnSubscribe(disposable -> {
                    System.out.println("doOnSubscribe:" + stopwatch.getElapsedTimeMili() + " ms");
                })//действие при подписке
                .subscribeOn(Schedulers.io())//где обрабатывать(бг поток)
                .observeOn(AndroidSchedulers.mainThread())//куда возвращать(главный)
                .doFinally(() -> {
                    System.out.println("doFinally:" + stopwatch.getElapsedTimeMili() + " ms");
                    stopwatch.stop();
                });//делаем при финале
    }

    private void work(ArrayList<GoodItem> goodItems) {
        System.out.println("work:" + stopwatch.getElapsedTimeMili() + " ms");
        Log.i(RxJavaActivity.class.getSimpleName(), "work: goodItems = " + goodItems);
    }

}
