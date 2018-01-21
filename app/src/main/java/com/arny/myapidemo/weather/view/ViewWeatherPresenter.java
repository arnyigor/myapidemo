package com.arny.myapidemo.weather.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.utils.DateTimeUtils;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.weather.datalayer.WeatherData;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class ViewWeatherPresenter extends MvpPresenter<WeatherView> {
	private final CompositeDisposable disposable = new CompositeDisposable();
	private Location location;

	public void getGPSWeather(Context context) {
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ToastMaker.toastError(context, "Включите разрешение местоположения");
			return;
		}
		if (location == null) {
			getViewState().showLoadingProcess("Получение координат");
		}
		ToastMaker.toastSuccess(context, "Получаем координаты " + DateTimeUtils.getDateTime());
		disposable.add(Utility.mainThreadObservable(DroidUtils.getLocation(context, 10000,  0.0f, LocationAccuracy.HIGH))
				.subscribe(location -> {
					this.location = location;
					loadingWeather(location);
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

	public void loadingWeather(double latitude,double longitude) {
		disposable.add(Utility.mainThreadObservable(WeatherData.getApiWeather(latitude, longitude))
				.doOnSubscribe(disposable1 -> {
					getViewState().showLoadingProcess("Загрузка погоды");
				})
				.subscribe(response -> {
					Log.d(ViewWeatherPresenter.class.getSimpleName(), "loadingWeather: response:" + response);
					getViewState().hideLoadingProcess();
					getViewState().showWeather(response, latitude, longitude);
				}, throwable -> {
					throwable.printStackTrace();
					getViewState().hideLoadingProcess();
					getViewState().showError(throwable.getMessage());
				}));
	}

	public void loadingWeather(Location location) {
		disposable.add(Utility.mainThreadObservable(WeatherData.getApiWeather(location.getLatitude(), location.getLongitude()))
				 .subscribe(response -> {
					Log.d(ViewWeatherPresenter.class.getSimpleName(), "loadingWeather: response:" + response);
					getViewState().hideLoadingProcess();
					getViewState().showWeather(response,location.getLatitude(),location.getLongitude());
				}, throwable -> {
					throwable.printStackTrace();
					getViewState().hideLoadingProcess();
					getViewState().showError(throwable.getMessage());
				}));
	}
}
