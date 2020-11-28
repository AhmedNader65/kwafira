package com.almusand.kawfira.kwafira.orderProcess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderMainServicesVM extends BaseViewModel<Navigator> {
    public void startService(String auth,String orderId,String serviceId) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        RetroWeb.getClient().create(ServiceApi.class).startService(orderId,"Bearer "+auth,serviceId,formattedDate).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    String msg = model.getMessage();
                    Log.e("msg",msg);
                    SessionModel session = model.getSession();
                    getNavigator().setSession(session);
                    setIsLoading(false);

                } else {
                    getNavigator().failed();

                    try {

                        final Buffer buffer = new Buffer();

                        call.request().body().writeTo(buffer);
                        Log.e("requesttt", buffer.readUtf8());
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

    public void end(){
        getNavigator().endOrderClicked();
    }
    public void endOrder(String auth,String orderId) {
        RetroWeb.getClient().create(ServiceApi.class).endOrder(orderId,"Bearer "+auth).enqueue(new Callback<OrdersResModel>() {
            @Override
            public void onResponse(Call<OrdersResModel> call, Response<OrdersResModel> response) {
                if (response.isSuccessful()) {
                    OrdersResModel model = response.body();
                    getNavigator().endOrder(model.getOrder());
                    setIsLoading(false);

                } else {

                    try {

                        final Buffer buffer = new Buffer();

                        call.request().body().writeTo(buffer);
                        Log.e("requesttt", buffer.readUtf8());
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


    public void updateService(SessionModel session, OrderModel order) {

        for (SessionModel model :order.getSessions() ) {
            if(model.getId()==session.getId()){
                model.setPrice(session.getPrice());
                model.setDuration(session.getDuration());
            }
        }
        getNavigator().updateAdapter(order);
    }
}