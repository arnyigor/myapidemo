package com.arny.myapidemo.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import com.arny.myapidemo.R;

//==============ActivityName start=========================
public class LocationActivity extends Activity implements LocationListener {
	static Location imHere; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.
	// =============Variables start================
	String latLongString;
	String provider = LocationManager.GPS_PROVIDER;
	int t = 1000; // миллисекунды
	int distance = 1; // meters
	LocationManager locationManager;
	String context = Context.LOCATION_SERVICE;
	// =============Variables end================
	// ==============Forms variables start==============
	TextView myLocationText;
	final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status,
									Bundle extras) {
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		setTitle("location");
		SetUpLocationListener(this);

		// ==================onCreateCode end=========================
	}

	public void SetUpLocationListener(Context context) // это нужно запустить в самом начале работы программы
	{
		LocationManager locationManager = (LocationManager)
				context.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationActivity();

		if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    public void requestPermissions(@NonNull String[] permissions, int requestCode)
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for Activity#requestPermissions for more details.
			return;
		}
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				5000,
				10,
				locationListener); // здесь можно указать другие более подходящие вам параметры

		imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	// ==================CustomCode start=========================
	private void updateWithNewLocation(Location location) {
		myLocationText = (TextView) findViewById(R.id.myLocationText);
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + lat + "\nLong:" + lng;
		} else {
			latLongString = "No location found";
		}
		myLocationText.setText("Your Current Position is:\n" + latLongString);
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	// ====================OnClicks======================================

	// ====================OnClicks end======================================

}// ===================ActivityName end==================================
// ===================SimpleActivity==================================