package com.arny.myapidemo.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arny.myapidemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveDataFragment extends Fragment {

    public LiveDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_data, container, false);
    }

}
