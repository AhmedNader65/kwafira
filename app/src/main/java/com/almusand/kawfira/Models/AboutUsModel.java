package com.almusand.kawfira.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AboutUsModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("text_ar")
    @Expose
    private String text_ar;
    @SerializedName("text_en")
    @Expose
    private String text_en;


    public int getCode() {
        return code;
    }

    public String getText_ar() {
        return text_ar;
    }

    public String getText_en() {
        return text_en;
    }
}
