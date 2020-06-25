package com.almusand.kawfira.ui.kwafiraReviewProfile;

import com.almusand.kawfira.Models.Login.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewsResponseModel implements Serializable {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("reviews")
    @Expose
    private ArrayList<ReviewModel> reviews;

    public int getCode() {
        return code;
    }

    public ArrayList<ReviewModel> getReviews() {
        return reviews;
    }
}