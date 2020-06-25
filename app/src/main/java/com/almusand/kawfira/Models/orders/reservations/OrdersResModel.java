package com.almusand.kawfira.Models.orders.reservations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrdersResModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("orders")
    @Expose
    private List<OrderModel> orders;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }


    public List<OrderModel>  getOrders() {
        return orders;
    }
}
