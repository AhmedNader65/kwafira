package com.almusand.kawfira.Models.reservations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReservationModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("services")
    @Expose
    private String[] services;

    @SerializedName("date")
    @Expose
    private String date;


    @SerializedName("client_id")
    @Expose
    private String client_id;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("work_method")
    @Expose
    private String work_method;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
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
