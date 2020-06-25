package com.almusand.kawfira.ui.main.ui.orders;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentOrdersBinding;
import com.almusand.kawfira.ui.main.ui.orders.adapter.OrdersAdapter;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.List;


public class OrdersFragment extends BaseFragment<FragmentOrdersBinding, OrdersViewModel> {
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
        ordersViewModel.initOrders(new GlobalPreferences(getContext()).getUserAuth());
        ordersViewModel.getResLiveData().observe(getViewLifecycleOwner(),resListUpdateObserver);
        setUp();
    }

    Observer<List<OrderModel>> resListUpdateObserver = resList -> {

        OrdersAdapter adapter = new OrdersAdapter(ordersViewModel);
        mFragmentBinding.appointmentsList.setAdapter(adapter);
        adapter.renewItems(resList);
    };
    private void setUp() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        mFragmentBinding.appointmentsList.setLayoutManager(mLayoutManager);
        mFragmentBinding.appointmentsList.setItemAnimator(new DefaultItemAnimator());
    }


}