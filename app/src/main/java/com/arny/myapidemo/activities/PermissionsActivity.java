package com.arny.myapidemo.activities;

import android.Manifest;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.arny.myapidemo.R;

public class PermissionsActivity extends RuntimePermissionsActivity {

	private static final int REQUEST_PERMISSIONS = 20;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permissions);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PermissionsActivity.super.requestAppPermissions(new
								String[]{Manifest.permission.READ_CONTACTS,
								Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION}, R.string
								.runtime_permissions_txt
						, REQUEST_PERMISSIONS);
			}
		});
	}


	@Override
	public void onPermissionsGranted(final int requestCode) {
		Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
	}
}
