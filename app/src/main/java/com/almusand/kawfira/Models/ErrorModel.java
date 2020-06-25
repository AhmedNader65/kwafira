package com.almusand.kawfira.Models;

import com.almusand.kawfira.Models.Login.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ErrorModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private ArrayList<ArrayList<String>> error;

    public ArrayList<ArrayList<String>> getError() {
        return error;
    }

    public int getCode() {
        return code;
    }

}
