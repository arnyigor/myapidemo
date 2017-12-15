package com.arny.myapidemo.mvp.auth;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface AuthView extends MvpView {
	void loggedIn();

	void showLoginError();

	void showPassError();

    void hideErrors();

	void showAuthError();

	void showProgress();

	void hideProgress();

	void setToolbarTitle(String title);

	void updateProgress(String progress);

	void showAlert(String message);

	void hideMessage();
}
