package com.almusand.kawfira.ui.kwafiraReviewProfile;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Models.Login.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewModel extends MutableLiveData<ReviewModel> implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("reviewed_id")
    private String reviewed_id;

    @Expose
    @SerializedName("stars")
    private String stars;

    @Expose
    @SerializedName("reviewer_id")
    private String reviewer_id;

    @Expose
    @SerializedName("order_id")
    private String order_id;

    @Expose
    @SerializedName("reviewer")
    private User reviewer;

    @Expose
    @SerializedName("img_url")
    private String img_url;

    @Expose
    @SerializedName("created_at")
    private String created_at;

    @Expose
    @SerializedName("content")
    private String content;

    public String getId() {
        return id;
    }

    public String getReviewed_id() {
        return reviewed_id;
    }

    public String getStars() {
        return stars;
    }

    public String getReviewer_id() {
        return reviewer_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public User getReviewer() {
        return reviewer;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getDate() {
        return created_at;
    }

    public String getContent() {
        return content;
    }
}
