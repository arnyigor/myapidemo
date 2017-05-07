package com.arny.myapidemo.net;


public class API {
    public static final String OPEN_WEATHER_APIID = "ab0d2ae4fb3a253bc8a063bbd00ed7a7";
    public static String JSON_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    private static String getOpenWeatherTown(String town) {
        return JSON_BASE_URL + town + "&units=metric&APPID=" + OPEN_WEATHER_APIID;
    }
}
