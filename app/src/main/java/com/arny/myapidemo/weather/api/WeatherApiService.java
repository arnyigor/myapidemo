package com.arny.myapidemo.weather.api;

import com.arny.myapidemo.api.AristosTransferResponse;
import com.arny.myapidemo.api.Auth;
import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;
public interface WeatherApiService {
    @GET("data/2.5/weather")
    Observable<ApiWeatherResponse> getByCoord(@QueryMap Map<String, String> options);

}

