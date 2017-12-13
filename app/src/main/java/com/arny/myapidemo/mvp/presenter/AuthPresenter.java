package com.arny.myapidemo.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.utils.Config;
import com.arny.myapidemo.mvp.view.AuthView;

import java.util.concurrent.TimeUnit;
@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView> {
	public void checkLoginPass(String login, String pass, Context context) {
		getViewState().hideErrors();
		if (TextUtils.isEmpty(login)) {
			getViewState().showLoginError();
			return;
		}
		if (TextUtils.isEmpty(pass)) {
			getViewState().showPassError();
			return;
		}
		sleepSecs(login, pass, context);
	}

	@SuppressLint("StaticFieldLeak")
	private void sleepSecs(String login, String pass, Context context) {
		new AsyncTask<Void, String, Void>() {
			@Override
			protected void onPreExecute() {
				getViewState().showProgress();
			}

			@Override
			protected Void doInBackground(Void... voids) {
				try {
					int max = 5;
					for (int i = 0; i < max; i++) {
						if (i == 3) {
							throw new Exception("new Exception");
						}
						int perc = (int) (((double)i/ max) * 100);
						publishProgress(String.valueOf(perc));
						sleep();
					}
				} catch (Exception e) {
					e.printStackTrace();
					publishProgress(e.getMessage());
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				getViewState().updateProgress("Progress:" + values[0] + "%");
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				getViewState().hideProgress();
				canLogin(login.concat(pass).equals(Config.getString("loginpass", context)));
			}
		}.execute();
	}

	private void canLogin(boolean loginpass) {
		if (loginpass) {
			getViewState().loggedIn();
		} else {
			getViewState().showAlert("Wrong login or pass");
		}
	}

	private void sleep() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onHideMessage(){
		getViewState().hideMessage();
	}
}
