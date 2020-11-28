package com.almusand.kawfira.ui.main.ui.send;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mStatus = new MutableLiveData<>();;

    public LiveData<String> getText() {
        return mStatus;
    }
    public void contactUs(String userAuth, String content ) {
        RetroWeb.getClient().create(ServiceApi.class).contactUs("Bearer "+userAuth,
                content).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {


                mStatus.setValue("Done");
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                mStatus = new MutableLiveData<>();
                mStatus.setValue("no");

            }

        });
    }
}