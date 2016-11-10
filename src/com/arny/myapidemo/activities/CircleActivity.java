package com.arny.myapidemo.activities;//Package name

// imports start==========
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import com.arny.myapidemo.R;
// imports end==========


//==============Activity start=========================
public class CircleActivity extends Activity
{
    TextView Tv1;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            Tv1 = (TextView)findViewById(R.id.textView1);
    }
        public boolean onCreateOptionsMenu(Menu menu) {
        	getMenuInflater().inflate(R.menu.menu_main, menu);
        	return true;
        }

    public void onExitItemClick(MenuItem item)
    {
    	finish();
    }
    
    public void onBtnClick(View view)
    {
    	Toast.makeText(getApplication(), "Кликнул", Toast.LENGTH_LONG).show();
    }
}