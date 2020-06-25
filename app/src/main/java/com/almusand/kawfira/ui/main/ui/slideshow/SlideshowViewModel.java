package com.almusand.kawfira.ui.main.ui.slideshow;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.almusand.kawfira.Bases.BaseViewModel;

public class SlideshowViewModel extends BaseViewModel {

    private MutableLiveData<Integer> positon = new MutableLiveData<>();


    public LiveData<Integer> getPositon() {
        Log.e("get Position","true");

        if (positon == null){
            positon = new MutableLiveData<>();
            positon.setValue(0);
        }
        return positon;
    }

    public Integer setPositon(Integer positon) {
        Log.e("changind Position","true");
        this.positon.setValue(positon);
        return positon;
    }
}