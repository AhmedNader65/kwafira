package com.almusand.kawfira.Models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

public class MsgModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("session")
    @Expose
    private SessionModel session;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public SessionModel getSession() {
        return session;
    }
}
