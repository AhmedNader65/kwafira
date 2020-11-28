package com.almusand.kawfira.ui.main.ui.about;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.AboutUsModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsViewModel extends BaseViewModel {

    public MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
    }

    public MutableLiveData<String> getText() {
        return mText;
    }


    public void getAbout(String lang){
        RetroWeb.getClient().create(ServiceApi.class).aboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                if (response.isSuccessful()) {
                    if(lang.equals("en")){
                        mText.setValue(response.body().getText_en());
                    }else{

                        mText.setValue(response.body().getText_ar());
                    }

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
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }

}