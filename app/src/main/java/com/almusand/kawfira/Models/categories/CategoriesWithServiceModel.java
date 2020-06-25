package com.almusand.kawfira.Models.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoriesWithServiceModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("active")
    @Expose
    private boolean active;

    @SerializedName("image")
    @Expose
    private String image;


    @SerializedName("name_ar")
    @Expose
    private String name_ar;

    @SerializedName("name_en")
    @Expose
    private String name_en;


    @SerializedName("services")
    @Expose
    private ArrayList<ServicesModel> services;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public ArrayList<ServicesModel> getServices() {
        return services;
    }

    public void setServices(ArrayList<ServicesModel> services) {
        this.services = services;
    }
}
