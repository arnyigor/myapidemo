package com.arny.myapidemo.net;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Map;
public interface Link {
	@FormUrlEncoded
	@GET("/api/v1.5/tr.json/translate")
	Call<Object> translate(@FieldMap Map<String, String> map);
}
