package com.arny.myapidemo.mvp.useredit;

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
import com.arny.myapidemo.mvp.auth.AuthView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
@InjectViewState
public class UserEditPresenter extends MvpPresenter<UserEditView> {

    public UserEditPresenter() {
        // Required empty public constructor
        UserEditView viewState = getViewState();
        Log.i(UserEditPresenter.class.getSimpleName(), "UserEditView: " + viewState);
    }

    public void initState(Context context, String login) {
        if (login != null) {
            getViewState().setToolbarTitle("Редактирование пользователя " + login);
            Utility.mainThreadObservable(RoomDB.getDb(context).getShopDao().getUserByLogin(login))
                    .subscribe(user -> {
                        if (user != null) {
                            getViewState().initUI(user.getLogin(),user.getEmail(),user.getAvatar());
                        }else{
                            getViewState().showAlert("Пользователь не найден в базе");
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        getViewState().showAlert(throwable.getMessage());
                    });
        }else{
            getViewState().setToolbarTitle("Создание пользователя");
        }
    }

    private boolean saveUser(Context context, String login, String token) throws Exception {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        ShopDao shopDao = RoomDB.getDb(context).getShopDao();
        return shopDao.insert(user) > 0;

    }


    public boolean checkLoginPass(String login, String pass) {
        getViewState().hideErrors();
        if (TextUtils.isEmpty(login)) {
//            getViewState().showLoginError();
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
//            getViewState().showPassError();
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
                getViewState().hideErrors();
            }

            String finalToken = token;
            if (!Utility.empty(finalToken)) {
                Utility.mainThreadObservable(Observable.create((ObservableOnSubscribe<Boolean>) e -> {
                    e.onNext(saveUser(context, login, finalToken));
                    e.onComplete();
                }))
//                        .doOnSubscribe(disposable -> getViewState().showProgress())
                        .subscribe(res -> {
//                            getViewState().hideProgress();
                            if (res) {
//                                getViewState().signUp();
                            } else {
                                getViewState().showAlert("Неверный логин или пароль");
                            }
                        }, throwable -> {
                            String message = throwable.getMessage();
                            if (message.contains("UNIQUE") && message.contains("login")) {
                                message = "Такой логин уже существует в базе";
                            }
                            getViewState().showAlert(message);
//                            getViewState().hideProgress();
                            throwable.printStackTrace();
                        });
            }
        }
    }

    public void alertRead() {
        getViewState().hideMessage();
    }

}
