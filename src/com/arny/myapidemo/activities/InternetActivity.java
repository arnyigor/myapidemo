package com.arny.myapidemo.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import com.arny.myapidemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//==============ActivityName start=========================
public class InternetActivity extends  Activity {
	// =============Variables start================

	// =============Variables end================

	// ==============Forms variables start==============
	TextView tv1;
	TextView tv2;
	TextView tv3;
	// ==============Forms variables end==============

	// ====================onCreate start=========================
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.internet);
		setTitle("Internet");

		// ================Forms Ids start=========================
		tv1 = (TextView)findViewById(R.id.txtView1);
		tv2 = (TextView)findViewById(R.id.txtView2);
		tv3 = (TextView)findViewById(R.id.txtView3);
		// ================Forms Ids end=========================

		// ==================onCreateCode start=========================
		//=================================internet=================
		tv1.append("Total:") ;
		tv1.append("\n\tRX Bytes:\t" + TrafficStats.getTotalRxBytes());
		tv1.append("\n\tRX Packets:\t" + TrafficStats.getTotalRxPackets());
		tv1.append("\n\tTX Bytes:\t" + TrafficStats.getTotalTxBytes()); 
		tv1.append("\n\tTX Packets:\t" + TrafficStats.getTotalTxPackets());
		tv1.append("\nMobile:");
		tv1.append("\n\tRX Bytes:\t" + TrafficStats.getMobileRxBytes());
		tv1.append("\n\tRX Packets:\t" + TrafficStats.getMobileRxPackets()); 
		tv1.append("\n\tTX Bytes:\t" + TrafficStats.getMobileTxBytes()); 
		tv1.append("\n\tTX Packets:\t" + TrafficStats.getMobileTxPackets());
		
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] ni = cm.getAllNetworkInfo();
		for (int i = 0; i < ni.length; i++) {
		tv2.append("Type:\t" + ni[i].getTypeName()); 
		tv2.append("\n\tAvailable:\t" + ni[i].isAvailable()); 
		tv2.append("n\n\tConnected:\t" + ni[i].isConnected()); 
		tv2.append("\n\tExtra:\t" + ni[i] .getExtraInfo() + "\n");
		} 
		//=================================internet=================
		
		//===============WIFI=========================
		String service = Context.WIFI_SERVICE;
		WifiManager wifi = (WifiManager)getSystemService(service);
		
		WifiInfo info = wifi.getConnectionInfo();
		if (info.getBSSID() != null) {
		int strength = WifiManager.calculateSignalLevel(info.getRssi(), 5);
		int speed = info.getLinkSpeed();
		String units = WifiInfo.LINK_SPEED_UNITS;
		String ssid = info.getSSID();
		String cSummary = String.format("Connected to %s at %s%s. Strength %s/5",
		ssid, speed, units, strength);
		
		tv3.append(":\t"+service);
		tv3.append("\n\tcSummary:\t"+cSummary);
		}
		//===============WIFI=========================
		
		
		
		//========================Download file================================
		try {
		    URL url = new URL("url from apk file is to be downloaded");
		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		    urlConnection.setRequestMethod("GET");
		    urlConnection.setDoOutput(true);
		    urlConnection.connect();

		    File sdcard = Environment.getExternalStorageDirectory();
		    File file = new File(sdcard, "filename.ext");

		    FileOutputStream fileOutput = new FileOutputStream(file);
		    InputStream inputStream = urlConnection.getInputStream();

		    byte[] buffer = new byte[1024];
		    int bufferLength = 0;

		    while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
		        fileOutput.write(buffer, 0, bufferLength);
		    }
		    fileOutput.close();

		} catch (MalformedURLException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		//========================Download file================================
		// ==================onCreateCode end=========================
	}//============onCreate end====================

	// ==================CustomCode start=========================
	// ==================CustomCode start=========================

	// ====================OnClicks======================================

	// ====================OnClicks end======================================

}// ===================ActivityName end==================================
// ===================SimpleActivity==================================