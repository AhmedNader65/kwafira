package com.almusand.kawfira.ui.main.ui.orders.adapter;

import android.text.format.DateFormat;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.CommonUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends BaseViewModel<Navigator> {

    public final ObservableField<String> orderNum;
    public final ObservableField<String> kwafiraName;
    public final ObservableField<String> status;
    public final ObservableField<String> price;
    public final ObservableField<String> date;
    public final ObservableField<String> time;
    public final ObservableField<String> firstService;
    public final ObservableField<String> rate;


    private final OrderModel model;

    public OrderViewModel(OrderModel model) {
        this.model = model;
        String n = "رقم الطلب   "+model.getId();
        orderNum = new ObservableField<>(n);
        kwafiraName = new ObservableField<>(model.getKwafera()!=null?model.getKwafera().getName():"جاري البحث عن كوافيرة");
        rate = new ObservableField<>(model.getKwafera()!=null?model.getKwafera().getOverall_rate()==null?"لا يوجد تقييم":getRate(model.getKwafera().getOverall_rate()):"");
        status = new ObservableField<>(model.getStatus());
        firstService = new ObservableField<>(model.getServices_data()[0].getTitle_ar());
        price = new ObservableField<>(model.getPrice());
        date = new ObservableField<>(getDateFormatted(model.getUpdated_at().substring(0,19).replace("T"," ")));
        time = new ObservableField<>("الساعة " + getTimeFormatted(model.getUpdated_at().replace("T"," ")));
    }

    private String getRate(String overall_rate) {
        Log.e("rate",overall_rate);
        float rate = Float.valueOf(overall_rate);
        if(rate>4){
            return "ممتاز";
        }else if(rate>3){
            return "جيد جداً";
        }else if(rate >2){
            return "جيد";
        }else{
            return "مقبول";
        }
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
        return date;
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
        return date;
    }

    public void showCancel(){
        getNavigator().showCancelDialog();
    }

}
