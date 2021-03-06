package com.arny.myapidemo.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.afollestad.materialdialogs.MaterialDialog;
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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
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
	private Button btnTest1;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MaterialDialog progress;
    private Button btnTest3;
    private Button btnRetry;
    private int retryCount;
    private int maxRetries;
    private int stopRetries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java);
        initUI();
        aristos = ApiFactory.getInstance().createService(AristorService.class, API.BASE_URL_ARISTOS);
        getRxEdit().map(s -> s.length() >= 3)
                .subscribe(res -> btn.setEnabled(res));
        progress = new MaterialDialog.Builder(this)
                .content("Загрузка...")
                .progress(true, 0)
                .negativeText("Отмена")
                .onNegative((dialog, which) -> disposeRX())
                .build();
    }

    private void initUI() {
        btn = findViewById(R.id.btn_login);
        btnTest1 = findViewById(R.id.btnTest1);
        btnTest3 = findViewById(R.id.btnTest3);
        btnTest3.setOnClickListener(this);
        btn.setOnClickListener(this);
        btnTest1.setOnClickListener(this);
        edt = findViewById(R.id.editText);
        findViewById(R.id.btnTest).setOnClickListener(this);
        findViewById(R.id.btnTest2).setOnClickListener(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        findViewById(R.id.btnGetTransfers).setOnClickListener(this);
        btnRetry = findViewById(R.id.buttonRetry);
        btnRetry.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposeRX();
    }

    private void disposeRX() {
        compositeDisposable.clear();
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
			System.out.println(Utility.getThread() + " thread " + " iteration:" + i + " " + stopwatch.getElapsedTimeSecs(3) + " sec");
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

	private boolean savetest(List<Integer> integers) {
		for (Integer integer : integers) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Utility.getThread() + "thread save integer:" + integer + " time:" + stopwatch.getElapsedTimeSecs(3) + " sec");
		}
		return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnTest:
				stopwatch = new Stopwatch();
				stopwatch.start();
                Utility.mainThreadObservable(Observable.fromCallable(this::generateList)
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
                                }).flatMap(integers -> Observable.fromCallable(() -> savetest(integers)))
                ).subscribe(aBoolean -> {
                    System.out.println("res " + aBoolean + " time:" + stopwatch.getElapsedTimeSecs(3) + " sec");
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
            case R.id.buttonRetry:
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                btnRetry.setEnabled(false);
                retryCount = 0;
                maxRetries = 5;
                stopRetries = 15;
                Utility.mainThreadObservable(Observable.fromCallable(() -> TestingUtils.londTimeConnection(1000, true)))
                        .doOnError(throwable -> {
                            Log.e(RxJavaActivity.class.getSimpleName(), "londTimeConnection error: " + throwable.getMessage() + " попытка:" + retryCount);
                        }).retryWhen(throwableObservable -> throwableObservable.flatMap((Function<Throwable, Observable<?>>) throwable -> {
                    long elapsedTimeMili = stopwatch.getElapsedTimeMili();
                    Log.d(RxJavaActivity.class.getSimpleName(), "retryWhen: retryCount:" + retryCount + " maxRetries:" + maxRetries + " time:" + elapsedTimeMili);
                    stopwatch.restart();
                    if (elapsedTimeMili < 5000) maxRetries += 5;
                    if (maxRetries >= stopRetries) {
                        maxRetries = stopRetries;
                    }
                    if (++retryCount < maxRetries) {
                        return Observable.timer(1, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(throwable);
                })).subscribe(
                        aBoolean -> {
                            stopwatch.stop();
                            btnRetry.setEnabled(true);
                            ToastMaker.toastSuccess(this, "Connection success");
                        }, throwable -> {
                            stopwatch.stop();
                            btnRetry.setEnabled(true);
                            throwable.printStackTrace();
                            ToastMaker.toastError(this, throwable.getMessage());
                        }
                );
                break;
            case R.id.btn_login:
                Utility.mainThreadObservable(aristos.login(getPostHashMap()))
                        .doOnSubscribe(disposable -> runOnUiThread(() -> DroidUtils.showProgress(pDialog, "Вход...")))
                        .doFinally(() -> DroidUtils.hideProgress(pDialog))
						.subscribe(auth -> {
							System.out.println(auth);
							if (auth.getSuccess()) {
								if (auth.getSession() != null) {
									API.setSession(auth.getSession());
								}
							} else {
								ToastMaker.toastError(RxJavaActivity.this, auth.getError());
							}
						}, throwable -> {
							ToastMaker.toastError(RxJavaActivity.this, throwable.getMessage());
						});
				break;
			case R.id.btnTest1:
                Stopwatch stopwatch1 = new Stopwatch();
                stopwatch1.start();
                Observable<String> stringObservable = Observable.fromCallable(this::londTimeFunctionString);
                Observable<List<Integer>> listObservable = Observable.fromCallable(this::londTimeFunctionList);
                compositeDisposable.add(Utility.mainThreadObservable(Schedulers.computation(), listObservable.flatMap(Observable::fromIterable).filter(integer -> integer > 3 && integer < 8).toList().flatMapObservable(Observable::fromIterable))
                        .doOnSubscribe(disposable -> progress.show())
                        .subscribe(
                                res -> {
                                    ToastMaker.toastSuccess(this, "RX result: " + res + " with time :" + stopwatch1.getElapsedTimeSecs(3) + " sec" + " in thread:" + Utility.getThread());
                                    Log.i(RxJavaActivity.class.getSimpleName(), "RX result: " + res + " with time :" + stopwatch1.getElapsedTimeSecs(3) + " sec" + " in thread:" + Utility.getThread());
                                    stopwatch1.stop();
                                    progress.dismiss();
                                }, throwable -> {
                                    ToastMaker.toastError(this, "RX error: " + throwable.getMessage());
                                    throwable.printStackTrace();
                                    Log.i(RxJavaActivity.class.getSimpleName(), "RX error:" + throwable.getMessage() + " : in thread:" + Utility.getThread());
                                    stopwatch1.stop();
                                    progress.dismiss();
                                }
                        ));
                break;

            case R.id.btnTest2:
//                Stopwatch stopwatch2 = new Stopwatch();
//                stopwatch2.start();
//                compositeDisposable.add(Utility.mainThreadObservable(JsonGeneratorAPIKt.getJson("4JzCgxruX")
//                        .doOnNext(places -> {
//                            RoomDB.getDb(this).getPlacesDao().insert(places);
//                            runOnUiThread(() -> progress.setContent("Обновление БД..."));
//                        })
//                ).doOnSubscribe(disposable -> progress.show())
//                        .subscribe(places -> {
//                            Log.i(RxJavaActivity.class.getSimpleName(), "getJson: " + places);
//                            ToastMaker.toastSuccess(this, "Geting json success!");
//                            progress.dismiss();
//                        }, throwable -> {
//                            throwable.printStackTrace();
//                            progress.dismiss();
//                            ToastMaker.toastError(this, "Geting json error:" + throwable.getMessage());
//                        }));
                String res1 = londTimeFunctionString();
                Log.i(RxJavaActivity.class.getSimpleName(), "res1: " + res1);
                break;
            case R.id.btnTest3:
               Utility.mainThreadObservable(Observable.fromCallable(this::londTimeFunctionString))
                        .subscribe(res -> {
                            Log.i(RxJavaActivity.class.getSimpleName(), "res: " + res);
                        });
                break;
        }
	}

    private String londTimeFunctionString() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionString: started:");
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionString update in thread " + Utility.getThread() + ": time:" + stopwatch.getElapsedTimeSecs(3) + " sec");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionString: finished:" + stopwatch.getElapsedTimeSecs(3) + " sec");
        stopwatch.stop();
        return "long_time_function_result";
    }

    private int londTimeFunctionInt() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionInt: started:");
        int res = 0;
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionInt update in thread " + Utility.getThread() + ": time:" + stopwatch.getElapsedTimeSecs(3) + " sec");
                res += MathUtils.randInt(0,9);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionInt: finished:" + stopwatch.getElapsedTimeSecs(3) + " sec");
        stopwatch.stop();
        return res;
    }

    private List<Integer> londTimeFunctionList() throws Exception {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionList: started:");
        int res = 0;
        List<Integer> integers = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                int randInt = MathUtils.randInt(0, 9);
                int i1 = MathUtils.randInt(0, 1);
                if (randInt == i1) {
                    throw new Exception(" Danger!!! number is equals " + i1);
                }
                Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionList "+randInt+" update in thread " + Utility.getThread() + ": time:" + stopwatch.getElapsedTimeSecs(3) + " sec");
                integers.add(randInt);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(RxJavaActivity.class.getSimpleName(), "londTimeFunctionList: finished:" + stopwatch.getElapsedTimeSecs(3) + " sec");
        stopwatch.stop();
        return integers;
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
