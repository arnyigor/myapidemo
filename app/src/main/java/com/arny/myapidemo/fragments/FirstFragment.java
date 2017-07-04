package com.arny.myapidemo.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.arny.myapidemo.R;
import com.arny.myapidemo.activities.FragmentsActivity;

public class FirstFragment extends Fragment implements FragmentCommunicator {

	private Context context;
	private Button btnFrstFrag;
	private ActivityCommunicator activityCommunicator;

	public FirstFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_first, container, false);
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
		btnFrstFrag = (Button) rootview.findViewById(R.id.btnFrstFrag);
		btnFrstFrag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnFrstFrag.setText("Hello from first");
				activityCommunicator.passDataToActivity("hello activity from first fragment");
			}
		});
	}

	public void go(String go){
		Log.i(FirstFragment.class.getSimpleName(), "go: go = " + go);
		btnFrstFrag.setText(go);
	}

	@Override
	public void passDataToFragment(String someValue) {
		Log.i(FirstFragment.class.getSimpleName(), "passDataToFragment: someValue = " + someValue);
		btnFrstFrag.setText(someValue);
	}
}
