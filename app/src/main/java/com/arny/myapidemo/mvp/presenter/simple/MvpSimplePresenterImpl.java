package com.arny.myapidemo.mvp.presenter.simple;

import android.content.Context;
import android.util.Log;
import com.androidnetworking.common.Method;
import com.arny.arnylib.network.NetworkService;
import com.arny.arnylib.interfaces.OnStringRequestResult;
import com.arny.myapidemo.mvp.view.simple.MvpSimpleActivity;
import com.arny.myapidemo.mvp.view.simple.MvpSimpleView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
public class MvpSimplePresenterImpl extends MvpBasePresenter<MvpSimpleView> implements MvpSimplePresenter {
	private Context context;

	public MvpSimplePresenterImpl(Context context) {
		this.context = context;
	}

	@Override
	public void buttonPressed() {
		NetworkService.apiRequest(context, Method.GET,"http://beta.json-generator.com/api/json/get/4J2sIi6Tf", null, new OnStringRequestResult() {
			@Override
			public void onSuccess(String result) {
				Log.i(MvpSimpleActivity.class.getSimpleName(), "onSuccess: result = " + result);
				if (isViewAttached()) {
					getView().showData(result);
				}
			}

			@Override
			public void onError(String error) {
				Log.i(MvpSimpleActivity.class.getSimpleName(), "onError: error = " + error);
				if (isViewAttached()) {
					getView().showData(error);
				}
			}
		});
	}
}
