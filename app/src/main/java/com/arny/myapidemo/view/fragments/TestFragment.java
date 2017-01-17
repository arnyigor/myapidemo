package com.arny.myapidemo.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.arny.myapidemo.R;

public class TestFragment extends Fragment {
    public static final String FRAGMENT_BUNDLE_NUMBER = "cat_number";

    public TestFragment() {
        // Для фрагмента требуется пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cat, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.frag_test);
        int i = getArguments().getInt(FRAGMENT_BUNDLE_NUMBER);
        String catNameTitle = getResources().getStringArray(R.array.cats_array_ru)[i];
        textView.setText(catNameTitle + " Привет из фрагмента");
        return rootView;
    }
}