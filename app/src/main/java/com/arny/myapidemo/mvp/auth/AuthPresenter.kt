package com.arny.myapidemo.mvp.auth

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arny.arnylib.security.CryptoStrings
import com.arny.arnylib.utils.Utility
import com.arny.myapidemo.database.RoomDB
import com.arny.myapidemo.database.ShopDao
import com.arny.myapidemo.models.User
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {
    init {
    }

    fun checkLoginPass(login: String, pass: String): Boolean {
        viewState.hideErrors()
        if (TextUtils.isEmpty(login)) {
            viewState.showLoginError()
            return false
        }
        if (TextUtils.isEmpty(pass)) {
            viewState.showPassError()
            return false
        }
        return true
    }

    fun register(login: String, pass: String, context: Context) {
        if (checkLoginPass(login, pass)) {
            var token = ""
            try {
                token = CryptoStrings.encrypt(login + pass, login + pass)
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.showAlert(e.message.toString())
                viewState.hideProgress()
            }

            val finalToken = token
            if (!Utility.empty(finalToken)) {

                Utility.mainThreadObservable(Observable.create<Boolean> { e ->
                    e.onNext(saveUser(context, login, finalToken))
                    e.onComplete()
                }).doOnSubscribe { _ -> viewState.showProgress() }
                        .subscribe({ res ->
                            viewState.hideProgress()
                            if (res!!) {
                                viewState.signUp()
                            } else {
                                viewState.showAlert("Неверный логин или пароль")
                            }
                        }) { throwable ->
                            var message = throwable.message
                            if (message?.contains("UNIQUE")!! && message.contains("login")) {
                                message = "Такой логин уже существует в базе"
                            }
                            viewState.showAlert(message)
                            viewState.hideProgress()
                            throwable.printStackTrace()
                        }
            }
        }
    }

    fun auth(login: String, pass: String, context: Context) {
        if (checkLoginPass(login, pass)) {
            var token = ""
            try {
                token = CryptoStrings.encrypt(login + pass, login + pass)
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.showAlert(e.message.toString())
                viewState.hideProgress()
            }

            val finalToken = token
            if (!Utility.empty(finalToken)) {
                Utility.mainThreadObservable(Observable.create<Boolean> { e ->
                    e.onNext(canLogin(context, finalToken))
                    e.onComplete()
                }).doOnSubscribe { viewState.showProgress() }
                        .subscribe({ res ->
                            viewState.hideProgress()
                            if (res!!) {
                                viewState.signUp()
                            } else {
                                viewState.showAlert("Неверный логин или пароль")
                            }
                        }) { throwable ->
                            viewState.showAlert(throwable.message.toString())
                            viewState.hideProgress()
                            throwable.printStackTrace()
                        }
            }
        }
    }

    @Throws(Exception::class)
    private fun saveUser(context: Context, login: String, token: String): Boolean {
        val user = User()
        user.login = login
        user.token = token
        val shopDao = RoomDB.getDb(context).shopDao
        return shopDao.insert(user) > 0
    }

    @Throws(Exception::class)
    private fun canLogin(context: Context, token: String): Boolean {
        val user = RoomDB.getDb(context).shopDao.getUserByToken(token)
        return user != null
    }

    fun alertRead() {
        viewState.hideMessage()
    }

}
