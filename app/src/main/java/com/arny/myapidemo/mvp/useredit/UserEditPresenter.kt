package com.arny.myapidemo.mvp.useredit

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arny.arnylib.files.FileUtils
import com.arny.arnylib.security.CryptoStrings
import com.arny.arnylib.utils.DroidUtils
import com.arny.arnylib.utils.Utility
import com.arny.myapidemo.database.RoomDB
import com.arny.myapidemo.database.ShopDao
import com.arny.myapidemo.models.User
import com.arny.myapidemo.mvp.auth.AuthView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.io.File

@InjectViewState
class UserEditPresenter : MvpPresenter<UserEditView>() {
    init {
    }
    fun initState(context: Context, login: String?) {
        if (login != null) {
            viewState.setToolbarTitle("Редактирование пользователя " + login)
            Utility.mainThreadObservable(RoomDB.getDb(context).shopDao.getUserByLogin(login))
                    .subscribe({ user ->
                        if (user != null) {
                            viewState.initUI(user.login, user.email, user.avatar)
                        } else {
                            viewState.showAlert("Пользователь не найден в базе")
                        }
                    }) { throwable ->
                        throwable.printStackTrace()
                        viewState.showAlert(throwable.message)
                    }
        } else {
            viewState.setToolbarTitle("Создание пользователя")
        }
    }

    @Throws(Exception::class)
    fun saveUser(context: Context, login: String, token: String): Boolean {
        val user = User()
        user.login = login
        user.token = token
        val shopDao = RoomDB.getDb(context).shopDao
        return shopDao.insert(user) > 0

    }


    fun checkLoginPass(login: String, pass: String): Boolean {
        viewState.hideErrors()
        if (TextUtils.isEmpty(login)) {
            //            getViewState().showLoginError();
            return false
        }
        return if (TextUtils.isEmpty(pass)) {
            //            getViewState().showPassError();
            false
        } else true
    }


    fun register(login: String, pass: String, context: Context) {
        if (checkLoginPass(login, pass)) {
            var token = ""
            try {
                token = CryptoStrings.encrypt(login + pass, login + pass)
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.showAlert(e.message)
                viewState.hideErrors()
            }

            val finalToken = token
            if (!Utility.empty(finalToken)) {
                Utility.mainThreadObservable(Observable.create<Boolean> { e ->
                    e.onNext(saveUser(context, login, finalToken))
                    e.onComplete()
                }).subscribe({ res ->
                    //                            getViewState().hideProgress();
                    if (res!!) {
                        //                                getViewState().signUp();
                    } else {
                        viewState.showAlert("Неверный логин или пароль")
                    }
                }) { throwable ->
                    var message = throwable.message
                    if (message?.contains("UNIQUE")!! && message.contains("login")) {
                        message = "Такой логин уже существует в базе"
                    }
                    viewState.showAlert(message)
                    //                            getViewState().hideProgress();
                    throwable.printStackTrace()
                }
            }
        }
    }

    fun alertRead() {
        viewState.hideMessage()
    }

    fun viewImage(file: File,context:Context) {
        val fileUri = FileUtils.getFileUri(context, file)
        viewState.viewImage(fileUri.toString())
    }

}
