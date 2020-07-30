package com.agladkov.loftmoney.screens.login;

import com.agladkov.loftmoney.remote.AuthApi;

public interface LoginPresenter {
    void performLogin(AuthApi authApi);
    void attachViewState(LoginView loginView);
    void disposeRequests();
}
