package com.arny.myapidemo.weather.api;

import java.util.HashMap;
public class WeatherApi {
	private static final String OPEN_WEATHER_APIID = "ab0d2ae4fb3a253bc8a063bbd00ed7a7";
	public static String OPENWEATHER_BASE_URL = "http://api.openweathermap.org/";
	public static String OPENWEATHER_ICON = "http://openweathermap.org/img/w/";

	public static String getWeatherIcon(String iconId) {
		return OPENWEATHER_ICON + iconId + ".png";
	}

	public static HashMap<String, String> getQueryCoodrs(double latitude, double longitude) {
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("APPID", OPEN_WEATHER_APIID);
		hashMap.put("units", "metric");
		hashMap.put("lat", String.valueOf(latitude));
		hashMap.put("lon", String.valueOf(longitude));
		return hashMap;
	}

	public static HashMap getQueryTown(String town) {
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("APPID", OPEN_WEATHER_APIID);
		hashMap.put("units", "metric");
		hashMap.put("q", town);
		return hashMap;
	}
}
