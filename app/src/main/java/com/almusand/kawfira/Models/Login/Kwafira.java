package com.almusand.kawfira.Models.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Kwafira implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("balance")
    @Expose
    private String balance;


    @SerializedName("verified")
    @Expose
    private String verified;



    @SerializedName("available")
    @Expose
    private String available;


    @SerializedName("firebase_id")
    @Expose
    private String firebase_id;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("national_id")
    @Expose
    private String national_id;

    @SerializedName("national_id_image")
    @Expose
    private String national_id_image;

    @SerializedName("certificate_image")
    @Expose
    private String certificate_image;

    @SerializedName("verification_created_at")
    @Expose
    private String verification_created_at;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("overall_rate")
    @Expose
    private String overall_rate;


    @SerializedName("services")
    @Expose
    private ArrayList<String> services;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getBalance() {
        return balance;
    }


    public String getVerified() {
        return verified;
    }

    public String getAvailable() {
        return available;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public String getLanguage() {
        return language;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOverall_rate() {
        return overall_rate;
    }
}
