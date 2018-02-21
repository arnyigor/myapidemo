package com.arny.myapidemo.domain.interactors;

import com.arny.myapidemo.domain.interactors.base.Interactor;
public interface WelcomeInteractor extends Interactor {
	interface Callback {
		void onMessageShow(String message);

		void onShowFailed(String error);
	}

}
