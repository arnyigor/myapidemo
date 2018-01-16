package com.arny.myapidemo.weather.datalayer;

import com.arny.arnylib.network.ApiFactory;
import com.arny.myapidemo.weather.api.ApiWeatherResponse;
import com.arny.myapidemo.weather.api.WeatherApi;
import com.arny.myapidemo.weather.api.WeatherApiService;
import io.reactivex.Observable;

public class WeatherData {

	private static final double HPA = 1.33322;
	private static final double MMRT = 0.7500637554192;

	public static Observable<ApiWeatherResponse> getApiWeather(double lat, double lon) {
		return ApiFactory
				.getInstance()
				.createService(WeatherApiService.class, WeatherApi.OPENWEATHER_BASE_URL)
				.getByCoord(WeatherApi.getQueryCoodrs(lat, lon));
	}

	public static double getMMRTST(double pressure) {
		return pressure * 0.7500637554192;
	}
}
