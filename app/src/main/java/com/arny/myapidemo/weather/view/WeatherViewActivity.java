package com.arny.myapidemo.weather.view;

import android.os.Bundle;
import android.widget.TextView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.myapidemo.R;
import com.arny.myapidemo.weather.api.ApiWeatherResponse;

public class WeatherViewActivity extends MvpAppCompatActivity implements WeatherView {
    @InjectPresenter
    ViewWeatherPresenter weatherPresenter;
    private TextView tvWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);
        initUI();
        double lat = 55.0;
        double lon = 36.0;
        weatherPresenter.loadingWeather(lat, lon);
    }

    private void initUI() {
        tvWeatherData = findViewById(R.id.tvWeatherData);
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
        tvWeatherData.setText(String.valueOf(response.getMainWeatherData().getTemp()));
    }
}
