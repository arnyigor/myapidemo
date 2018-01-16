package com.arny.myapidemo.weather.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.arnylib.utils.MathUtils;
import com.arny.myapidemo.R;
import com.arny.myapidemo.weather.api.ApiWeatherResponse;
import com.arny.myapidemo.weather.api.WeatherApi;
import com.arny.myapidemo.weather.datalayer.Loc;
import com.arny.myapidemo.weather.datalayer.WeatherData;
import com.bumptech.glide.Glide;
import com.github.florent37.rxgps.RxGps;

public class WeatherViewActivity extends MvpAppCompatActivity implements WeatherView {
	@InjectPresenter
	ViewWeatherPresenter weatherPresenter;
	private TextView tvWeatherData;
	private TextView tvTemp;
	private ImageView imgView;
	private TextView tvWind;
	private TextView tvCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_view);
		initUI();
		weatherPresenter.getGPSWeather(Loc.getInstance(this));
	}

	private void initUI() {
		tvWeatherData = findViewById(R.id.tvWeatherData);
		tvTemp = findViewById(R.id.tvTemp);
		imgView = findViewById(R.id.imgView);
		tvWind = findViewById(R.id.tvWind);
		tvCity = findViewById(R.id.tvCity);
	}

	@Override
	public void showLoadingProcess() {
		tvWeatherData.setText("Loading weather...");
	}

	@Override
	public void showError(String error) {
		tvWeatherData.setText("error");
	}

	@Override
	public void hideLoadingProcess() {
		tvWeatherData.setText("");
	}

	@Override
	public void showWeather(ApiWeatherResponse response) {
		double temp = response.getMainWeatherData().getTemp();
		double pressure = response.getMainWeatherData().getPressure();
		double humidity = response.getMainWeatherData().getHumidity();
		tvTemp.setText(String.format("%s C°", String.valueOf(temp)));
		String pressData = MathUtils.round(pressure, 2) + " hPa\n" + MathUtils.round(WeatherData.getMMRTST(pressure), 2) + " мм.рт.ст.";
		tvWeatherData.setText(String.format("pressure:%s\nhumidity:%s%%", pressData, String.valueOf(humidity)));
		Glide.with(this)
				.load(WeatherApi.getWeatherIcon(response.getWeather().get(0).getIcon()))
				.into(imgView);
		double deg = response.getWind().getDeg();
		double speed = response.getWind().getSpeed();
		tvWind.setText(String.format("speed:%s deg:%s", speed, deg));
		tvCity.setText(response.getName());
	}
}
