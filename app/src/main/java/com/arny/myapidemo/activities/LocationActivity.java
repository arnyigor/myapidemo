package com.arny.myapidemo.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.utils.BasePermissions;
import com.arny.myapidemo.utils.BaseUtils;
import com.arny.myapidemo.utils.ToastMaker;

public class LocationActivity extends AppCompatActivity {

    private LocationManager mLocMgr;
    private TextView myLocationText;
    private long locationTime;


    private static final int LOCATION_REQUEST = 100;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        initToolbar();
        myLocationText = (TextView) findViewById(R.id.myLocationText);
        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle(getString(R.string.title_location));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (BasePermissions.permissionGranted(grantResults)) {
                    getLocation();
                } else {
                    ToastMaker.toast(this, "Включите GPS и откройте интернет подключение", true);
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!BasePermissions.canAccessLocation(this, LOCATION_REQUEST)) {
            ToastMaker.toast(this, "Включите GPS и откройте интернет подключение", true);
        } else {
            getLocation();
        }
    }

    @Override
    protected void onDestroy() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocMgr.removeUpdates(locationListener);
        super.onDestroy();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastMaker.toast(this, "Включите разрешение местоположения", true);
            return;
        }
        mLocMgr.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 300, 0, locationListener);
    }


    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            long updateTeme = 0;
            if (locationTime==0){
                locationTime = location.getTime();
            }else{
                updateTeme = location.getTime() - locationTime;
                locationTime = location.getTime();
            }
            myLocationText.setText(String.format("Провайдер:%s\nВремя: %s\n Время обновления:%d\nLat = %.6f Long = %.6f",
                    location.getProvider(), BaseUtils.getDateTime(location.getTime(),null),updateTeme,location.getLatitude(),location.getLongitude()));
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
}