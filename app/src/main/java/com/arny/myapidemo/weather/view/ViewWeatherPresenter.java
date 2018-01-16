package com.arny.myapidemo.weather.view;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.weather.datalayer.WeatherData;
import com.github.florent37.rxgps.RxGps;
import io.reactivex.disposables.CompositeDisposable;
@InjectViewState
public class ViewWeatherPresenter extends MvpPresenter<WeatherView> {
	private final CompositeDisposable disposable = new CompositeDisposable();

	public void getGPSWeather(RxGps gps) {
		disposable.add(gps.lastLocation()
				.subscribe(location -> {
					loadingWeather(location.getLatitude(), location.getLongitude());
				}, throwable -> {
					if (throwable instanceof RxGps.PermissionException) {
						Log.e(ViewWeatherPresenter.class.getSimpleName(), "getGPS:PermissionException " + throwable.getMessage());
					} else if (throwable instanceof RxGps.PlayServicesNotAvailableException) {
						Log.e(ViewWeatherPresenter.class.getSimpleName(), "getGPS:PlayServicesNotAvailableException " + throwable.getMessage());
					} else {
						Log.e(ViewWeatherPresenter.class.getSimpleName(), throwable.getMessage());
					}
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

	void loadingWeather(double lat, double lon) {
		getViewState().showLoadingProcess();
		Utility.mainThreadObservable(WeatherData.getApiWeather(lat, lon)).subscribe(response -> {
			getViewState().hideLoadingProcess();
			getViewState().showWeather(response);
		}, throwable -> {
			throwable.printStackTrace();
			getViewState().hideLoadingProcess();
			getViewState().showError(throwable.getMessage());
		});
	}
}
