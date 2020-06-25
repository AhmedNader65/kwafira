package com.almusand.kawfira.ui.main.ui.slideshow;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Observable;

public class SlideshowModel extends Observable implements Serializable {
    private MutableLiveData<Integer> positon = new MutableLiveData<>();


    public LiveData<Integer> getPositon() {

        if (positon == null){
            positon = new MutableLiveData<>();
            positon.setValue(0);
        }
        return positon;
    }

    public Integer setPositon(Integer positon) {
        this.positon.setValue(positon);
        return positon;
    }

}
