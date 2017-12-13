package com.arny.myapidemo.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface BaseView extends MvpView {
    void subscribe();
    void unsubscribe();
}
