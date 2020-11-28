package com.almusand.kawfira.kwafira.home.ui.home.bottomsheet;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.ui.map.fragments.schedule.ScheduleNavigator;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetViewModel extends BaseViewModel<BottomSheetNavigator> {


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

    public BottomSheetViewModel(OrderModel model, Context context) {
        this.model = model;
        String n = context.getString(R.string.orderNum)+model.getId();
        orderNum = new ObservableField<>(n);
        kwafiraName = new ObservableField<>(model.getClient().getName());
        address = new ObservableField<>(model.getAddress());
        status = new ObservableField<>(model.getStatus());
        firstService = new ObservableField<>(model.getServices_data()[0].getTitle_ar());
        price = new ObservableField<>(model.getPrice());
        date = new ObservableField<>(getDateFormatted(new GlobalPreferences(context).getLanguage(),model.getUpdated_at().substring(0,19).replace("T"," ")));
        time = new ObservableField<>(context.getString(R.string.at) + getTimeFormatted(new GlobalPreferences(context).getLanguage(),model.getUpdated_at().replace("T"," ")));
        datetime = new ObservableField<>(date.get()+"     "+time.get());
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

    public String getDateFormatted(String lang,String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date1); // Thursday
            String month = (String) DateFormat.format("MMM", date1); // Thursday
            String day = (String) DateFormat.format("dd", date1); // Thursday
            if(lang.equals("en")) {
                return dayOfTheWeek + " " + day + " " + month;
            }else {
                return CommonUtils.getDateInAr(dayOfTheWeek) + " " + day + " " + CommonUtils.getMonthInAr(month);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getTimeFormatted(String lang,String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String timeOfDay = (String) DateFormat.format("hh:mm", date1); // Thursday
            String AMOrPM = (String) DateFormat.format("a", date1); // Thursday
            if(lang.equals("en")) {
                return timeOfDay + " " + AMOrPM;
            }else{
                return timeOfDay + " " + CommonUtils.getAMORPMInAR(lang,AMOrPM);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void viewOnMap(){
        getNavigator().viewOnMap();
    }
}
