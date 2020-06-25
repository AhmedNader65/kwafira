package com.almusand.kawfira.ui.main.ui.orders.adapter;

import android.util.Log;

import androidx.databinding.ObservableField;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends BaseViewModel<Navigator> {

    public final ObservableField<String> services;
    public final ObservableField<String> status;
    public final ObservableField<String> price;


    private final OrderModel model;

    public OrderViewModel(OrderModel model) {
        this.model = model;
        String n = "'طلب ' #"+model.getId();
        services = new ObservableField<>(n);
        status = new ObservableField<>(model.getStatus());
        price = new ObservableField<>(model.getPrice());
    }

    public void cancelOrder(String auth,String id) {
        RetroWeb.getClient().create(ServiceApi.class).onCancelOrder(id,"Bearer " + auth).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                Log.e("response", "response.toString()");
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    getNavigator().refresh();
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
                Log.e("response", "" + call.toString());

            }

        });
    }

}
