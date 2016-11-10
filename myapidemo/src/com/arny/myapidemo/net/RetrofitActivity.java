package com.arny.myapidemo.net;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

public class RetrofitActivity extends Activity {

	/*
	private static final String TAG = "LOG_TAG";
	EditText edtRetrofit;
	TextView tvRetrofit;
	Button btnRetrofit;
	private final String URL = "https://translate.yandex.ru";
	final String YANDEX_TRANSLATE_KEY = "trnsl.1.1.20161010T185457Z.b5008f659310a2ad.3c7089f72e98d0f4451239d43e41f8a5fd588f7b";
	private Gson gson = new GsonBuilder().create();
	private Retrofit retrofit = new Retrofit.Builder()
			//.addConverterFactory(GsonConverterFactory.create(gson))
			.baseUrl(URL)
			.build();

	private Link intf = retrofit.create(Link.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retrofit_activity);
		initUI();
	}

	private void netWork() {
		Map<String, String> mapJson = new HashMap<>();
		mapJson.put("key",YANDEX_TRANSLATE_KEY);
		mapJson.put("text",edtRetrofit.getText().toString());
		mapJson.put("lang","ru-en");
		Call<Object> call = intf.translate(mapJson);
		try {
			Response<Object> response = call.execute();
			Map<String, String> map = gson.fromJson(response.body().toString(),Map.class);
			for (Map.Entry e: map.entrySet()){
				Log.i(TAG, "netWork map.getKey = " + e.getKey() + " getValue = " + e.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			netWork();
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	private void initUI() {
		edtRetrofit = (EditText) findViewById(R.id.edtRetrofit);
		tvRetrofit = (TextView) findViewById(R.id.tvRetrofit);
		btnRetrofit = (Button) findViewById(R.id.btnRetrofit);
		btnRetrofit.addTextChangedListener(textWatcher);
	}  */
}