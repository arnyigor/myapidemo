package com.arny.myapidemo.weather.view;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.weather.datalayer.WeatherData;
@InjectViewState
public class ViewWeatherPresenter extends MvpPresenter<WeatherView> {

    public void getGPS() {

    }

    void loadingWeather(double lat, double lon) {
        getViewState().showLoadingProcess();
        Utility.mainThreadObservable(WeatherData.getApiWeather(lat, lon)).subscribe(response -> {
            getViewState().hideLoadingProcess();
            getViewState().showWeather(response);
        }, throwable -> {
            getViewState().hideLoadingProcess();
            getViewState().showError(throwable.getMessage());
        });
    }
}
