package com.arny.myapidemo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.arny.myapidemo.R;

public class CardViewActivity extends AppCompatActivity {
    TextView personName;
    TextView personAge;
    ImageView personPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        personName = (TextView)findViewById(R.id.person_name);
        personAge = (TextView)findViewById(R.id.person_age);
        personPhoto = (ImageView)findViewById(R.id.person_photo);
        personName.setText("Emma Wilson");
        personAge.setText("23 years old");
        personPhoto.setImageResource(android.R.drawable.ic_media_play);
    }
}
