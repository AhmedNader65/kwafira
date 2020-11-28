package com.almusand.kawfira.ui.main.ui.editingActivities.name;

import android.net.Uri;
import android.util.Log;

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

public class ChangeNameViewModel extends BaseViewModel<ChangeNameNavigator> {
    public void validateAndSave(Uri uri, String auth, String toString,String filePath) {
        if(toString.length()<2){
            getNavigator().showToast("برجاء ادخال الاسم بشكل صحيح");
        }else{
            try {
                updateUser(uri,auth,toString,filePath);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateUser(Uri uri, String auth, String toString,String filePath) throws URISyntaxException {
        Log.e("String name",toString);
        MultipartBody.Part body;

        if(filePath==null){
            body= null;
        }else{

            File file = new File(filePath);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
             body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }

        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), toString);
        RetroWeb.getClient().create(ServiceApi.class).updateUser("Bearer "+auth
        ,null,null,null,usernameBody,body).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel model = response.body();
                    User user = model.getResponse();
                    Log.e("response >>>>",user.getImage());
                    getNavigator().saveToPref(toString,user.getImage());
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
