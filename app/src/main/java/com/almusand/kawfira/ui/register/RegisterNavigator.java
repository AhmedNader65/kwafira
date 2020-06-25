package com.almusand.kawfira.ui.register;

import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;

public interface RegisterNavigator {

    void handleError(Throwable throwable);

    void register();

    void openMainActivity(LoginModel model);
    void showToast(String msg);

    void openVerifyActivity(User user);
}