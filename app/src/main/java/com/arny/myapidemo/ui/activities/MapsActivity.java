package com.arny.myapidemo.ui.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.arny.myapidemo.R;
import com.arny.myapidemo.ui.fragments.OsmandMapFragment;

public class MapsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
        initToolbar();
        OsmandMapFragment osmandMapFragment = new OsmandMapFragment();
        Bundle bundle = new Bundle();
        ComponentName callingActivity = getCallingActivity();
        Log.d(MapsActivity.class.getSimpleName(), "onClick: callingActivity = " + callingActivity);
        if (callingActivity != null) {
            bundle.putBoolean("request", true);
        }
        osmandMapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, osmandMapFragment)
                .commit();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.map);
//		mapFragment.getMapAsync(this);
    }

    public void responseCoordinatesFromMap(double latitude, double longitude) {
        Intent intent = getIntent();
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Карта");
        }
    }

	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
//	@Override
//	public void onMapReady(GoogleMap googleMap) {
//		mMap = googleMap;
//
//		// Add a marker in Sydney and move the camera
//		LatLng sydney = new LatLng(-34, 151);
//		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//	}
}
