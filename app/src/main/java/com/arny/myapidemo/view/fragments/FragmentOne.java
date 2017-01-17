package com.arny.myapidemo.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.arny.myapidemo.R;

public class FragmentOne extends Fragment {

	final String TAG = "LOG_TAG";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_one, null);

		Button button = (Button) v.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.i(TAG, "Button click in Fragment1");
				Toast.makeText(getActivity().getBaseContext(), "Helo from FragmentOne", Toast.LENGTH_SHORT).show();
			}
		});

		return v;
	}
}
