package com.arny.myapidemo.mvp.view;

public interface AuthView extends LoadingView {
    void openRepoScreen();

    void showLoginError();

    void showPasswordError();
}
