package com.almusand.kawfira.ui.map;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivityViewModel extends BaseViewModel<MapsActivityNavigator> {
    MutableLiveData<User> userLoginModelMutableLiveData = new MutableLiveData<>();
    User model;
    boolean bottomSheet = false;

    public boolean isEmailAndPasswordValid(String num, String password) {
        // validate email and password
        if (TextUtils.isEmpty(num)) {
            return false;
        }
        if (!CommonUtils.isPhoneValid(num)) {
            return false;
        }
        return !TextUtils.isEmpty(password);
    }

    public void toAddress() {

    }

    public void onCallClicked() {

    }

    public void onChatClicked() {

    }

    public void onKwafiraProfileClicked() {

    }

    public void confirmOrSchedule(int i) {
        switch (i) {
            case 0:
                getNavigator().scheduling();
                break;
            case 1:
                getNavigator().confirm();

        }
    }


    public boolean isBottomSheet() {
        return bottomSheet;
    }

    public void setBottomSheet(boolean bottomSheet) {
        this.bottomSheet = bottomSheet;
    }

    public void shouldShow() {
        if (!isBottomSheet()) {
            setBottomSheet(true);
            getNavigator().showBottomSheet();
        }
    }

    public void postOrder(String auth, String lat, String lon, ArrayList<String> services, String address, String homeNum, String apartNum) {
        Log.e("ad apa homeN lon lat", address+">> "+  apartNum+">> "+ homeNum+">> "+ lon+">> "+ lat);
        RetroWeb.getClient().create(ServiceApi.class).PostOrder("Bearer " + auth, address, apartNum, homeNum, lon, lat, services).enqueue(new Callback<OrdersResModel>() {
            @Override
            public void onResponse(Call<OrdersResModel> call, Response<OrdersResModel> response) {
                if (response.isSuccessful()) {
                    OrdersResModel model = response.body();
                    OrderModel order = model.getOrder();
                    Log.e("ORDER << ",order.toString());
                    Log.e("ORDER address<< ",order.getAddress());
                    Log.e("ORDER address<< ",order.getAppartment_number());
//                    servicesLiveData.setValue(modelList);

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
                Log.e("response", "" + t.getCause());
                Log.e("response", "" + t.getMessage());

                setIsLoading(false);

            }

        });
    }

    public void postReservation(String auth, String date, String lat, String lon, ArrayList<String> services, String address, String homeNum, String apartNum) {

        Log.e("Auth to post res. >>>", auth + "");
        RetroWeb.getClient().create(ServiceApi.class).PostReservation("Bearer " + auth, date, lon, lat, address, apartNum, homeNum, services).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    String msg = model.getMessage();
//                    servicesLiveData.setValue(modelList);
                    getNavigator().resDone();
                    setIsLoading(false);
                } else {
                    try {
                        FormBody body = (FormBody) call.request().body();
                        Log.e("error reviews", response.errorBody().string());
                        Log.e("requesttt", call.request().toString());

                        final Buffer buffer = new Buffer();

                        call.request().body().writeTo(buffer);
                        Log.e("requesttt", buffer.readUtf8());

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

    public void logout(String auth) {
        RetroWeb.getClient().create(ServiceApi.class).logout("Bearer " + auth).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    Log.e("response", response.toString());
                    MsgModel model = response.body();
                    getNavigator().successfullyLogout();
                } else {
                    try {
                        Log.e("error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {

                setIsLoading(false);

            }

        });

    }
}
