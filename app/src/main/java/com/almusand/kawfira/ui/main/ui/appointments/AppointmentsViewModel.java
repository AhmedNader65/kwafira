package com.almusand.kawfira.ui.main.ui.appointments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.Models.reservations.ReservationResModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsViewModel extends BaseViewModel {
    private MutableLiveData<List<ReservationModel>> resLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ReservationModel>> getResLiveData() {
        return resLiveData;
    }


    public void initAppointments(String auth){

        Log.e("Auth to get res. >>>",auth+"");
        RetroWeb.getClient().create(ServiceApi.class).onGetReservations("Bearer "+auth).enqueue(new Callback<ReservationResModel>() {
            @Override
            public void onResponse(Call<ReservationResModel> call, Response<ReservationResModel> response) {
                if (response.isSuccessful()) {
                    ReservationResModel model = response.body();
                    List<ReservationModel> modelList = model.getReservations();
                    resLiveData.setValue(modelList);

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
            public void onFailure(Call<ReservationResModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }
}