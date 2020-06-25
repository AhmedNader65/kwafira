package com.almusand.kawfira.ui.main.ui.appointments.adapter;

import android.text.format.DateFormat;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentViewModel extends BaseViewModel<Navigator> {

    public final ObservableField<String> name;
    public final ObservableField<String> date;
    public final ObservableField<String> time;


    private final ReservationModel model;

    public AppointmentViewModel(ReservationModel model) {
        this.model = model;
        String n = "حجز #"+model.getId();
        name = new ObservableField<>(n);
        date = new ObservableField<>(getDateFormatted(model.getDate()));
        time = new ObservableField<>("الساعة " + getTimeFormatted(model.getDate()));
    }

    public String getDateFormatted(String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date1); // Thursday
            String month = (String) DateFormat.format("MMM", date1); // Thursday
            String day = (String) DateFormat.format("dd", date1); // Thursday
            return CommonUtils.getDateInAr(dayOfTheWeek) + " " + day + " " + CommonUtils.getMonthInAr(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return model.getDate();
    }

    public String getTimeFormatted(String date) {
        try {
            Date date1 = CommonUtils.stringToDate(date);
            String timeOfDay = (String) DateFormat.format("hh:mm", date1); // Thursday
            String AMOrPM = (String) DateFormat.format("a", date1); // Thursday
            return timeOfDay + " " + CommonUtils.getAMORPMInAR(AMOrPM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return model.getDate();
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
