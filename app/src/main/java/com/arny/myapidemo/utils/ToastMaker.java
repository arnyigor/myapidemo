
package com.arny.myapidemo.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastMaker {

    public static void toast(Context context, String message) {
	    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

	public static void toastError(Context context, String message) {
		Toasty.error(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void toastInfo(Context context, String message) {
		Toasty.info(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void toastSuccess(Context context, String message) {
		Toasty.success(context, message, Toast.LENGTH_SHORT).show();
	}


}
