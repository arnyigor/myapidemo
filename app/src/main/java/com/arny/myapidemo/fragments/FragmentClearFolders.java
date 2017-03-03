package com.arny.myapidemo.fragments;
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

public class FragmentClearFolders extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_two, null);

		Button button = (Button) v.findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getActivity().getBaseContext(), "Helo from fagment two", Toast.LENGTH_SHORT).show();
			}
		});
		return v;
	}
}