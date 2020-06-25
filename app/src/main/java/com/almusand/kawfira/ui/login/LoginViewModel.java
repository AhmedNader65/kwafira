package com.almusand.kawfira.ui.login;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    MutableLiveData<User> userLoginModelMutableLiveData = new MutableLiveData<>();
    LoginModel model;
    Error error;


    public boolean isEmailAndPasswordValid(String num, String password) {
        // validate email and password
        if (TextUtils.isEmpty(num)) {
            return false;
        }
        if (!CommonUtils.isPhoneValid(num)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }
    public void onClickLogin(String mobile, String password) {

        model = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onLogin(mobile, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if(response.isSuccessful()) {
                        Log.e("response",response.toString());
                        model = (LoginModel) response.body();
                        setIsLoading(false);
                        model.getResponse().setToken(model.getAccess_token());
                        userLoginModelMutableLiveData.setValue((User) model.getResponse());
                        if(((User)model.getResponse()).getVerified().equals("1")) {
                            getNavigator().openMainActivity(model);
                        }else{
                            //TODO to verification
                            getNavigator().openVerifyActivity((User) model.getResponse());
                        }
                    }else{
                        try {
                            Log.e("error",response.errorBody().string());
                        } catch (IOException e) {
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
    public void onServerLoginClick() {
        getNavigator().login();
    }
    public void onServerForgetClick() {
        getNavigator().forget();
    }

    public void onServerRegisterClick() {
        getNavigator().register();
    }

}
