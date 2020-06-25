package com.almusand.kawfira.ui.map.fragments.schedule;

import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulingViewModel extends BaseViewModel<ScheduleNavigator> {
    public String getTimerText(int pos) {
        switch (pos){
            case 1:
                return "1:00";
            case 2:
                return "1:30";
            case 3:
                return "2:00";
            case 4:
                return "2:30";
            case 5:
                return "3:00";
            case 6:
                return "3:30";
            case 7:
                return "4:00";
            case 8:
                return "4:30";
            case 9:
                return "5:00";
            case 10:
                return "5:30";
            case 11:
                return "6:00";
            case 12:
                return "6:30";
            case 13:
                return "7:00";
            case 14:
                return "7:30";
            case 15:
                return "8:00";
            case 16:
                return "8:30";
            case 17:
                return "9:00";
            case 18:
                return "9:30";
            case 19:
                return "10:00";
            case 20:
                return "10:30";
            case 21:
                return "11:00";
            case 22:
                return "11:30";
            case 23:
                return "12:00";
            case 24:
                return "12:30";

        }
        return "";
    }
    public void onConfirmSelection() {
        getNavigator().toConfirmAndShowKawafiraToast();
    }

    public void validateDate(String date,String defDate, String time,String defTime) {
        if(date.equals(defDate)) {
            getNavigator().showDateToast();
        }else if(time.equals(defTime)) {
            getNavigator().showTimeToast();
        }else{
            getNavigator().toConfirmAndShowKawafiraToast();
        }

    }

}
