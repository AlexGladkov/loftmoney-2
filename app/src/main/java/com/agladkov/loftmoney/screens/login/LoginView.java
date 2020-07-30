package com.agladkov.loftmoney.screens.login;

public interface LoginView {
    void toggleSending(boolean isActive);
    void showMessage(String text);
    void showSuccess(String token);
}
