package com.almusand.kawfira.kwafira.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.kwafira.home.ui.home.status.StatusNavigator;

import java.io.IOException;
import java.util.List;

import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityViewModel extends BaseViewModel<StatusNavigator> {
    private MutableLiveData<User> userData = new MutableLiveData<>();
    private MutableLiveData<List<OrderModel>> resLiveData = new MutableLiveData<>();
    private MutableLiveData<List<OrderModel>> acceptedOrder = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAvailable = new MutableLiveData<>();


    public MutableLiveData<List<OrderModel>> getResLiveData() {
        return resLiveData;
    }
    public MutableLiveData<List<OrderModel>> getAcceptedOrder() {
        return acceptedOrder;
    }

    public MutableLiveData<User> getUserData() {
        return userData;
    }



    public MutableLiveData<Boolean> isAvailable() {
        return isAvailable;
    }


    public void setIsAvailable(boolean isAvailable, String userAuth) {

        updateUser(userAuth, isAvailable);

    }

    boolean checkedForORders= false;

    public void checkChanged(boolean switchState,boolean currentState ) {
        checkedForORders = false;
        if(currentState == switchState){
            getNavigator().dontUpdate();
            try{
            getNavigator2().dontUpdate();
        }catch (Exception e){}
        }else{
            getNavigator().update(switchState);
            try {
                getNavigator2().update(switchState);
            }catch (Exception e){}
        }

    }

    public void getUser(String auth) {
        RetroWeb.getClient().create(ServiceApi.class).onGetUser("Bearer " + auth).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.e("response", "response.toString()");
                if (response.isSuccessful()) {
                    LoginModel model = response.body();
                    User user = model.getResponse();
                    userData.setValue(user);
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
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("response", "" + call.toString());

                setIsLoading(false);

            }

        });
    }


    private void updateUser(String auth, boolean available) {
        if(available==true&&checkedForORders==false){
            if (acceptedOrder.getValue()!=null) {
                if (acceptedOrder.getValue().size()>0) {
                    checkedForORders = true;
                    getNavigator().revertAndShowToast("لا يمكنك تغيير حالتك الى متاح إلا بعد الانتهاء من الطلب الحالي");
                    return;
                }
            }
        }else if(checkedForORders==true&&available==true){
            getNavigator2().revertAndShowToast("لا يمكنك تغيير حالتك الى متاح إلا بعد الانتهاء من الطلب الحالي");
            return;
        }
        RetroWeb.getClient().create(ServiceApi.class).updateUserStatus("Bearer " + auth
                , available ? 1 : 0).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel model = response.body();
                    isAvailable.setValue(available);
                    setIsLoading(false);
                    getNavigator().updatedSuccessfuly(model.getResponse());
                    try{
                    getNavigator2().updatedSuccessfuly(model.getResponse());
                }catch (Exception e){}
                } else {

                    final Buffer buffer = new Buffer();

                    try {
                        call.request().body().writeTo(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getNavigator().revertAndShowToast("Try again");
                    try{
                    getNavigator2().revertAndShowToast("Try again"); }catch (Exception e){}

                    try {
                        Log.e("error reviews", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                setIsLoading(false);

            }

        });
    }
    public void initOrders(String auth){
        RetroWeb.getClient().create(ServiceApi.class).onGetKwafiraOrders("Bearer "+auth).enqueue(new Callback<OrdersResModel>() {
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
    public void getCurrentOrder(String auth,String status){
        RetroWeb.getClient().create(ServiceApi.class).onGetOrders(status,"Bearer "+auth).enqueue(new Callback<OrdersResModel>() {
            @Override
            public void onResponse(Call<OrdersResModel> call, Response<OrdersResModel> response) {
                if (response.isSuccessful()) {
                    OrdersResModel model = response.body();
                    List<OrderModel> modelList = model.getOrders();
                    acceptedOrder.setValue(modelList);
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
    public void logout(String auth) {
        RetroWeb.getClient().create(ServiceApi.class).logout("Bearer "+auth).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if(response.isSuccessful()) {
                    Log.e("response",response.toString());
                    MsgModel model = response.body();
                    getNavigator().successfullyLogout();
                }else{
                    try {
                        Log.e("error",response.errorBody().string());
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
