package com.almusand.kawfira.kwafira.identity.certFragment;

import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.MsgModel;
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

public class CertViewModel extends BaseViewModel<CertVerifyNavigator> {

    public void validateAndSave(String auth, String filePath) {

        if (filePath != null) {
            try {
                sendId(auth, filePath);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            getNavigator().showToast("برجاء ادخال صورة الشهادة بشكل صحيح");
        }

    }

    private void sendId(String auth, String filePath) throws URISyntaxException {
        MultipartBody.Part body;

        if (filePath == null) {
            body = null;
        } else {

            File file = new File(filePath);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            body =
                    MultipartBody.Part.createFormData("national_id_image", file.getName(), requestFile);
        }

        RetroWeb.getClient().create(ServiceApi.class).onKwafiraUploadCert("Bearer " + auth
                , body).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    String msg = model.getMessage();
                    Log.e("response >>>>", msg);
                    setIsLoading(false);
                    getNavigator().toNextActivity();
                } else {
                    try {
                        Log.e("error reviews", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                Log.e("response", "" + t.getCause());
                Log.e("response", "" + t.getMessage());

                setIsLoading(false);

            }

        });
    }

}