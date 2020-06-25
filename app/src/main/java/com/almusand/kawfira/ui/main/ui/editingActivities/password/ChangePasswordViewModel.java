package com.almusand.kawfira.ui.main.ui.editingActivities.password;

import android.net.Uri;
import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.ErrorModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.ui.main.ui.editingActivities.name.ChangeNameNavigator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends BaseViewModel<ChangePasswordNavigator> {


    MsgModel msgModel;

    public void onStorePassword(String auth,String oldPassword,String newPassword) {
        msgModel = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onUpdatePassword("bearer"+auth,oldPassword,newPassword).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {

                if (response.isSuccessful()) {
                    setIsLoading(false);
                    msgModel = response.body();
                    getNavigator().showToast(msgModel.getMessage());

                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {

                setIsLoading(false);
            }

        });


    }
}
