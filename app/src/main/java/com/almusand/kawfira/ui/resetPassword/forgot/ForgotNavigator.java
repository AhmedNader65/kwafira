package com.almusand.kawfira.ui.resetPassword.forgot;

public interface ForgotNavigator {
    void showToast(String message);


    void forgotSuccess();

    void handleError(Throwable t);
}
