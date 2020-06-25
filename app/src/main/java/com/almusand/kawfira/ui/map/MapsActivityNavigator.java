package com.almusand.kawfira.ui.map;

public interface MapsActivityNavigator {

    void handleError(Throwable throwable);




    void showBottomSheet();
    void ToCallKawafira();

    void ToChatWithKawafira();

    void ToOpenKawafiraProfile();

    void openMainActivity();

    void scheduling();

    void confirm();


}