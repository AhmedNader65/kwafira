package com.almusand.kawfira.ui.allServices;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesViewModel extends BaseViewModel<ServicesNavigator> {
    public final ObservableField<String> title = new ObservableField<String>();
    private MutableLiveData<List<ServicesModel>> servicesLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ServicesModel>> getServiceOfCat() {
        return servicesLiveData;
    }
    public void setTitle(String title) {
        this.title.set(title);

    }

    public void fromWhich(String type) {
        switch (type){
            case "allServices":
                getNavigator().AllServices();
                break;
            case "category":
                getNavigator().serviceOfCategory();
                break;
            case "allCategories":
                getNavigator().AllCategories();
                break;
        }
    }

    public void initServices(int id){
        RetroWeb.getClient().create(ServiceApi.class).onGetServicesOfCat(id+"").enqueue(new Callback<ServicesResponseModel>() {
            @Override
            public void onResponse(Call<ServicesResponseModel> call, Response<ServicesResponseModel> response) {
                if (response.isSuccessful()) {
                    ServicesResponseModel model = response.body();
                    List<ServicesModel> modelList = model.getServices();
                    servicesLiveData.setValue(modelList);

                    setIsLoading(false);

                } else {
                    try {
                        Log.e("error reviews", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ServicesResponseModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }

}
