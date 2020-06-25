package com.almusand.kawfira.ui.serviceDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityServiceDetailsBinding;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceDetailsActivity extends BaseActivity<ActivityServiceDetailsBinding, ServiceDetailsViewModel> implements ServiceDetailsNavigator {

    ActivityServiceDetailsBinding binding;
    ServiceDetailsViewModel viewModel;
    ServicesModel service;
    GlobalPreferences pf;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_details;
    }

    @Override
    public ServiceDetailsViewModel getViewModel() {
       viewModel = ViewModelProviders.of(this).get(ServiceDetailsViewModel.class);
       return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        pf = new GlobalPreferences(this);
        service = (ServicesModel) getIntent().getSerializableExtra("service");
        binding.Desc.setText(service.getDescription_ar());
        binding.title.setText(service.getTitle_ar());
        binding.initprice.setText(service.getInitial_price()+" ريال");
        Picasso.get().load(service.getImage()).into(binding.sliderImg);
        viewModel.setNavigator(this);

        viewModel.initCart(pf,service.getId());
        viewModel.getCartNumber().observe(this,cartObserver);
        viewModel.getCartFromPf(pf);
    }
    Observer<Integer> cartObserver = cartCount -> {
        viewModel.shouldShow(cartCount);
    };
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ServiceDetailsActivity.class);
        return intent;
    }

    @Override
    public void onOrderService() {
        binding.button.setBackgroundResource(R.drawable.rounded_solid_accent_delete_order);
        binding.button.setText(" ×  احذف");
        pf.increaseCartCounter(service.getInitial_price());
        viewModel.getCartFromPf(pf);
        pf.saveInCart(service.getId());
    }

    @Override
    public void onCancelService() {
        binding.button.setBackgroundResource(R.drawable.rounded_solid_prim);
        binding.button.setText(" +  اطلب");
        pf.decreaseCartCounter(service.getInitial_price());
        viewModel.getCartFromPf(pf);
        pf.removeFromCart(service.getId());
    }

    @Override
    public void showCart() {
        binding.cart.setVisibility(View.VISIBLE);
        binding.cartPrice.setText(pf.getCost()+" ريال");
        binding.count.setText(pf.getCartCounter()+"");
    }

    @Override
    public void hideCart() {
        binding.cart.setVisibility(View.INVISIBLE);
    }

    @Override
    public void inCart() {
        binding.button.setBackgroundResource(R.drawable.rounded_solid_accent_delete_order);
        binding.button.setText(" ×  احذف");
    }
}