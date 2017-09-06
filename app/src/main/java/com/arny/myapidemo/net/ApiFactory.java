package com.arny.myapidemo.net;

import com.arny.myapidemo.api.retrofit.JsonPlaceholderService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiFactory {

	public static JsonPlaceholderService createService(){
		return new Retrofit.Builder()
				.baseUrl(API.JSON_PLASEHOLDER_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()
				.create(JsonPlaceholderService.class);
	}
}
