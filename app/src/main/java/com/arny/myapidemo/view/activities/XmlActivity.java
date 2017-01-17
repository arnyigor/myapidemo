package com.arny.myapidemo.view.activities;//Package name

// imports start==========
import java.math.BigDecimal;
import java.util.ArrayList;

import com.arny.myapidemo.R;
import org.xmlpull.v1.XmlPullParser;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// imports end==========

//==============Activity start=========================
public class XmlActivity extends Activity {
	// =============Variables start================
	final String LOG_TAG = "myLogs";
	ArrayList<Double> lat = new ArrayList<Double>();
	ArrayList<Double> lng = new ArrayList<Double>();
	ArrayList<Double> dec = new ArrayList<Double>();
	double latitude;
	double longitude;
	double declination;
	int i,j,k,l,m;

	EditText edt1;
	EditText edt2;
	TextView tv1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.declinationview);
		setTitle("Main");

		// ================Forms Ids start=========================
		edt1 = (EditText) findViewById(R.id.editText1);
		edt2 = (EditText) findViewById(R.id.editText2);
		tv1 = (TextView) findViewById(R.id.textView1);
		// ================Forms Ids end=========================

		// ==================onCreateCode start=========================
		try {
			XmlPullParser parser = getResources().getXml(R.xml.declination);

			while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlPullParser.START_TAG
						&& parser.getName().equals("result")) {
						lat.add(Double.parseDouble(parser.getAttributeValue(1)));
						lng.add(Double.parseDouble(parser.getAttributeValue(2)));
						dec.add(Double.parseDouble(parser.getAttributeValue(0)));
				}
				parser.next();
			}
		} catch (Throwable t) {
			Toast.makeText(this,
					"Ошибка при загрузке XML-документа: " + t.toString(),
					Toast.LENGTH_LONG).show();
		}
		// ==================onCreateCode end=========================

	}// ============onCreate end====================

	// ====================CustomCode start======================================
	  public BigDecimal roundUp(double value, int digits){
	      return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
	  } 
	
	
	private void finddeclination() 
    {
		latitude=Double.parseDouble(String.valueOf(roundUp(latitude,0)));
		longitude=Double.parseDouble(String.valueOf(roundUp(longitude,0)));
		for (int i = 0;i<lat.size(); i++) {
			if (lat.get(i)==latitude) break;
		}
		for (int j = i; j<lng.size(); j++) {
			if (lng.get(j)==longitude) break;
		}
		declination=Double.parseDouble(String.valueOf(roundUp(dec.get(j),0)));
    }
	
	// ====================CustomCode end======================================

	// ====================OnClicks======================================

	public void onDecClick(View view )
    {
		latitude= (double) Float.parseFloat(edt1.getText().toString());
		longitude= (double) Float.parseFloat(edt2.getText().toString());
		finddeclination();
		tv1.setText(String.valueOf(declination));
    }
	
	// ====================OnClicks end======================================
}// ===================Activity end==================================
// ===================SimpleActivity==================================
