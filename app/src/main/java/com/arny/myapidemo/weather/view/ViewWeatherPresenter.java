package com.arny.myapidemo.weather.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.weather.datalayer.WeatherData;
import com.google.android.gms.location.LocationRequest;
import io.reactivex.disposables.CompositeDisposable;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

@InjectViewState
public class ViewWeatherPresenter extends MvpPresenter<WeatherView> {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ReactiveLocationProvider locationProvider;
    private LocationRequest request;

    public void getGPSWeather(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastMaker.toastError(context, "Включите разрешение местоположения");
            return;
        }
        getViewState().showLoadingProcess("Получение координат");
        if (locationProvider == null) {
            locationProvider = new ReactiveLocationProvider(context);
        }
        if (request == null) {
            request = LocationRequest.create() //standard GMS LocationRequest
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setNumUpdates(5)
                    .setInterval(100);
        }
        disposable.add(Utility.mainThreadObservable(locationProvider.getUpdatedLocation(request))
                .subscribe(location -> {
                    Log.d(ViewWeatherPresenter.class.getSimpleName(), "getGPSWeather: " + location);
                    loadingWeather(location.getLatitude(), location.getLongitude());
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().showError(throwable.getMessage());
                }));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		disposable.clear();
	}

    public void loadingWeather(double lat, double lon) {
        disposable.add(Utility.mainThreadObservable(WeatherData.getApiWeather(lat, lon))
                .doOnSubscribe(disposable1 -> {
                    getViewState().showLoadingProcess("Загрузка погоды");
                })
                .subscribe(response -> {
                    Log.d(ViewWeatherPresenter.class.getSimpleName(), "loadingWeather: response:" + response);
                    getViewState().hideLoadingProcess();
                    getViewState().showWeather(response);
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideLoadingProcess();
                    getViewState().showError(throwable.getMessage());
                }));
    }
}
