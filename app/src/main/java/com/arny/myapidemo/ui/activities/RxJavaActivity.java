package com.arny.myapidemo.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.Generator;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.Stopwatch;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.GoodItem;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {
	private EditText edt;
	private Stopwatch stopwatch;
	private Observable<ArrayList<GoodItem>> obs;
	private Observable<String> obsedit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java);
		findViewById(R.id.btn_change).setOnClickListener(this);
		edt = (EditText) findViewById(R.id.editText);
		rx2();
		rx3();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_change:
				obs.subscribe(this::work);
				break;
		}
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

	public static Observable<String> observeEdit(EditText editText) {
		final PublishSubject<String> selectSubject = PublishSubject.create();
		// for production code, unsubscribe, UI thread assertions are needed
		// see WidgetObservable from rxandroid for example
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				selectSubject.onNext(s.toString());
			}
		});
		return selectSubject;
	}

	private void rx3() {
		obsedit = observeEdit(edt);
		obsedit.debounce(1, TimeUnit.SECONDS)
				.map(this::getTimeString)
				.doOnNext(System.out::println)
				.onErrorReturn(throwable -> null);
	}

	private String getTimeString(String s) {
		if (s.length() >= 3) {
			return s.substring(0,2) + ":" + s.substring(2,s.length()-1);
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
