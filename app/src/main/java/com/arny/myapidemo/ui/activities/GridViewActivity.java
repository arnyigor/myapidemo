package com.arny.myapidemo.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import com.arny.myapidemo.R;

public class GridViewActivity extends Activity {
	GridView gridView;
	Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		gridView = (GridView) findViewById(R.id.gridView);

		ArrayAdapter<?> arrayAdapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, integers);
		gridView.setAdapter(arrayAdapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(GridViewActivity.this, String.valueOf(integers[position]), Toast.LENGTH_SHORT).show();
			}
		});
	}
}