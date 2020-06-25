package com.almusand.kawfira.ui.kwafiraReviewProfile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KwafiraRevViewModel extends BaseViewModel<KwafiraRevNavigator> {

    private MutableLiveData<ReviewsResponseModel> model = new MutableLiveData<>();
    public MutableLiveData<ReviewsResponseModel>  getData(String userId,String auth) {
        getReviews(userId,auth);
        return model;
    }

    private void getReviews(String userId,String auth) {
        Log.e("autherntication",auth);

        RetroWeb.getClient().create(ServiceApi.class).onGetReviews("8","Bearer "+auth).enqueue(new Callback<ReviewsResponseModel>() {
            @Override
            public void onResponse(Call<ReviewsResponseModel> call, Response<ReviewsResponseModel> response) {
                if (response.isSuccessful()) {
                    Log.e("response", response.toString());
                    model.setValue(response.body());
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
            public void onFailure(Call<ReviewsResponseModel> call, Throwable t) {

                setIsLoading(false);

            }

        });
    }
}
