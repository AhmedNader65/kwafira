package com.almusand.kawfira.Models.Login;

import com.almusand.kawfira.Models.Login.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("user")
    @Expose
    private User user;

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

    public User getResponse() {
        return user;
    }
}
