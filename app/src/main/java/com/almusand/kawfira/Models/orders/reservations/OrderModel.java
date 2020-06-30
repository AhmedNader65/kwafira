package com.almusand.kawfira.Models.orders.reservations;

import com.almusand.kawfira.Models.Login.Kwafira;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
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



    @SerializedName("client_id")
    @Expose
    private int client_id;


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

    @SerializedName("address")
    @Expose
    private String address;


    @SerializedName("appartment_number")
    @Expose
    private String appartment_number;

    @SerializedName("house_number")
    @Expose
    private String house_number;

    @SerializedName("payment_method")
    @Expose
    private String payment_method;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("services_data")
    @Expose
    private ServicesModel[] services_data;

    @SerializedName("kwafera")
    @Expose
    private User kwafera;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

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

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppartment_number() {
        return appartment_number;
    }

    public void setAppartment_number(String appartment_number) {
        this.appartment_number = appartment_number;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public ServicesModel[] getServices_data() {
        return services_data;
    }

    public void setServices_data(ServicesModel[] services_data) {
        this.services_data = services_data;
    }

    public User getKwafera() {
        return kwafera;
    }

    public void setKwafera(User kwafera) {
        this.kwafera = kwafera;
    }


    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
