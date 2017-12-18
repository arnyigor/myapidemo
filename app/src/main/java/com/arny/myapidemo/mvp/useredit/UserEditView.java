package com.arny.myapidemo.mvp.useredit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface UserEditView extends MvpView {

    void initUI(String login,String email,@Nullable String avatar);

    void hideErrors();

    void setToolbarTitle(String title);

	void updateProgress(String progress);

	void showAlert(String message);

	void hideMessage();

	void viewImage(String uri);
}
