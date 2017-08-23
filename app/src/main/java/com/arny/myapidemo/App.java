package com.arny.myapidemo;

import android.app.Application;
import com.androidnetworking.AndroidNetworking;
import com.arny.arnylib.database.DBProvider;
public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		AndroidNetworking.initialize(getApplicationContext());
		DBProvider.initDB(getApplicationContext(),"apidemo",1);
	}
}
