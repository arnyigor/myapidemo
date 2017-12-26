package com.arny.myapidemo.api.jsongenerator;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.ArrayList;
public interface JsonGeneratorService {
    @GET("{id}")
    Observable<ArrayList<Place>> json(@Path("id") String id);
}