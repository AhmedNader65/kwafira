package com.almusand.kawfira.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SessionModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("order_id")
    @Expose
    private int order_id;

    @SerializedName("service_id")
    @Expose
    private int service_id;

    @SerializedName("duration")
    @Expose
    private int duration;


    @SerializedName("price")
    @Expose
    private String price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
