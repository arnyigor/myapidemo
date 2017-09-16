package com.arny.myapidemo.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.network.ApiUtils;
import com.arny.arnylib.utils.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.api.API;
import com.arny.myapidemo.api.AristorService;
import com.arny.myapidemo.api.Auth;
import com.arny.myapidemo.api.umorili.UmoriliApi;
import com.arny.myapidemo.models.GoodItem;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {
	private EditText edt;
	private Stopwatch stopwatch;
	private Observable<ArrayList<GoodItem>> obs;
    private Button btn;
    private AristorService aristos;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java);
        btn = (Button) findViewById(R.id.btn_change);
        btn.setOnClickListener(this);
		edt = (EditText) findViewById(R.id.editText);
        aristos = ApiFactory.getInstance().createService(AristorService.class, API.BASE_URL_ARISTOS);
        getRxEdit()
                .map(s -> s.length() >= 3)
                .subscribe(res -> btn.setEnabled(res));
        rx2();
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

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_change:
                JSONObject ctx = new JSONObject();
                JSONObject params = new JSONObject();
                Utility.setJsonParam(ctx,"app","pw.aristos.packit.debug");
                Utility.setJsonParam(ctx,"version","1.4.1");
                Utility.setJsonParam(ctx,"code","141");
                Utility.setJsonParam(params,"context",ctx);
                Utility.setJsonParam(params,"login","test");
                Utility.setJsonParam(params,"pass","123456");
				HashMap<String, String> hashMap = new HashMap<>();
				hashMap.put("login", "test");
				hashMap.put("pass", edt.getText().toString());
				hashMap.put("context", ctx.toString());
				aristos.login(hashMap)
						.map(auth -> {
							System.out.println("auth:" + auth);
							return auth;
						})
						.doOnError(throwable -> runOnUiThread(() -> ToastMaker.toastError(RxJavaActivity.this,throwable.getMessage())))
						.flatMap(auth -> {
							if (auth.getSession() != null) {
								return aristos.check(getAuthSession(auth));
							}
							return observer -> {
							};
						})
						.map(o -> {
							System.out.println("o:" + o);
							return o;
						})
						.doOnError(throwable -> runOnUiThread(() -> ToastMaker.toastError(RxJavaActivity.this,throwable.getMessage())))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(System.out::println);
				break;
		}
	}

	@NonNull
	private HashMap<String, String> getAuthSession(Auth auth) {
		String session = auth.getSession();
		HashMap<String, String> map = new HashMap<>();
		map.put("session", session);
		return map;
	}

	private ArrayList<GoodItem> generateItems(int min, int max1) throws Exception {
		ArrayList<GoodItem> items = new ArrayList<>();
		int max = MathUtils.randInt(min, max1);
		for (int i = 0; i < max; i++) {
			if (i == 18) {
				throw new Exception("Errorrrrr!!!!!!!!!!!!!");
			}
			TimeUnit.SECONDS.sleep(1);
			items.add(new GoodItem(MathUtils.randDouble(0.5, 10.5), Generator.getString(MathUtils.randInt(3, 5)), String.valueOf(i)));
			System.out.println("generate item " + i + " from " + max + ":" + stopwatch.getElapsedTimeMili() + " ms");
		}
		return items;
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
            return s.substring(0,2) + ":" + s.substring(2,s.length());
		}
		return s;
	}

	private void rx2() {
		stopwatch = new Stopwatch();
		stopwatch.start();
		obs = Observable.create(e -> {
			ArrayList<GoodItem> list = generateItems(10, 20);
			e.onNext(list);
			e.onComplete();
		})
				.onErrorReturn(throwable -> new ArrayList<GoodItem>())//при ошибке
				.map(o -> (ArrayList<GoodItem>) o)//преобразуем
				.map(goodItems -> {
					for (GoodItem goodItem : goodItems) {
						goodItem.setImgUrl(Generator.getImageUrl(150, 150, Generator.GENERATOR_TYPE_IMG_PLACEIMG));
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
		System.out.println("rx2:" + stopwatch.getElapsedTimeMili() + " ms");
	}

	private void work(ArrayList<GoodItem> goodItems) {
		System.out.println("work:" + stopwatch.getElapsedTimeMili() + " ms");
		Log.i(RxJavaActivity.class.getSimpleName(), "work: goodItems = " + goodItems);
	}

}
