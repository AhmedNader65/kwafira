package com.almusand.kawfira.ui.main.ui.editingActivities.email;

public interface ChangeEmailNavigator {

    void showToast(String msg);

    void saveToPref(String toString, String filePath);

    void saveEmailToPref(String email);
}
