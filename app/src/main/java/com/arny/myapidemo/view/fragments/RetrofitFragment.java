package com.arny.myapidemo.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.arny.myapidemo.R;

public class RetrofitFragment extends Fragment {

    private Context context;
    private static final String TAG = "LOG_TAG";

    public RetrofitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String myValue = this.getArguments().getString("message");
        this.context = getActivity().getApplicationContext();
        String appName = context.getResources().getString(R.string.app_name);
        Toast.makeText(context, myValue +"  " +appName, Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        return view;
    }

}
