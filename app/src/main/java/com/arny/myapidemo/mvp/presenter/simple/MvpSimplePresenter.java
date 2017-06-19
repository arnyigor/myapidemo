package com.arny.myapidemo.mvp.presenter.simple;

import com.arny.myapidemo.mvp.view.simple.MvpSimpleView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
public interface MvpSimplePresenter extends MvpPresenter<MvpSimpleView> {
	void buttonPressed();
}
