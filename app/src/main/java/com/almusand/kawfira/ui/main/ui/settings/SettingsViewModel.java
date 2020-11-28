package com.almusand.kawfira.ui.main.ui.settings;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsViewModel extends BaseViewModel {

    private MutableLiveData<User> userData = new MutableLiveData<>();
    public MutableLiveData<String> language;

    public MutableLiveData<User> getUserData() {
        return userData;
    }
    public MutableLiveData<String> getLang() {
        return language;
    }

    public SettingsViewModel() {
        this.language  =new MutableLiveData<>();;
    }

    public void getUser(String auth){
        language.setValue(Locale.getDefault().getDisplayLanguage());
        Log.e("language",Locale.getDefault().getDisplayLanguage());
        RetroWeb.getClient().create(ServiceApi.class).onGetUser("Bearer "+auth).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.e("response", "response.toString()");
                if (response.isSuccessful()) {
                    LoginModel model = response.body();
                    User user = model.getResponse();
                    userData.setValue(user);
                    setIsLoading(false);

                } else {
                    try {
                        Log.e("error reviews", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("response", ""+call.toString());

                setIsLoading(false);

            }

        });
    }

}