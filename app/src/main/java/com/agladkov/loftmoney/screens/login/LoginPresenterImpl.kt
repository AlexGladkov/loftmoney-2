package com.agladkov.loftmoney.screens.login

class LoginPresenterImpl : LoginPresenter {

    private lateinit var loginView: LoginView

    override fun attachViewState(loginView: LoginView) {
        this.loginView = loginView
    }

    override fun disposeRequests() {
    }
}