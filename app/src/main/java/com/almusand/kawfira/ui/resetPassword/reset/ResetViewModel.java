package com.almusand.kawfira.ui.resetPassword.reset;

import android.text.TextUtils;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.ErrorModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetViewModel extends BaseViewModel<ResetNavigator> {

    public void onServerClickSavePassword(String password){
        getNavigator().savePassword(password);

    }

    MsgModel msgModel;
    public void onStorePassword(String password,String code) {
        msgModel = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onStorePassword(password,code).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {

                if (response.isSuccessful()) {
                    setIsLoading(false);
                    msgModel = response.body();

                    if(msgModel.getCode()==2){
                        getNavigator().failedStore();
                    }else {
                        getNavigator().storeSuccess();
                    }
                    getNavigator().showToast(msgModel.getMessage());

                } else {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ErrorModel>() {
                        }.getType();
                        ErrorModel errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                        getNavigator().showToast(errorResponse.getError().get(0).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                        getNavigator().showToast("الرمز غير صحيح");
                    }
                    getNavigator().failedStore();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {

                setIsLoading(false);
                getNavigator().handleError(t);

            }

        });


    }


    public boolean isPasswordValid( String password) {
        // validate email and password
        return !TextUtils.isEmpty(password) && password.length() >= 8;
    }

}
