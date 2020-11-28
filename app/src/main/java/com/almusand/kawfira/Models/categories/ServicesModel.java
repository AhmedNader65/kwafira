package com.almusand.kawfira.Models.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicesModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("price_per_minute")
    @Expose
    private int price_per_minute;

    @SerializedName("initial_price")
    @Expose
    private int initial_price;

    @SerializedName("category_id")
    @Expose
    private int category_id;


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


    @SerializedName("active")
    @Expose
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice_per_minute() {
        return price_per_minute;
    }

    public void setPrice_per_minute(int price_per_minute) {
        this.price_per_minute = price_per_minute;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getDescription_ar() {
        return description_ar;
    }

    public void setDescription_ar(String description_ar) {
        this.description_ar = description_ar;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public int getInitial_price() {
        return initial_price;
    }

    public void setInitial_price(int initial_price) {
        this.initial_price = initial_price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
