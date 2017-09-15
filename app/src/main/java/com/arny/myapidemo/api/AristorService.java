package com.arny.myapidemo.api;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface AristorService {
    @POST("api/login")
    Observable<Object> login(@Body String login,
                             @Body String pass,
                             @Body String context
    );

}

