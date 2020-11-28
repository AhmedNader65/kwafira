package com.almusand.kawfira.ui.allServices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.categories.CategoriesModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityAllServicesBinding;
import com.almusand.kawfira.ui.main.ui.home.adapter.CategoriesAdapter;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicesActivity extends BaseActivity<ActivityAllServicesBinding, ServicesViewModel> implements ServicesNavigator, ServicesAdapter.onCartListener {

    ActivityAllServicesBinding binding;
    ServicesViewModel viewModel;
    List<ServicesModel> services;
    List<CategoriesModel> categoriesModels;
    GlobalPreferences pf;
    private ServicesAdapter servicesAdapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_services;
    }

    @Override
    public ServicesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ServicesViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        pf = new GlobalPreferences(this);
        String type = getIntent().getStringExtra("type");
        viewModel.setNavigator(this);
        viewModel.fromWhich(type);

    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ServicesActivity.class);
        return intent;
    }
//
//    @Override
//    public void onOrderService() {
//
//    }
//
//    @Override
//    public void onCancelService() {
//
//    }

    @Override
    public void showCart() {


        binding.cart.setVisibility(View.VISIBLE);
        binding.cartPrice.setText(pf.getCost() + " ريال");
        binding.count.setText(pf.getCartCounter() + "");
    }

    @Override
    public void hideCart() {
        binding.cart.setVisibility(View.GONE);
    }

    @Override
    public void AllServices() {

        try {
            String listSerializedToJson = getIntent().getExtras().getString("services");
            services = new ArrayList<>(Arrays.asList(new Gson().fromJson(listSerializedToJson, ServicesModel[].class)));

            servicesAdapter = new ServicesAdapter(this);
            binding.services.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            binding.services.setAdapter(servicesAdapter);
            servicesAdapter.renewItems(services);
            viewModel.setTitle("أحدث الخدمات");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("services", "error");
        }
    }

    @Override
    public void serviceOfCategory() {
        viewModel.getServiceOfCat().observe(this, servicesListUpdateObserver);
        viewModel.initServices(getIntent().getIntExtra("id", 1));
        viewModel.setTitle(getIntent().getStringExtra("title"));

    }

    @Override
    public void AllCategories() {

        try {
            String listSerializedToJson = getIntent().getExtras().getString("categories");
            categoriesModels = new ArrayList<>(Arrays.asList(new Gson().fromJson(listSerializedToJson, CategoriesModel[].class)));

            CategoriesAdapter categoriesAdapter = new CategoriesAdapter();

            binding.services.setLayoutManager(new
                    GridLayoutManager(this,
                    3,
                    RecyclerView.VERTICAL,
                    false)
            );
            binding.services.setAdapter(categoriesAdapter);
            categoriesAdapter.renewItems(categoriesModels);
            viewModel.setTitle("أحدث الخدمات");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("services", "error");
        }
    }

    private List<ServicesModel> modelList;
    Observer<List<ServicesModel>> servicesListUpdateObserver = servicesList -> {
        modelList = servicesList;
        servicesAdapter = new ServicesAdapter(this);
        binding.services.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.services.setAdapter(servicesAdapter);
        servicesAdapter.renewItems(servicesList);
    };

//    @Override
//    public void inCart() {
//
//    }

}