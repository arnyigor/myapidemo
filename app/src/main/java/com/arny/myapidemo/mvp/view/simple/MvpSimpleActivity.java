package com.arny.myapidemo.mvp.view.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.mvp.presenter.simple.MvpSimplePresenter;
import com.arny.myapidemo.mvp.presenter.simple.MvpSimplePresenterImpl;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

public class MvpSimpleActivity extends MvpActivity<MvpSimpleView,MvpSimplePresenter>  implements MvpSimpleView {

	private FloatingActionButton fab;
	private TextView tvresult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mvp);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		fab = (FloatingActionButton) findViewById(R.id.fab);
		tvresult = (TextView) findViewById(R.id.tvresult);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getPresenter().buttonPressed();
			}
		});
	}

	@NonNull
	@Override
	public MvpSimplePresenter createPresenter() {
		return new MvpSimplePresenterImpl(getApplicationContext());
	}

	@Override
	public void showData(String data) {
		tvresult.setText(data);
	}
}
