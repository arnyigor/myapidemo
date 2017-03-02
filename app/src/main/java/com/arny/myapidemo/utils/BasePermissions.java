package com.arny.myapidemo.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


public class BasePermissions {

    private static final String PERM_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERM_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String PERM_READ_EXTERN = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String PERM_WRITE_EXTERN = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


    private static final String[] STORAGE_PERMS = {
            PERM_READ_EXTERN,
            PERM_WRITE_EXTERN
    };

    private static final String[] LOCATION_PERMS={
            PERM_FINE_LOCATION,
            PERM_COARSE_LOCATION
    };

    public static boolean canAccessLocation(Activity activity,int requestCode) {
        return(hasPermission(activity, LOCATION_PERMS, PERM_FINE_LOCATION,requestCode));
    }

    public static boolean canAccessStorage(Activity activity,int requestCode) {
        return(hasPermission(activity, STORAGE_PERMS, PERM_WRITE_EXTERN,requestCode));
    }

    public static boolean permissionGranted(int[] grantResults){
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean hasPermission(Activity activity, String[] permissions,String permision,int requestCode) {
        int permission = ActivityCompat.checkSelfPermission(activity, permision);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    requestCode
            );
        }
        return permission == PackageManager.PERMISSION_GRANTED;


    }


}
