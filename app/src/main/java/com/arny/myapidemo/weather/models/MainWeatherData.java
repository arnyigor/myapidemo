
package com.arny.myapidemo.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainWeatherData {

    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("pressure")
    @Expose
    private int pressure;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

}
