package com.arny.myapidemo.mvp.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.arny.myapidemo.mvp.BaseView;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface AuthView extends BaseView {
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
