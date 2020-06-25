package com.almusand.kawfira.ui.counterActivity;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityCounterBinding;

public class CounterActivity extends BaseActivity<ActivityCounterBinding,CounterViewModel> implements CounterNavigator{

    CounterViewModel counterViewModel;
    ActivityCounterBinding mActivityBinding;
    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_counter;
    }

    @Override
    public CounterViewModel getViewModel() {
        counterViewModel = ViewModelProviders.of(this).get(CounterViewModel.class);
        return counterViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding= getViewDataBinding();
        counterViewModel.setNavigator(this);
    }
}
