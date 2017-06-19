package com.arny.myapidemo.mvp.view.hello;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.mvp.presenter.hello.HelloWorldPresenter;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

public class HelloWorldActivity extends MvpActivity<HelloWorldView, HelloWorldPresenter>
		implements HelloWorldView, View.OnClickListener {

	private TextView greetingTextView;
	private Button helloButton;
	private Button goodbyButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mvp_load);
		greetingTextView = (TextView) findViewById(R.id.greetingTextView);
		helloButton = (Button) findViewById(R.id.helloButton);
		goodbyButton = (Button) findViewById(R.id.goodbyButton);
		goodbyButton.setOnClickListener(this);
	}

	@NonNull
	@Override
	public HelloWorldPresenter createPresenter() {
		return new HelloWorldPresenter();
	}

	@Override
	public void showHello(String greetingText) {
		greetingTextView.setTextColor(Color.RED);
		greetingTextView.setText(greetingText);
	}

	@Override
	public void showGoodbye(String greetingText) {
		greetingTextView.setTextColor(Color.BLUE);
		greetingTextView.setText(greetingText);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.helloButton:
				presenter.greetHello();
				break;
			case R.id.goodbyButton:
				presenter.greetGoodbye();
				break;
		}
	}
}
