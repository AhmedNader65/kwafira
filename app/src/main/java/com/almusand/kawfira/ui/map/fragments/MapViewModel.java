package com.almusand.kawfira.ui.map.fragments;

import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapViewModel extends BaseViewModel<MapsNavigator> {
    public void confirmAddress(String address,String homeNum,String apartNum){
        String validateText = validate(address,homeNum,apartNum);
        if(validateText.length()>1) {
            getNavigator().showToast(validateText);
        }else{
            getNavigator().showType(address,homeNum,apartNum);
        }
    }

    public void confirmAddress(String address,String homeNum,String apartNum,String lat ,String lng){
        getNavigator2().showType(address,homeNum,apartNum,lat,lng);
    }
    private String validate(String address, String homeNum, String apartNum) {
        if(address.length()<2)
            return "برجاء تحديد المكان على الخريطة";
        if(homeNum.length()<1)
            return "برحاء ادخال رقم المنزل";
        if(apartNum.length()<1)
            return "برجاء ادخال رقم الشقة";
        return "";
    }

}
