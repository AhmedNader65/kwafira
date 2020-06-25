package com.almusand.kawfira.ui.register;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.ErrorModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {
    MutableLiveData<User> userLoginModelMutableLiveData = new MutableLiveData<>();
    LoginModel model;
    ErrorModel errorModel;


    public boolean isEmailAndPasswordValid(String num,String email, String password) {
        // validate email and password
        if (TextUtils.isEmpty(num)) {
            return false;
        } if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (!CommonUtils.isPhoneValid(num)) {
            return false;
        }
        if (TextUtils.isEmpty(password)|| password.length()<8) {
            return false;
        }
        return true;
    }
    public void onServerRegisterClick() {
        getNavigator().register();
    }

    public void onClickRegister(String name, String email, String phone, String password, String role, String token) {
        model = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onRegisterUser(name, email, phone, password, role, token).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if (response.isSuccessful()) {
                        setIsLoading(false);
                    model = response.body();
                        Log.e("user", response.body().getResponse().toString());
                        userLoginModelMutableLiveData.setValue((User) response.body().getResponse());
                    ((User)model.getResponse()).setToken(model.getAccess_token());
                        getNavigator().openVerifyActivity((User)model.getResponse());
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
            public void onFailure(Call<LoginModel> call, Throwable t) {

                setIsLoading(false);
                getNavigator().handleError(t);

            }

        });
    }

}
