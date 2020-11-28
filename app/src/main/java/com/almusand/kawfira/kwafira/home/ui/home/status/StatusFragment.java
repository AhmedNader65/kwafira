package com.almusand.kawfira.kwafira.home.ui.home.status;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentStatusBinding;
import com.almusand.kawfira.kwafira.home.HomeActivityViewModel;
import com.almusand.kawfira.ui.login.LoginActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

public class StatusFragment extends BaseFragment<FragmentStatusBinding, HomeActivityViewModel> implements StatusNavigator {
    HomeActivityViewModel viewModel;
    FragmentStatusBinding binding;
    GlobalPreferences gp;

    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_status;
    }

    @Override
    public HomeActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(requireActivity()).get(HomeActivityViewModel.class);
        return viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(getContext());
        viewModel.setNavigator(this);
        viewModel.getUserData().observe(getViewLifecycleOwner(), userObserver);

        binding.statusSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.checkChanged(isChecked, user.getAvailable().equals("1"));
        });
        viewModel.isAvailable().observe(getViewLifecycleOwner(), aBoolean -> {
            binding.statusSwitch.setChecked(aBoolean);
        });
    }

    private User user;
    Observer<User> userObserver = user -> {
        this.user = user;
        binding.statusSwitch.setChecked(user.getAvailable().equals("1"));
    };

    @Override
    public void updatedSuccessfuly(User available) {
        hideLoading();
        this.user = available;
    }

    @Override
    public void revertAndShowToast(String msg) {
        hideLoading();
        binding.statusSwitch.toggle();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(boolean isChecked) {

        showLoading();
        viewModel.setIsAvailable(isChecked, gp.getUserAuth());
    }

    @Override
    public void dontUpdate() {
        hideLoading();
    }

    @Override
    public void successfullyLogout() {

            startActivity(LoginActivity.newIntent(getContext()));
            gp.storeLogged(false);
            gp.clearSharedPreferences();
            getActivity().finish();
    }
}