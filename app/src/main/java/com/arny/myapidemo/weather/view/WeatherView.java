package com.arny.myapidemo.weather.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.arny.myapidemo.weather.api.ApiWeatherResponse;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface WeatherView extends MvpView {
	void showLoadingProcess(String process);

	void showError(String error);

	void hideLoadingProcess();

	void showWeather(ApiWeatherResponse response);

}
