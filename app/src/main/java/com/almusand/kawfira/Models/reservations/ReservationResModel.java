package com.almusand.kawfira.Models.reservations;

import com.almusand.kawfira.Models.offers.SliderModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReservationResModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("reservations")
    @Expose
    private List<ReservationModel> reservations;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }


    public List<ReservationModel>  getReservations() {
        return reservations;
    }
}
