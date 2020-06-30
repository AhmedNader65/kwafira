package com.almusand.kawfira.ui.login;

import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;

public interface LoginNavigator {

    void handleError(Throwable throwable);

    void login();
    void register();
    void openVerifyActivity(User user);

    void openMainActivity(LoginModel model);

    void forget();

    void registerKwafira();

    void openVerifyIdForKwafira(LoginModel user);

    void openUnderReviewActivity(LoginModel model);

    void openMainActivityForKwafira(LoginModel model);
}