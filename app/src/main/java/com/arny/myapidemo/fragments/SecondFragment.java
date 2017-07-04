package com.arny.myapidemo.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.activities.FragmentsActivity;

public class SecondFragment extends Fragment implements FragmentCommunicator {

	private Context context;
	private ActivityCommunicator activityCommunicator;
	private TextView tvSecFrag;

	public SecondFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_second, container, false);
		initUI(rootview);
		return rootview;
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		context = getActivity();
		activityCommunicator =(ActivityCommunicator)context;//send to activity
		((FragmentsActivity)context).fragmentCommunicator = this;//recieve from activity
	}

	private void initUI(View rootview) {
		tvSecFrag = (TextView) rootview.findViewById(R.id.tvSecFrag);
		tvSecFrag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvSecFrag.setText("Second fragment");
				activityCommunicator.passDataToActivity("hello activity from Second fragment");
			}
		});
	}

	@Override
	public void passDataToFragment(String someValue) {
		Log.i(SecondFragment.class.getSimpleName(), "passDataToFragment: someValue = " + someValue);
		tvSecFrag.setText(someValue);
	}
}
