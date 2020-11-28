package com.almusand.kawfira.kwafira.home.ui.home.orders;

import android.text.format.DateFormat;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KwafiraOrderViewModel extends BaseViewModel<Navigator> {
    public final ObservableField<String> orderNum;
    public final ObservableField<String> kwafiraName;
    public final ObservableField<String> status;
    public final ObservableField<String> price;
    public final ObservableField<String> date;
    public final ObservableField<String> time;
    public final ObservableField<String> datetime;
    public final ObservableField<String> firstService;
    public final ObservableField<String> address;


    private final OrderModel model;

    public KwafiraOrderViewModel(String lang, OrderModel model) {
        if (model != null) {
            this.model = model;
            String n;
            kwafiraName = new ObservableField<>(model.getClient().getName());
            address = new ObservableField<>(model.getAddress());
            status = new ObservableField<>(model.getStatus());
            firstService = new ObservableField<>(model.getServices_data()[0].getTitle_ar());
            price = new ObservableField<>(model.getPrice());
            date = new ObservableField<>(getDateFormatted(lang, model.getUpdated_at().substring(0, 19).replace("T", " ")));
            if (lang.equals("en")) {
                n = "Order number   " + model.getId();
                time = new ObservableField<>("at " + getTimeFormatted(lang, model.getUpdated_at().replace("T", " ")));
            } else {
                n = "رقم الطلب   " + model.getId();
                time = new ObservableField<>("الساعة " + getTimeFormatted(lang, model.getUpdated_at().replace("T", " ")));
            }
            orderNum = new ObservableField<>(n);
            datetime = new ObservableField<>(date.get() + "     " + time.get());
        } else {

            this.model = model;
            kwafiraName = new ObservableField<>();
            address = new ObservableField<>();
            status = new ObservableField<>();
            firstService = new ObservableField<>();
            price = new ObservableField<>();
            date = new ObservableField<>();

            time = new ObservableField<>();

            orderNum = new ObservableField<>();
            datetime = new ObservableField<>();
        }
    }


    public void acceptOrder(String auth, String id) {
        RetroWeb.getClient().create(ServiceApi.class).onAcceptOrder(id, "Bearer " + auth).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                Log.e("response", "response.toString()");
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    int code = model.getCode();
                    if (code == 3) {
                        getNavigator().showToast("يجب عليك تسديد اموال الطلبات السابقة أولا");
                    } else if (code == 4) {
                        getNavigator().showToast("لا يمكن قبول طلبات اخرى");
                    } else if (code == 2) {
                        getNavigator().showToast("تم قبول هذا الطلب بواسطة كوافيرة اخرى");
                    } else if (code == 1) {
                        getNavigator().refresh();
                    }
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

    public String getDateFormatted(String lang, String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date1); // Thursday
            String month = (String) DateFormat.format("MMM", date1); // Thursday
            String day = (String) DateFormat.format("dd", date1); // Thursday
            if (lang.equals("en")) {
                return dayOfTheWeek + " " + day + " " + month;
            } else {
                return CommonUtils.getDateInAr(dayOfTheWeek) + " " + day + " " + CommonUtils.getMonthInAr(month);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getTimeFormatted(String lang, String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String timeOfDay = (String) DateFormat.format("hh:mm", date1); // Thursday
            String AMOrPM = (String) DateFormat.format("a", date1); // Thursday
            if (lang.equals("en")) {
                return timeOfDay + " " + (AMOrPM);
            } else {
                return timeOfDay + " " + CommonUtils.getAMORPMInAR(lang, AMOrPM);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void showCancel() {
        getNavigator().showOnMap();
    }
}