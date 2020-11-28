package com.almusand.kawfira.ui.map.fragments;

public interface MapsNavigator {


    void showToast(String validateText);

    void showType(String address, String homeNum, String apartNum);

    void showType(String address, String homeNum, String apartNum, String lat, String lng);
}