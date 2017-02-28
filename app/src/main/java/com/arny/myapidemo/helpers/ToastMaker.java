
package com.arny.myapidemo.helpers;

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
     * @param error
     */
    public static void toast(Context context, String message, boolean error) {
        if (error) {
            Toasty.error(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Toasty.success(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
