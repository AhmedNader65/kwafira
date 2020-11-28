package com.almusand.kawfira.ui.main.ui.editingActivities.email;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeEmailViewModel extends BaseViewModel<ChangeEmailNavigator> {
    public void validateAndSave(String auth, String email) {
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            try {
                updateUser(auth,email);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            getNavigator().showToast("wrongEmail");
        }
    }


    private void updateUser(String auth, String email) throws URISyntaxException {

        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RetroWeb.getClient().create(ServiceApi.class).updateUser("Bearer "+auth
        ,emailBody,null,null,null,null).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel model = response.body();
                    User user = model.getResponse();
                    Log.e("response >>>>",user.getImage());
                    getNavigator().saveEmailToPref(email);
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
