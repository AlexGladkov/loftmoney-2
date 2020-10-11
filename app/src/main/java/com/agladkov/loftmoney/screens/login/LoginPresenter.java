package com.agladkov.loftmoney.screens.login;

public interface LoginPresenter {
    void attachViewState(LoginView loginView);
    void disposeRequests();
}
