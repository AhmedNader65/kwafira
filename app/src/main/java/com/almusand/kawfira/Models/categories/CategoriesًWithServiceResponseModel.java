package com.almusand.kawfira.Models.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoriesًWithServiceResponseModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("categories")
    @Expose
    private List<CategoriesModel> categories;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public int getCode() {
        return code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public List<CategoriesModel>  getCategories() {
        return categories;
    }
}
