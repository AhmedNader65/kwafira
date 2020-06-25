package com.almusand.kawfira.Models.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SliderModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("active")
    @Expose
    private int active;

    @SerializedName("coupon")
    @Expose
    private String coupon;


    @SerializedName("description_ar")
    @Expose
    private String description_ar;

    @SerializedName("description_en")
    @Expose
    private String description_en;

    @SerializedName("title_ar")
    @Expose
    private String title_ar;

    @SerializedName("title_en")
    @Expose
    private String title_en;


    @SerializedName("image")
    @Expose
    private String image;


    public int getId() {
        return id;
    }

    public int getActive() {
        return active;
    }

    public String getCoupon() {
        return coupon;
    }

    public String getDescription_ar() {
        return description_ar;
    }

    public String getDescription_en() {
        return description_en;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public void setDescription_ar(String description_ar) {
        this.description_ar = description_ar;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle_ar() {
        return title_ar;
    }

    public void setTitle_ar(String title_ar) {
        this.title_ar = title_ar;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }
}
