package com.arny.myapidemo.weather.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.arnylib.utils.BasePermissions;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.ui.activities.MapsActivity;
import com.arny.myapidemo.weather.api.ApiWeatherResponse;
import com.arny.myapidemo.weather.api.WeatherApi;
import com.arny.myapidemo.weather.datalayer.WeatherData;
import com.bumptech.glide.Glide;

public class WeatherViewActivity extends MvpAppCompatActivity implements WeatherView {
	public static final int REQUEST_CODE_GET_MAP_COORD = 505;
	@InjectPresenter
	ViewWeatherPresenter weatherPresenter;
	private TextView tvWeatherData;
	private TextView tvTemp;
	private ImageView imgView;
	private TextView tvWind;
	private TextView tvCity;
	private ProgressBar progressBar;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_view);
		initUI();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!BasePermissions.canAccessLocation(this, 100)) {
			ToastMaker.toastError(this, "Включите GPS и откройте интернет подключение");
		} else {
			weatherPresenter.getGPSWeather(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weather_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_get_coodr_from_map:
				Intent intent = new Intent(this, MapsActivity.class);
				startActivityForResult(intent, REQUEST_CODE_GET_MAP_COORD);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(WeatherViewActivity.class.getSimpleName(), "onActivityResult: requestCode:" + requestCode + " resultCode:" + resultCode + " data:" + DroidUtils.dumpIntent(data));
		switch (requestCode) {
			case REQUEST_CODE_GET_MAP_COORD:
				if (resultCode == RESULT_OK) {
					double latitude = data.getDoubleExtra("latitude", 0);
					double longitude = data.getDoubleExtra("longitude", 0);
					weatherPresenter.loadingWeather(latitude, longitude);
				}
				break;
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case 100:
				if (BasePermissions.permissionGranted(grantResults)) {
					weatherPresenter.getGPSWeather(this);
				} else {
					ToastMaker.toastError(this, "Включите GPS и откройте интернет подключение");
				}
				break;
		}
	}

	private void initUI() {
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			setTitle("Openweather");
		}
		tvWeatherData = findViewById(R.id.tvWeatherData);
		tvTemp = findViewById(R.id.tvTemp);
		imgView = findViewById(R.id.imgView);
		tvWind = findViewById(R.id.tvWind);
		tvCity = findViewById(R.id.tvCity);
		tvCity = findViewById(R.id.tvCity);
		progressBar = findViewById(R.id.progressBar);
	}

	@Override
	public void showLoadingProcess(String process) {
		setTitle(process);
		setUI(View.VISIBLE, View.GONE);
	}

	public void setUI(int visibleProgress, int visibleText) {
		progressBar.setVisibility(visibleProgress);
		tvWeatherData.setVisibility(visibleText);
		tvTemp.setVisibility(visibleText);
		imgView.setVisibility(visibleText);
		tvWind.setVisibility(visibleText);
		tvCity.setVisibility(visibleText);
	}

	@Override
	public void showError(String error) {
		setUI(View.GONE, View.VISIBLE);
		tvWeatherData.setText(error);
	}

	@Override
	public void hideLoadingProcess() {
		setUI(View.GONE, View.VISIBLE);
		tvWeatherData.setText("");
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void showWeather(ApiWeatherResponse response, double latitude, double longitude) {
		setTitle("Погода обновлена");
		setUI(View.GONE, View.VISIBLE);
		double temp = response.getMainWeatherData().getTemp();
		double pressure = response.getMainWeatherData().getPressure();
		double humidity = response.getMainWeatherData().getHumidity();
		tvTemp.setText(String.format("%s C°", String.valueOf(temp)));
		String pressData = MathUtils.round(pressure, 2) + " hPa\n" + MathUtils.round(WeatherData.getMMRTST(pressure), 2) + " мм.рт.ст.";
		tvWeatherData.setText(String.format("Давление:%s\nвладжность:%s%%", pressData, String.valueOf(humidity)));
		Glide.with(this)
				.load(WeatherApi.getWeatherIcon(response.getWeather().get(0).getIcon()))
				.into(imgView);
		double deg = response.getWind().getDeg();
		double speed = response.getWind().getSpeed();
		tvWind.setText(String.format("Ветер:%.2f %.2f м/с", deg, speed));
		tvCity.setText(String.format("%s\n%.6f %.6f", response.getName(), latitude, longitude));
	}
}
