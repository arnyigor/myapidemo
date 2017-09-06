package com.arny.myapidemo.net;


public class API {
    public static final String OPEN_WEATHER_APIID = "ab0d2ae4fb3a253bc8a063bbd00ed7a7";
    public static String OPENWEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	public static final String JSON_PLASEHOLDER_BASE_URL = "http://beta.json-generator.com/api/json/get/";

    private static String getOpenWeatherTown(String town) {
        return OPENWEATHER_BASE_URL + town + "&units=metric&APPID=" + OPEN_WEATHER_APIID;
    }
}
