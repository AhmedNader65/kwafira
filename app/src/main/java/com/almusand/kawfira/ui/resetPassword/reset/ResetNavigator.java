package com.almusand.kawfira.ui.resetPassword.reset;

public interface ResetNavigator {
    void savePassword(String password);

    void showToast(String message);

    void storeSuccess();

    void handleError(Throwable t);

    void failedStore();
}
