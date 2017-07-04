package com.arny.myapidemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arny.myapidemo.R;

public class MainFragment extends Fragment {

	private Context context;

	public MainFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_main, container, false);
		this.context = container.getContext();
		getFragmentManager().beginTransaction()
				.replace(R.id.fragFirst, new FirstFragment())
				.commit();
		getFragmentManager().beginTransaction()
				.replace(R.id.fragContainer, new SecondFragment())
				.commit();
		return rootview;
	}

}
