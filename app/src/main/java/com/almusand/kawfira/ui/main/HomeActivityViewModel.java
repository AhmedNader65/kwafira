package com.almusand.kawfira.ui.main;

import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityViewModel extends BaseViewModel<HomeActivityNavigator> {
    public void logout(String auth) {
        RetroWeb.getClient().create(ServiceApi.class).logout("Bearer "+auth).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if(response.isSuccessful()) {
                    Log.e("response",response.toString());
                    MsgModel model = response.body();
                    getNavigator().successfullyLogout();
                }else{
                    try {
                        Log.e("error",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {

                setIsLoading(false);

            }

        });

    }

}
