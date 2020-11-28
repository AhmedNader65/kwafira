package com.almusand.kawfira.kwafira.home.ui.home.status;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.Login.Kwafira;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.UnavailableFragmentBinding;
import com.almusand.kawfira.kwafira.home.HomeActivityViewModel;
import com.almusand.kawfira.ui.login.LoginActivity;
import com.almusand.kawfira.utils.GlobalPreferences;

public class UnavailableFragment extends BaseFragment<UnavailableFragmentBinding, HomeActivityViewModel> implements StatusNavigator {
    private static final String KWAFIRA_CODE = "KWAFIRA_USER";
    UnavailableFragmentBinding binding;
    private HomeActivityViewModel mViewModel;
    GlobalPreferences gp;
    User user;

    public static UnavailableFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KWAFIRA_CODE,user);
        UnavailableFragment fragment = new UnavailableFragment();
        fragment.setArguments(bundle);
        return fragment;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getArguments().getSerializable(KWAFIRA_CODE);
        user.setAvailable("0");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(getContext());
        mViewModel.setNavigator2(this);
        binding.statusSwwitch.setChecked(user.getAvailable().equals("1"));
        binding.statusSwwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mViewModel.checkChanged(isChecked, user.getAvailable().equals("1"));
        });
        mViewModel.isAvailable().observe(getViewLifecycleOwner(), aBoolean -> {
            binding.statusSwwitch.setChecked(aBoolean);
        });
    }
    @Override
    public void updatedSuccessfuly(User available) {
        hideLoading();
        this.user = available;

    }

    @Override
    public void revertAndShowToast(String msg) {
        hideLoading();
//        binding.statusSwwitch.toggle();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        hideLoading();
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

    @Override
    public void successfullyLogout() {

        startActivity(LoginActivity.newIntent(getContext()));
        gp.storeLogged(false);

        gp.clearSharedPreferences();
        getActivity().finish();
    }
}