package com.almusand.kawfira.ui.main.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.categories.CategoriesModel;
import com.almusand.kawfira.Models.categories.CategoriesResponseModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.Models.offers.OffersModel;
import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private MutableLiveData<List<SliderModel>> offersLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoriesModel>> catLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ServicesModel>> servicesLiveData = new MutableLiveData<>();
    public LiveData<List<SliderModel>> getOffers() {
        return offersLiveData;
    }

      public MutableLiveData<List<CategoriesModel>> getCategories() {
        return catLiveData;
    }

      public MutableLiveData<List<ServicesModel>> getServices() {
        return servicesLiveData;
    }

    public void initOffers(String auth){
        RetroWeb.getClient().create(ServiceApi.class).onGetOffers("Bearer "+auth).enqueue(new Callback<OffersModel>() {
            @Override
            public void onResponse(Call<OffersModel> call, Response<OffersModel> response) {
                Log.e("response", "response.toString()");
                if (response.isSuccessful()) {
                    OffersModel model = response.body();
                    Log.e("loggg",model.getOffer().get(0).getDescription_ar());
                    List<SliderModel> modelList = model.getOffer();
                    offersLiveData.setValue(modelList);
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
            public void onFailure(Call<OffersModel> call, Throwable t) {
                Log.e("response", ""+call.toString());

                setIsLoading(false);

            }

        });
    }
    public void initCategories(){
        RetroWeb.getClient().create(ServiceApi.class).onGetCategories().enqueue(new Callback<CategoriesResponseModel>() {
            @Override
            public void onResponse(Call<CategoriesResponseModel> call, Response<CategoriesResponseModel> response) {
                if (response.isSuccessful()) {
                    CategoriesResponseModel model = response.body();
                    List<CategoriesModel> modelList = model.getCategories();
                    Log.e("cat","here to set value");
                    catLiveData.setValue(modelList);
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
            public void onFailure(Call<CategoriesResponseModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }
    public void initServices(){
        RetroWeb.getClient().create(ServiceApi.class).onGetServices().enqueue(new Callback<ServicesResponseModel>() {
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

    public void showAll(){
        getNavigator().onShowAllServices();
    }
    public void openMaps(){
        getNavigator().openMapsActivity();
    }
    public void showAllCategories(){
        getNavigator().onShowAllCategories();
    }

}