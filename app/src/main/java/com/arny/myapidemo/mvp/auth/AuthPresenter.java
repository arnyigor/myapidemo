package com.arny.myapidemo.mvp.auth;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.security.CryptoStrings;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.database.RoomDB;
import com.arny.myapidemo.database.ShopDao;
import com.arny.myapidemo.models.User;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView> {

    public AuthPresenter() {
        // Required empty public constructor
        AuthView viewState = getViewState();
        Log.i(AuthPresenter.class.getSimpleName(), "AuthPresenter: " + viewState);
    }

    public boolean checkLoginPass(String login, String pass) {
        getViewState().hideErrors();
        if (TextUtils.isEmpty(login)) {
            getViewState().showLoginError();
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            getViewState().showPassError();
            return false;
        }
        return true;
    }

    public void register(String login, String pass, Context context) {
        if (checkLoginPass(login, pass)) {
            String token = "";
            try {
                token = CryptoStrings.encrypt(login + pass, login + pass);
            } catch (Exception e) {
                e.printStackTrace();
                getViewState().showAlert(e.getMessage());
                getViewState().hideProgress();
            }

            String finalToken = token;
            if (!Utility.empty(finalToken)) {
                Utility.mainThreadObservable(Observable.create((ObservableOnSubscribe<Boolean>) e -> {
                    e.onNext(saveUser(context, login, finalToken));
                    e.onComplete();
                }))
                        .doOnSubscribe(disposable -> getViewState().showProgress())
                        .subscribe(res -> {
                            getViewState().hideProgress();
                            if (res) {
                                getViewState().signUp();
                            } else {
                                getViewState().showAlert("Неверный логин или пароль");
                            }
                        }, throwable -> {
                            String message = throwable.getMessage();
                            if (message.contains("UNIQUE") && message.contains("login")) {
                                message = "Такой логин уже существует в базе";
                            }
                            getViewState().showAlert(message);
                            getViewState().hideProgress();
                            throwable.printStackTrace();
                        });
            }
        }
    }

    public void auth(String login, String pass, Context context) {
        if (checkLoginPass(login, pass)) {
            String token = "";
            try {
                token = CryptoStrings.encrypt(login + pass, login + pass);
            } catch (Exception e) {
                e.printStackTrace();
                getViewState().showAlert(e.getMessage());
                getViewState().hideProgress();
            }
            String finalToken = token;
            if (!Utility.empty(finalToken)) {
                Utility.mainThreadObservable(Observable.create((ObservableOnSubscribe<Boolean>) e -> {
                    e.onNext(canLogin(context, finalToken));
                    e.onComplete();
                }))
                        .doOnSubscribe(disposable -> getViewState().showProgress())
                        .subscribe(res -> {
                            getViewState().hideProgress();
                            if (res) {
                                getViewState().signUp();
                            } else {
                                getViewState().showAlert("Неверный логин или пароль");
                            }
                        }, throwable -> {
                            getViewState().showAlert(throwable.getMessage());
                            getViewState().hideProgress();
                            throwable.printStackTrace();
                        });
            }
        }
    }

    private boolean saveUser(Context context, String login, String token) throws Exception {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        ShopDao shopDao = RoomDB.getDb(context).getShopDao();
        return shopDao.insert(user) > 0;

    }

    private boolean canLogin(Context context, String token) throws Exception {
        User user = RoomDB.getDb(context).getShopDao().getUserByToken(token);
        return user != null;
    }

    public void alertRead() {
        getViewState().hideMessage();
    }

}
