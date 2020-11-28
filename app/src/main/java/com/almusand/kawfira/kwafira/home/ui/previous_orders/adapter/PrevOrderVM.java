package com.almusand.kawfira.kwafira.home.ui.previous_orders.adapter;

import android.text.format.DateFormat;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrevOrderVM extends BaseViewModel<Navigator> {

    public final ObservableField<String> name;
    public final ObservableField<String> date;
    public final ObservableField<String> time;
    public final ObservableField<String> price;


    private final OrderModel model;

    public PrevOrderVM(String lang,OrderModel model) {
        this.model = model;
        String n;
        if(lang.equals("ar")) {
             n = "حجز #" + model.getId();
            time = new ObservableField<>("الساعة " + getTimeFormatted(lang,model.getUpdated_at().replace("T"," ")));
        }else{

            n = "Order #" + model.getId();
            time = new ObservableField<>("at " + getTimeFormatted(lang,model.getUpdated_at().replace("T"," ")));
        }
        name = new ObservableField<>(n);
        price = new ObservableField<>(model.getPrice());
        date = new ObservableField<>(getDateFormatted(lang,model.getUpdated_at().substring(0,19).replace("T"," ")));
    }

    public String getDateFormatted(String lang,String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date1); // Thursday
            String month = (String) DateFormat.format("MMM", date1); // Thursday
            String day = (String) DateFormat.format("dd", date1); // Thursday
            if(lang.equals("en")) {
                return dayOfTheWeek + " " + day + " " + (month);
            }else {
                return CommonUtils.getDateInAr(dayOfTheWeek) + " " + day + " " + CommonUtils.getMonthInAr(month);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return model.getUpdated_at();
    }

    public String getTimeFormatted(String lang,String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String timeOfDay = (String) DateFormat.format("hh:mm", date1); // Thursday
            String AMOrPM = (String) DateFormat.format("a", date1); // Thursday
            return timeOfDay + " " + CommonUtils.getAMORPMInAR(lang,AMOrPM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return model.getUpdated_at();
    }

    public void cancelOrder(String auth,String id) {
        Log.e("\"Bearer \" + auth",  auth);
        RetroWeb.getClient().create(ServiceApi.class).onCancelReservation(id,"Bearer " + auth).enqueue(new Callback<MsgModel>() {
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
