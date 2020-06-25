package com.almusand.kawfira.ui.verify;

import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.ErrorModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationViewModel extends BaseViewModel<VerificationNavigator> {
    ErrorModel errorModel;
    LoginModel model;

    public void onServerClickVerify(String code) {

        if(code.length()==4) {
            getNavigator().verify(code);
        }else{
            getNavigator().showToast("الكود غير صحيح");
        }
    }

    public void onServerClickResend() {
        getNavigator().resend();

    }

    public void sendVerify(String code, String token) {

        model = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onUserVerify(code, "Bearer "+token).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if (response.isSuccessful()) {
                        setIsLoading(false);
                        model = response.body();
                        if(model.getCode()==2){
                            getNavigator().verifyFailed();
                            getNavigator().showToast("لقد مر ٣ دقائق على هذا الكود برجاء اعادة الارسال");
                        }else if(model.getCode()==0) {
                            getNavigator().verifyFailed();
                            getNavigator().showToast("الكود غير صحيح");
                        }else{
                            Log.e("user", response.body().getResponse().toString());
                                getNavigator().openMainActivity(model);

                        }

                } else {
                    try {
                        Log.e("Clearing","code١");

                        Gson gson = new Gson();
                        Type type = new TypeToken<ErrorModel>() {}.getType();
                        ErrorModel errorResponse = gson.fromJson(response.errorBody().charStream(),type);
                        getNavigator().showToast(errorResponse.getError().get(0).get(0));
                        getNavigator().verifyFailed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                setIsLoading(false);
                getNavigator().handleError(t);

            }

        });
    }
    MsgModel msgModel;
    public void resend (String phone) {
        msgModel = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onUserResendVerify(phone).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {

                if (response.isSuccessful()) {
                        setIsLoading(false);
                    msgModel = response.body();
                    getNavigator().showToast(msgModel.getMessage());
                    getNavigator().resendSuccess();

                } else {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ErrorModel>() {}.getType();
                        ErrorModel errorResponse = gson.fromJson(response.errorBody().charStream(),type);
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

    public void whichToLoad(String code, boolean isReset) {
        if(isReset){
            getNavigator().openResetActivity(code);
            return;
        }else{
            getNavigator().SendVerifyRequest(code);
        }
    }
}
