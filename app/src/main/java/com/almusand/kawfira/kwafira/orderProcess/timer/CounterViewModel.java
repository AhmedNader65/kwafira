package com.almusand.kawfira.kwafira.orderProcess.timer;

import android.os.Handler;
import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounterViewModel extends BaseViewModel<CounterNavigator> {
    public long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                int hours = minutes/60;
                minutes = minutes % 60;

                getNavigator().updateUI(String.format("%02d:%02d:%02d",hours, minutes, seconds));

                timerHandler.postDelayed(this, 500);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void init(long millis) {
        startTime = millis;
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void checkType(String type) {
        if(type.equals("start")){
            getNavigator().start();
        }else if(type.equals("payment")) {
            getNavigator().payment();
        }else if(type.equals("paymentFromStart")) {
            getNavigator().paymentFromStart();
        }else if(type.equals("confirm")) {
            getNavigator().confirmedPayment();
        }else{
//            pause();
//
                getNavigator().stop(null);
        }
    }

    public void endService(String auth,String orderId,String serviceId) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = df.format(c);
        RetroWeb.getClient().create(ServiceApi.class).stopService(orderId,"Bearer "+auth,serviceId).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    try {

                        MsgModel model = response.body();
                        SessionModel session = model.getSession();
//                        for(SessionModel )
//                        Log.e("session",session.toString());
//                        Log.e("session",model.getMessage());
//                        int duration = session.get("duration").getAsInt();
//                        int price = session.get("price").getAsInt();
                        getNavigator().stop(session);
                        setIsLoading(false);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    }

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
            public void onFailure(Call<MsgModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }



    public void pause() {
        timerHandler.removeCallbacks(timerRunnable);
    }
    public void apply(String coupon) {
        getNavigator().applyCoupon(coupon);
    }

    public void applyCoupon(String userAuth, int orderid,String coupon) {
        RetroWeb.getClient().create(ServiceApi.class).applyOffer(orderid+"","Bearer "+userAuth,coupon).enqueue(new Callback<OrdersResModel>() {
            @Override
            public void onResponse(Call<OrdersResModel> call, Response<OrdersResModel> response) {
                if (response.isSuccessful()) {
                    try {
                        OrdersResModel model = response.body();
                        getNavigator().updatePrice(model.getOrder(),Double.valueOf(model.getOrder().getPrice()) -Double.valueOf(model.getOrder().getDiscount()));
                        setIsLoading(false);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    }

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
    public void payOrder(String userAuth, int orderid,String method) {
        RetroWeb.getClient().create(ServiceApi.class).payOrder(orderid+"","Bearer "+userAuth,method).enqueue(new Callback<OrdersResModel>() {
            @Override
            public void onResponse(Call<OrdersResModel> call, Response<OrdersResModel> response) {
                if (response.isSuccessful()) {
                    try {
                        OrdersResModel model = response.body();
                        getNavigator().paid(model.getOrder());
                        setIsLoading(false);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    }

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
    public void confirmPayment(String userAuth, int orderid) {
        RetroWeb.getClient().create(ServiceApi.class).confirmPayment(orderid+"","Bearer "+userAuth).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    try {
                        MsgModel model = response.body();
                        getNavigator().confirmedPayment();
                        setIsLoading(false);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    }

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
            public void onFailure(Call<MsgModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }
    public void review(String userAuth, String content, int reviewedId, float stars , int orderid) {
        RetroWeb.getClient().create(ServiceApi.class).review("Bearer "+userAuth,content,orderid,reviewedId,stars).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    try {
                        MsgModel model = response.body();
                        getNavigator().rated();
                        setIsLoading(false);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    }

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
            public void onFailure(Call<MsgModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }
}
