package com.arny.myapidemo.mvp;

import android.content.Context;
import android.util.Log;
import com.arny.arnylib.network.NetworkService;
import com.arny.arnylib.network.OnStringRequestResult;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import org.json.JSONObject;
public class MvpSimplePresenterImpl extends MvpBasePresenter<MvpSimpleView> implements MvpSimplePresenter {
	private Context context;

	public MvpSimplePresenterImpl(Context context) {
		this.context = context;
	}

	@Override
	public void buttonPressed() {
		NetworkService.apiRequest(context, "http://beta.json-generator.com/api/json/get/4J2sIi6Tf", new JSONObject(), new OnStringRequestResult() {
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
		} );


	}



}
