package com.almusand.kawfira.ui.resetPassword.forgot;

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

public class ForgotViewModel extends BaseViewModel<ForgotNavigator> {

    MsgModel msgModel;

    public void onServerClickSend(String phone) {
        msgModel = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onResetPassword(phone).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {

                if (response.isSuccessful()) {
                    setIsLoading(false);
                    msgModel = response.body();

                    getNavigator().showToast(msgModel.getMessage());
                    getNavigator().forgotSuccess();

                } else {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ErrorModel>() {
                        }.getType();
                        ErrorModel errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                        getNavigator().showToast(errorResponse.getError().get(0).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {

                setIsLoading(false);
                getNavigator().handleError(t);

            }

        });


    }
}
