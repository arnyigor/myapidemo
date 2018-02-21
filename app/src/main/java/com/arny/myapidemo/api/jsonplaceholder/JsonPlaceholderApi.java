package com.arny.myapidemo.api.jsonplaceholder;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arny.arnylib.network.ApiRequest;
import com.arny.arnylib.network.ApiUtils;
import com.arny.myapidemo.ui.activities.NetworkActivity;
import com.google.gson.JsonArray;

import java.util.ArrayList;
public class JsonPlaceholderApi {
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    public static final String POSTS = "posts";
    public static final String COMMENTS = "comments";
    public static final String ALBUMS = "albums";
    public static final String PHOTOS = "photos";
    public static final String TODOS = "todos";
    public static final String USERS = "users";


    public interface OnGetPostsResponse {
        void onResultSuccess(ArrayList<Post> posts);

        void onError(String error);
    }

    public static void getApiPosts(Context context, final OnGetPostsResponse postsResponse) {
        ApiRequest.apiResponse(context, BASE_URL + POSTS, null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {
                JsonArray posts = ApiUtils.fromJson(response, JsonArray.class);
                ArrayList<Post> arrayList = ApiUtils.convertArray(posts, Post.class);
                postsResponse.onResultSuccess(arrayList);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postsResponse.onError(ApiUtils.getVolleyError(error));
                Log.e(NetworkActivity.class.getSimpleName(), "onErrorResponse: " + ApiUtils.getVolleyError(error));
            }
        });
    }
}
