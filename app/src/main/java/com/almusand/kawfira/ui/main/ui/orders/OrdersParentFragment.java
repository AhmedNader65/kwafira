package com.almusand.kawfira.ui.main.ui.orders;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentOrdersBinding;
import com.google.android.material.tabs.TabLayoutMediator;


public class OrdersParentFragment extends BaseFragment<FragmentOrdersBinding, OrdersViewModel> {
    private FragmentOrdersBinding mFragmentBinding;
    private OrdersViewModel ordersViewModel;

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_orders;
    }

    @Override
    public OrdersViewModel getViewModel() {

        ordersViewModel = ViewModelProviders.of(requireActivity()).get(OrdersViewModel.class);
        return ordersViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mFragmentBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,getContext());
        mFragmentBinding.viewPager.setAdapter(sectionsPagerAdapter);
        new TabLayoutMediator(mFragmentBinding.tabs, mFragmentBinding.viewPager,
                (tab, position) -> {
                    if(position==0) {
                        tab.setText(R.string.tab_text_1);
                    }else
                        tab.setText(R.string.tab_text_2);
                }).attach();
    }


}