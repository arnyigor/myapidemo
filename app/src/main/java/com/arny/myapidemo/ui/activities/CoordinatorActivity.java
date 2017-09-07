package com.arny.myapidemo.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.arny.myapidemo.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class CoordinatorActivity extends AppCompatActivity implements OnMapReadyCallback {
	private GoogleMap mGoogleMap;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_activity);
	    SupportMapFragment mMapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmap);
	    mMapView.getMapAsync(this);
    }

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mGoogleMap = googleMap;
		mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
	}
}