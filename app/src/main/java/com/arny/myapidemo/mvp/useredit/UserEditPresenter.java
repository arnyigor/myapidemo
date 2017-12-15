package com.arny.myapidemo.mvp.useredit;

import android.content.Context;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.database.RoomDB;
import com.arny.myapidemo.mvp.auth.AuthView;
@InjectViewState
public class UserEditPresenter extends MvpPresenter<UserEditView> {

    public UserEditPresenter() {
        // Required empty public constructor
    }

    public void initState(Context context, String login) {
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

    }

    public void alertRead() {
        getViewState().hideMessage();
    }

}
