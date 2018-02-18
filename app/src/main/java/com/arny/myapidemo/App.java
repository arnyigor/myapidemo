package com.arny.myapidemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.arny.arnylib.database.DBProvider;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
public class App extends Application {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		DBProvider.initDB(getApplicationContext(),"apidemo",5);
        BigImageViewer.initialize(GlideImageLoader.with(this));
    }
}
