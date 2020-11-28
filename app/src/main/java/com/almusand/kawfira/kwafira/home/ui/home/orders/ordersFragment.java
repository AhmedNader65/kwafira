package com.almusand.kawfira.kwafira.home.ui.home.orders;

import android.os.Bundle;
import android.util.Log;
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
import com.almusand.kawfira.databinding.FragmentAvailableKwafiraBinding;
import com.almusand.kawfira.databinding.FragmentOdersViewpagerBinding;
import com.almusand.kawfira.kwafira.home.HomeActivityViewModel;
import com.almusand.kawfira.ui.main.ui.orders.OrdersViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ordersFragment extends BaseFragment<FragmentAvailableKwafiraBinding, HomeActivityViewModel> {


    private HomeActivityViewModel viewModel;
    FragmentAvailableKwafiraBinding binding;

    public static ordersFragment newInstance() {
        ordersFragment fragment = new ordersFragment();
        Log.e("create instanse","created");
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_available_kwafira;
    }

    @Override
    public HomeActivityViewModel getViewModel() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        showLoading();
        Log.e("Loader","showing ") ;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.initOrders(new GlobalPreferences(getContext()).getUserAuth());
        viewModel.getResLiveData().observe(getViewLifecycleOwner(), resListUpdateObserver);
        setUp();
    }

    Observer<List<OrderModel>> resListUpdateObserver = resList -> {

        OrdersAdapter adapter = new OrdersAdapter(viewModel);
        binding.appointmentsList.setAdapter(adapter);
        adapter.renewItems(resList);
        hideLoading();
        Log.e("Loader","Hiding") ;
    };

    private void setUp() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.appointmentsList.setLayoutManager(mLayoutManager);
        binding.appointmentsList.setItemAnimator(new DefaultItemAnimator());
    }
}