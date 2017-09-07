package com.arny.myapidemo.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.arny.myapidemo.R;

public class MultiListActivity extends Activity {
	ListView listView;
	String[] strings;
	String checked;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multilistview);
		strings = getResources().getStringArray(R.array.listviewarray);
		listView = (ListView) findViewById(R.id.listView);
		ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strings);
		listView.setAdapter(stringArrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SparseBooleanArray chosen = listView.getCheckedItemPositions();
				checked = "";
				for (int i = 0; i < chosen.size(); i++) {
					if (chosen.valueAt(i)) {
						checked +=strings[(int)listView.getItemIdAtPosition(chosen.keyAt(i))] + ", ";
					}
				}
				if (!checked.equals(""))Toast.makeText(getApplicationContext(), checked, Toast.LENGTH_LONG).show();
			}
		});
	}
}