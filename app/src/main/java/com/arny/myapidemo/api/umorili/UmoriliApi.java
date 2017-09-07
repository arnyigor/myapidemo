package com.arny.myapidemo.api.umorili;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
public interface UmoriliApi {
	@GET("api/get")
	Call<List<PostModel>> getPosts(
			@Query("num") int num);
}