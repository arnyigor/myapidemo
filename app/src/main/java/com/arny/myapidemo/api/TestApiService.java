package com.arny.myapidemo.api;

import com.arny.myapidemo.models.GoodItem;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.ArrayList;
public interface TestApiService {
	@GET("4JzCgxruX")
	Observable<ArrayList<GoodItem>> getGoodItems();

}

