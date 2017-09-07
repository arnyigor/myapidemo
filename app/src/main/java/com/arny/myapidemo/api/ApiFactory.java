package com.arny.myapidemo.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;
public class ApiFactory {
	private static ApiFactory instance = new ApiFactory();
	public static ApiFactory getInstance() {
		return instance;

	}
	private ApiFactory(){}

	private Retrofit getRetrofit(String baseUrl) {
		Gson gson = new GsonBuilder()
				.setLenient()
				.create();
		OkHttpClient client = new OkHttpClient.Builder()
				.retryOnConnectionFailure(true)
				.connectTimeout(15, TimeUnit.SECONDS)
				.build();
		return new Retrofit.Builder()
				.baseUrl(baseUrl)
				.client(client)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();

	}


	public <S> S createService(Class<S> serviceClass,String baseUrl) {
		return getRetrofit(baseUrl).create(serviceClass);
	}


}
