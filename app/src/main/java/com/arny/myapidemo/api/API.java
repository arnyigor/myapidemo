package com.arny.myapidemo.api;

import com.arny.myapidemo.api.retrofit.PlaceholderApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class API {
    public static final String OPEN_WEATHER_APIID = "ab0d2ae4fb3a253bc8a063bbd00ed7a7";
    public static String OPENWEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	public static final String JSON_PLASEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com/";
	public static final String JSON_GENERATOR_BASE_URL = "http://beta.json-generator.com/api/json/";
	public static final String BASE_URL_UMORILI = "http://umorili.herokuapp.com/";

	public static String getOpenWeatherTown(String town) {
        return OPENWEATHER_BASE_URL + town + "&units=metric&APPID=" + OPEN_WEATHER_APIID;
    }


	public static JsonPlaceholderService createService(){
		return new Retrofit.Builder()
				.baseUrl(API.JSON_PLASEHOLDER_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()
				.create(JsonPlaceholderService.class);
	}

	public static PlaceholderApi getPlaceholderApi(){
		return new Retrofit.Builder()
				.baseUrl(API.JSON_PLASEHOLDER_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(PlaceholderApi.class);
	}
}
