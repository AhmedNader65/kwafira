package com.almusand.kawfira.Models.orders.reservations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("kwafera_id")
    @Expose
    private int kwafera_id;


    @SerializedName("services")
    @Expose
    private String[] services;

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("work_method")
    @Expose
    private String work_method;

    @SerializedName("offer_applied")
    @Expose
    private String offer_applied;

    @SerializedName("offer_id")
    @Expose
    private String offer_id;

    @SerializedName("assigned_at")
    @Expose
    private String assigned_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public int getKwafera_id() {
        return kwafera_id;
    }

    public void setKwafera_id(int kwafera_id) {
        this.kwafera_id = kwafera_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffer_applied() {
        return offer_applied;
    }

    public void setOffer_applied(String offer_applied) {
        this.offer_applied = offer_applied;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getAssigned_at() {
        return assigned_at;
    }

    public void setAssigned_at(String assigned_at) {
        this.assigned_at = assigned_at;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getWork_method() {
        return work_method;
    }

    public void setWork_method(String work_method) {
        this.work_method = work_method;
    }
}
