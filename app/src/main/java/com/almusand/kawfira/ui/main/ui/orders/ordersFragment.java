package com.almusand.kawfira.ui.main.ui.orders;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentOdersViewpagerBinding;
import com.almusand.kawfira.ui.main.ui.orders.adapter.OrdersAdapter;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ordersFragment extends BaseFragment<FragmentOdersViewpagerBinding, OrdersViewModel> {

    private static final String ARG_STATUS = "status";

    private OrdersViewModel viewModel;
    FragmentOdersViewpagerBinding binding;
    private String status;

    public static ordersFragment newInstance(String status) {
        ordersFragment fragment = new ordersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_STATUS, status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_oders_viewpager;
    }

    @Override
    public OrdersViewModel getViewModel() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        status = getArguments().getString(ARG_STATUS);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.initOrders(new GlobalPreferences(getContext()).getUserAuth(), status);
        viewModel.getResLiveData().observe(getViewLifecycleOwner(), resListUpdateObserver);
        setUp();
    }

    Observer<List<OrderModel>> resListUpdateObserver = resList -> {

        OrdersAdapter adapter = new OrdersAdapter(viewModel,status);
        binding.appointmentsList.setAdapter(adapter);
        adapter.renewItems(resList);
    };

    private void setUp() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.appointmentsList.setLayoutManager(mLayoutManager);
        binding.appointmentsList.setItemAnimator(new DefaultItemAnimator());
    }
}