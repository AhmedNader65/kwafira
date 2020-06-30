package com.almusand.kawfira.kwafira.home.ui;

import com.almusand.kawfira.Models.Login.User;

public interface StatusNavigator {
    void updatedSuccessfuly(User available);

    void revertAndShowToast();

    void update(boolean isChecked);

    void dontUpdate();
}
