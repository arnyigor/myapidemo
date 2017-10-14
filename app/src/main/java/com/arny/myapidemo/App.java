package com.arny.myapidemo;

import android.app.Application;
import android.support.multidex.MultiDex;
import com.arny.arnylib.database.DBProvider;
public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		MultiDex.install(this);
		DBProvider.initDB(getApplicationContext(),"apidemo",3);
	}
}
