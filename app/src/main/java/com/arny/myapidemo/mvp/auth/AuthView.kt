package com.arny.myapidemo.mvp.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AuthView : MvpView {
    fun signUp()

    fun signIn()

    fun showLoginError()

    fun showPassError()

    fun hideErrors()

    fun showAuthError()

    fun showProgress()

    fun hideProgress()

    fun setToolbarTitle(title: String)

    fun updateProgress(progress: String)

    fun showAlert(message: String)

    fun hideMessage()
}
