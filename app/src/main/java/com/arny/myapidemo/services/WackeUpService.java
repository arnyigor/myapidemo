package com.arny.myapidemo.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.arny.myapidemo.R;
import com.arny.myapidemo.activities.VolleyTestActivity;
import com.arny.myapidemo.net.NetworkJSONLoader;
import com.arny.myapidemo.net.NetworkStringLoader;
import com.arny.myapidemo.utils.BaseUtils;

import static com.arny.myapidemo.models.Consts.TAG;

public class WackeUpService extends IntentService {
    public WackeUpService() {
        super("WakeUpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.i(WackeUpService.class.getSimpleName(), "onHandleIntent: extras = " + extras.toString());
        Log.i(WackeUpService.class.getSimpleName(), "onHandleIntent: extras test= " + extras.get("test"));
        Log.i(WackeUpService.class.getSimpleName(), "onHandleIntent: extras code= " + extras.get("code"));
        Log.i(WackeUpService.class.getSimpleName(), "onHandleIntent: intent time= " + BaseUtils.getDateTime(0,null));
        NetworkStringLoader loader = new NetworkStringLoader(getApplicationContext(),null);
        String lat = String.valueOf(BaseUtils.randInt(15, 35) + ".08400000000002");
        String lon = String.valueOf(BaseUtils.randInt(15, 180) + ".08400000000002");
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        String params = "http://dev.aristos.pw:5055?id=666666&lat=" + lat + "&lon="+lon+"&timestamp=" + timestamp;
        loader.requestString(params);
    }
}
