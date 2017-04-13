
package com.arny.myapidemo.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastMaker {

    /**
     * Displays a Toast notification for a short duration.
     *
     * @param context
     * @param message
     */
    public static void toast(Context context, String message) {
        Toasty.info(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a new Toast notification for a short duration.
     *
     * @param context
     * @param message
     * @param success
     */
    public static void toast(Context context, String message, boolean success) {
        if (success) {
            Toasty.success(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Toasty.error(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
