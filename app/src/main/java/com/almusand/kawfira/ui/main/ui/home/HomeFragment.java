package com.almusand.kawfira.ui.main.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.categories.CategoriesModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentHomeBinding;
import com.almusand.kawfira.ui.allServices.ServicesActivity;
import com.almusand.kawfira.ui.counterActivity.CounterActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.home.adapter.CategoriesAdapter;
import com.almusand.kawfira.ui.main.ui.home.adapter.ServicesAdapter;
import com.almusand.kawfira.ui.main.ui.home.adapter.SliderAdapter;
import com.almusand.kawfira.ui.main.ui.orders.CurrentOrdersNavigator;
import com.almusand.kawfira.ui.main.ui.orders.OrdersViewModel;
import com.almusand.kawfira.ui.map.MapActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.google.gson.Gson;

import java.util.List;


public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel>  implements ServicesAdapter.onCartListener,HomeNavigator, CurrentOrdersNavigator {
    List<ServicesModel> servicesModels;
    List<CategoriesModel> categoriesModels;
    HomeViewModel homeViewModel;
    GlobalPreferences pf;
    FragmentHomeBinding binding;
    OrdersViewModel ordersViewModel;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel.class);
        return homeViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        homeViewModel.setNavigator(this);
        ordersViewModel =  ViewModelProviders.of(requireActivity()).get(OrdersViewModel.class);
        ordersViewModel.setNavigator(this);
        pf = new GlobalPreferences(getContext());
        homeViewModel.getOffers().observe(this, offerListUpdateObserver);
        homeViewModel.getCategories().observe(this, categoriesListUpdateObserver);
        homeViewModel.getServices().observe(this, servicesListUpdateObserver);
        homeViewModel.initOffers(pf.getUserAuth());
        homeViewModel.initCategories();
        homeViewModel.initServices();
        Log.e("HOME","HOMEEE");
        ordersViewModel.initOrders(new GlobalPreferences(getContext()).getUserAuth(), "incomplete");
        try {
            ordersViewModel.getResLiveData().observe(getViewLifecycleOwner(), resListUpdateObserver);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    Observer<List<OrderModel>> resListUpdateObserver = resList -> {
        Log.e("LOGGG",resList.size()+"");
    };


    Observer<List<SliderModel>> offerListUpdateObserver = offersList -> {
        SliderAdapter adapter = new SliderAdapter();

        binding.viewPager.setAdapter(adapter);
        adapter.renewItems(offersList);
        SliderModel model =new SliderModel();
        binding.emptyOffers.setVisibility(View.GONE);
    };
    Observer<List<CategoriesModel>> categoriesListUpdateObserver = categoriesList -> {
        categoriesModels = categoriesList;
        Log.e("changed","categories");
        final CategoriesAdapter adapter = new CategoriesAdapter();
        binding.categories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        binding.emptyCategories.setVisibility(View.GONE);

        binding.categories.setAdapter(adapter);
        adapter.renewItems(categoriesList);

    };
    private ServicesAdapter servicesAdapter;
    Observer<List<ServicesModel>> servicesListUpdateObserver = servicesList -> {
        servicesModels = servicesList;
         servicesAdapter = new ServicesAdapter(this);
        binding.services.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        binding.services.setAdapter(servicesAdapter);
        servicesAdapter.renewItems(servicesList);

        binding.emptyServices.setVisibility(View.GONE);
    };

    @Override
    public void showCart() {

        binding.cart.setVisibility(View.VISIBLE);
        binding.cartPrice.setText(pf.getCost()+getString(R.string.riyyal));
        binding.count.setText(pf.getCartCounter()+"");
    }

    @Override
    public void hideCart() {
        binding.cart.setVisibility(View.GONE);

    }

    public void refreshCart() {
        Log.e("Log","Refreshing");
        servicesAdapter.refreshCart();
    }

    @Override
    public void onShowAllServices() {
        String listSerializedToJson = new Gson().toJson(servicesModels);
        Intent intent = ServicesActivity.newIntent(getContext());
        intent.putExtra("type", "allServices");
        intent.putExtra("services", listSerializedToJson);

        (getActivity()).startActivityForResult(intent, HomeActivity.OPENALLSERVICE_CODE);
    }

    @Override
    public void onShowAllCategories() {
        String listSerializedToJson = new Gson().toJson(categoriesModels);
        Intent intent = ServicesActivity.newIntent(getContext());
        intent.putExtra("type", "allCategories");
        intent.putExtra("categories", listSerializedToJson);

        (getActivity()).startActivityForResult(intent, HomeActivity.OPENALLSERVICE_CODE);
    }

    @Override
    public void openMapsActivity() {
        Intent intent = MapActivity.newIntent(getContext());
            startActivity(intent);
    }

    @Override
    public void showStatusFragment() {
        Intent intent = MapActivity.newIntent(getContext()).putExtra("showStatus",1);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void openPayment(OrderModel orderModel) {

        Intent intent = new Intent(getContext(), CounterActivity.class)
                .putExtra("type","paymentFromStart")
                .putExtra("order",orderModel);
        startActivity(intent);
        getActivity().finish();
    }
}