package com.almusand.kawfira.kwafira.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.kwafira.home.ui.StatusNavigator;

import java.io.IOException;

import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityViewModel extends BaseViewModel<StatusNavigator> {
    private MutableLiveData<User> userData = new MutableLiveData<>();

    public MutableLiveData<User> getUserData() {
        return userData;
    }

    private MutableLiveData<Boolean> isAvailable = new MutableLiveData<>();


    public MutableLiveData<Boolean> isAvailable() {
        return isAvailable;
    }


    public void setIsAvailable(boolean isAvailable, String userAuth) {

        updateUser(userAuth, isAvailable);

    }


    public void checkChanged(boolean switchState,boolean currentState ) {
        Log.e("cur >"+currentState,"switch > "+switchState);
        if(currentState == switchState){
            getNavigator().dontUpdate();
            getNavigator2().dontUpdate();
        }else{
            getNavigator().update(switchState);
            getNavigator2().update(switchState);
        }

    }

    public void getUser(String auth) {
        RetroWeb.getClient().create(ServiceApi.class).onGetUser("Bearer " + auth).enqueue(new Callback<LoginModel>() {
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
                Log.e("response", "" + call.toString());

                setIsLoading(false);

            }

        });
    }


    private void updateUser(String auth, boolean available) {

        RetroWeb.getClient().create(ServiceApi.class).updateUserStatus("Bearer " + auth
                , available ? 1 : 0).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel model = response.body();
                    isAvailable.setValue(available);
                    setIsLoading(false);
                    getNavigator().updatedSuccessfuly(model.getResponse());
                    getNavigator2().updatedSuccessfuly(model.getResponse());
                } else {

                    final Buffer buffer = new Buffer();

                    try {
                        call.request().body().writeTo(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getNavigator().revertAndShowToast();
                    getNavigator2().revertAndShowToast();

                    try {
                        Log.e("error reviews", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                setIsLoading(false);

            }

        });
    }


}
