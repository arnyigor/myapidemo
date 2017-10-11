package com.arny.myapidemo.api;

import android.support.annotation.NonNull;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.api.retrofit.PlaceholderApi;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.json.JSONObject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
public class API {
    public static final String OPEN_WEATHER_APIID = "ab0d2ae4fb3a253bc8a063bbd00ed7a7";
    public static String OPENWEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	public static final String JSON_PLASEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com/";
	public static final String JSON_GENERATOR_BASE_URL = "http://beta.json-generator.com/api/json/";
	public static final String BASE_URL_UMORILI = "http://umorili.herokuapp.com/";
	public static final String BASE_URL_ARISTOS = "https://hub.tesla.aristos.pw/";

	private static String session;

	public static String getOpenWeatherTown(String town) {
        return OPENWEATHER_BASE_URL + town + "&units=metric&APPID=" + OPEN_WEATHER_APIID;
    }

    public static Observable<AristosTransferResponse> getTransfer(){
	    HashMap<String, Object> hashMap = new HashMap<>();
	    JSONObject ctx = getAristosPostCtx();
	    String session = getSession();
	    hashMap.put("session", session);
	    hashMap.put("context", ctx);
	    if (session != null) {
		    return ApiFactory
				    .getInstance()
				    .createService(AristorService.class, API.BASE_URL_ARISTOS)
				    .getTransfer(hashMap);
	    }
	    return Observable.create(Emitter::onComplete);
    }

	@NonNull
	public static JSONObject getAristosPostCtx() {
		JSONObject ctx = new JSONObject();
		Utility.setJsonParam(ctx, "app", "pw.aristos.packit.debug");
		Utility.setJsonParam(ctx, "version", "1.4.1");
		Utility.setJsonParam(ctx, "code", "141");
		return ctx;
	}

	public static String getSession() {
		return session;
	}

	public static void setSession(String session) {
		API.session = session;
	}
}
