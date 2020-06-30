package com.almusand.kawfira.kwafira.identity.idFragment;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
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

public class IdViewModel extends BaseViewModel<IdVerifyNavigator> {
    public void validateAndSave(String auth, String id, String filePath) {
        if(id.length()<2){
            getNavigator().showToast("برجاء ادخال رقم الهوية بشكل صحيح");
        }else{
            if(filePath!=null) {
                try {
                    sendId(auth, id, filePath);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }else{
                getNavigator().showToast("برجاء ادخال صورة الهوية بشكل صحيح");
            }
        }
    }


    private void sendId(String auth, String toString, String filePath) throws URISyntaxException {
        MultipartBody.Part body;

        if(filePath==null){
            body= null;
        }else{

            File file = new File(filePath);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            body =
                    MultipartBody.Part.createFormData("national_id_image", file.getName(), requestFile);
        }

        RequestBody national_id = RequestBody.create(MediaType.parse("text/plain"), toString);
        RetroWeb.getClient().create(ServiceApi.class).onKwafiraUploadId("Bearer "+auth
                ,national_id,body).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    String msg = model.getMessage();
                    Log.e("response >>>>",msg);
                    setIsLoading(false);
                    getNavigator().toNextTab();
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
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }
}