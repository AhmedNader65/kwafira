package com.almusand.kawfira.ui.main.ui.orders;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.Models.reservations.ReservationResModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersViewModel extends BaseViewModel {
    private MutableLiveData<List<OrderModel>> resLiveData = new MutableLiveData<>();
    public MutableLiveData<List<OrderModel>> getResLiveData() {
        return resLiveData;
    }


    public void initOrders(String auth,String status){
        RetroWeb.getClient().create(ServiceApi.class).onGetOrders(status,"Bearer "+auth).enqueue(new Callback<OrdersResModel>() {
            @Override
            public void onResponse(Call<OrdersResModel> call, Response<OrdersResModel> response) {
                if (response.isSuccessful()) {
                    OrdersResModel model = response.body();
                    List<OrderModel> modelList = model.getOrders();
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
            public void onFailure(Call<OrdersResModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }
}