package com.almusand.kawfira.kwafira.home.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.UnavailableFragmentBinding;
import com.almusand.kawfira.kwafira.home.HomeActivityViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;

public class UnavailableFragment extends BaseFragment<UnavailableFragmentBinding, HomeActivityViewModel> implements StatusNavigator {
    UnavailableFragmentBinding binding;
    private HomeActivityViewModel mViewModel;
    GlobalPreferences gp;
    User user;

    public static UnavailableFragment newInstance() {
        return new UnavailableFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.unavailable_fragment;
    }

    @Override
    public HomeActivityViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(HomeActivityViewModel.class);
        return mViewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(getContext());
        mViewModel.setNavigator2(this);
        mViewModel.getUserData().observe(getViewLifecycleOwner(), userObserver);
        binding.statusSwwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.e("user.getAvailable()", user.getAvailable());
            mViewModel.checkChanged(isChecked, user.getAvailable().equals("1"));
        });
        mViewModel.isAvailable().observe(getViewLifecycleOwner(), aBoolean -> {
            binding.statusSwwitch.setChecked(aBoolean);
        });
    }

    Observer<User> userObserver = user -> {
        Log.e("user", user.getName());
        this.user = user;
        binding.statusSwwitch.setChecked(user.getAvailable().equals("1"));
    };

    @Override
    public void updatedSuccessfuly(User available) {
        hideLoading();
        this.user = available;
        Log.e("availableaaa",available.getAvailable()+"");

    }

    @Override
    public void revertAndShowToast() {
        hideLoading();
        binding.statusSwwitch.toggle();
    }


    @Override
    public void update(boolean isChecked) {
        showLoading();
        mViewModel.setIsAvailable(isChecked, gp.getUserAuth());

    }

    @Override
    public void dontUpdate() {
        hideLoading();
    }
}