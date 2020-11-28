package com.almusand.kawfira.kwafira;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KwafiraServicesVM extends BaseViewModel{

    private MutableLiveData<List<ServicesModel>> servicesLiveData = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<List<ServicesModel>> getServices() {
        return servicesLiveData;
    }
    public MutableLiveData<User> getUserUpdated() {
        return user;
    }

    public void initServices(){
        RetroWeb.getClient().create(ServiceApi.class).onGetServices().enqueue(new Callback<ServicesResponseModel>() {
            @Override
            public void onResponse(Call<ServicesResponseModel> call, Response<ServicesResponseModel> response) {
                if (response.isSuccessful()) {
                    ServicesResponseModel model = response.body();
                    List<ServicesModel> modelList = model.getServices();
                    servicesLiveData.setValue(modelList);

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
            public void onFailure(Call<ServicesResponseModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }
    public void updateUser(String auth, ArrayList<String> services) {

        RetroWeb.getClient().create(ServiceApi.class).updateKwafiraServices("Bearer "+auth
                ,services).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel model = response.body();
                    User userModel = model.getResponse();
                    user.setValue(userModel);
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
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }

}