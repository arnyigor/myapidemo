package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.GoodItem;
import com.arny.myapidemo.net.ApiFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {

	private ArrayList<GoodItem> goodItems = new ArrayList<>();
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
		findViewById(R.id.btn_change).setOnClickListener(this);
	}

	private void showProgress(){
        progressBar2.setVisibility(View.VISIBLE);
    }

    private void  hideProgress(){
	    progressBar2.setVisibility(View.GONE);
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_change:
				ApiFactory.createService()
						.getGoodItems()
						.map(String::valueOf)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(goodItem -> Log.i(RxJavaActivity.class.getSimpleName(), goodItem ));
				break;
		}
	}

	private void justExample() {
		Observable.just(1, 2, 4,8,16,32,64)
				.filter(integer -> integer>11)
				.map(String::valueOf)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(integer-> Log.i(RxJavaActivity.class.getSimpleName(), integer));
	}

}
