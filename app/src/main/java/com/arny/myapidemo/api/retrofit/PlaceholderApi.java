package com.arny.myapidemo.api.retrofit;

import com.arny.myapidemo.api.jsonplaceholder.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;
public interface PlaceholderApi {
	@GET("posts")
	Call<List<Post>> getPosts();
	@GET("posts/{id}")
	Call<Post> getPost(@Path("id") int postId);
}