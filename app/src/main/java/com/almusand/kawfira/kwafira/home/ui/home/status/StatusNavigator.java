package com.almusand.kawfira.kwafira.home.ui.home.status;

import com.almusand.kawfira.Models.Login.User;

public interface StatusNavigator {
    void updatedSuccessfuly(User available);

    void revertAndShowToast(String msg);

    void update(boolean isChecked);

    void dontUpdate();

    void successfullyLogout();
}
