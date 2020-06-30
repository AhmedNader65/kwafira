package com.almusand.kawfira.ui.verify;

import com.almusand.kawfira.Models.Login.LoginModel;

public interface VerificationNavigator {

    void handleError(Throwable throwable);

    void verify(String code);
    void verifyFailed();
    void showToast(String msg);

    void resend();
    void resendSuccess();

    void openMainActivity(LoginModel model);

    void openResetActivity(String code);

    void SendVerifyRequest(String code);

    void openVerifyIdActivity(LoginModel model);
}