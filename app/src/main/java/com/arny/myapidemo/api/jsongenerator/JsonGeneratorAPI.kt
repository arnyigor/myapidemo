package com.arny.myapidemo.api.jsongenerator

import com.arny.arnylib.network.ApiFactory
import io.reactivex.Observable

fun getJson(id:String): Observable<ArrayList<Place>>  {
    return ApiFactory
            .getInstance()
            .createService(JsonGeneratorService::class.java,"https://next.json-generator.com/api/json/get/")
            .json(id)
}