package com.arny.myapidemo.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class NetworkStringLoader {
    Context context;
    TextView display;

    public NetworkStringLoader(Context con, TextView tv) {
        context = con;
        display = tv;
    }

    public void requestString(String url) {
        // Tag used to cancel the request
        Log.i(NetworkStringLoader.class.getSimpleName(), "requestString: ");

        StringRequest strReq = new StringRequest(Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i(NetworkStringLoader.class.getSimpleName(), "onResponse: response = " + response);
                        if (display != null) {
                            display.setText(response);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(NetworkStringLoader.class.getSimpleName(), "onErrorResponse: error = " + error);
                if (display != null) {
                    display.setText(error.toString());
                }
            }
        });

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(strReq);
    }

}