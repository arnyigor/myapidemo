package com.arny.myapidemo.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface LoginView extends MvpView {
	void loggedIn();

	void showLoginError();

	void showPassError();

	void showAuthError();

	void hideErrors();

	void showProgress();

	void hideProgress();

	void updateProgress(String progress);

	void showAlert(String message);

	void hideMessage();
}
