package com.arny.myapidemo.mvp;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
public interface MvpSimplePresenter extends MvpPresenter<MvpSimpleView> {
	void buttonPressed();
}
