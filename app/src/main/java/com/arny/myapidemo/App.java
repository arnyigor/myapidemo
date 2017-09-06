package com.arny.myapidemo;

import android.app.Application;
import com.arny.arnylib.database.DBProvider;
public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		DBProvider.initDB(getApplicationContext(),"apidemo",1);
	}
}
