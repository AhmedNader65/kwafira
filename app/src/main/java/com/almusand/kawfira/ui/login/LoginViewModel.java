package com.almusand.kawfira.ui.login;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.WebServices.MyFirebaseMessagingService;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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
        return !TextUtils.isEmpty(password);
    }
    public void onClickLogin(String mobile, String password) {

        model = null;
        setIsLoading(true);
        RetroWeb.getClient().create(ServiceApi.class).onLogin(mobile, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if(response.isSuccessful()) {
                        Log.e("response",response.toString());
                        model = response.body();
                        setIsLoading(false);
                        model.getResponse().setToken(model.getAccess_token());
                        userLoginModelMutableLiveData.setValue(model.getResponse());
                        User user = model.getResponse();
                        getToken(model.getAccess_token());
                        if(user.getVerified().equals("1")) {
                            if(user.getRole().equals("client")) {
                                getNavigator().openMainActivity(model);
                            }else{
                                if(user.getStatus().equals("incomplete")){
                                    getNavigator().openVerifyIdForKwafira(model);
                                }else if(user.getStatus().equals("under_review")){
                                    getNavigator().openUnderReviewActivity(model);
                                }else if(user.getStatus().equals("accepted")){
                                    getNavigator().openMainActivityForKwafira(model);
                                }
                            }
                        }else{
                            //TODO to verification
                            getNavigator().openVerifyActivity(model.getResponse());
                        }
                    }else{
                        try {
                            Log.e("error",response.errorBody().string());
                            getNavigator().wrongInfo();
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

    private void getToken(String auth) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        MyFirebaseMessagingService.updateUser(auth,token);
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
    public void onServerRegisterAsKwafiraClick() {
        getNavigator().registerKwafira();
    }

}
