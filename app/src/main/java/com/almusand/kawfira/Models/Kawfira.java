package com.almusand.kawfira.Models;

import android.os.Parcel;
import android.os.Parcelable;
public class Kawfira implements Parcelable {

    private String name;
    private String mobile;
    private String email;
    private String  type;


    public Kawfira(String name, String mobile, String email, String type) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    protected Kawfira(Parcel in) {
        name = in.readString();
        mobile = in.readString();
        email = in.readString();
        type = in.readString();
    }

    public static final Creator<Kawfira> CREATOR = new Creator<Kawfira>() {
        @Override
        public Kawfira createFromParcel(Parcel in) {
            return new Kawfira(in);
        }

        @Override
        public Kawfira[] newArray(int size) {
            return new Kawfira[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(type);
    }
}
