package com.arny.myapidemo.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;
public interface AristorService {
	@FormUrlEncoded
    @POST("api/login")
    Observable<Auth> login(@FieldMap Map<String, String> names);
	@FormUrlEncoded
	@POST("api/check")
	Observable<Object> check(@FieldMap Map<String, String> names);

}

