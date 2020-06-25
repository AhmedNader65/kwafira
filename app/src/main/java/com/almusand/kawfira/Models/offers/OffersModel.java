package com.almusand.kawfira.Models.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OffersModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("offers")
    @Expose
    private List<SliderModel> offers;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public int getCode() {
        return code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public List<SliderModel>  getOffer() {
        return offers;
    }
}
