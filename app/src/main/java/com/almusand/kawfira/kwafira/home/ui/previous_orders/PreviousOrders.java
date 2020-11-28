package com.almusand.kawfira.kwafira.home.ui.previous_orders;

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
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentAppointmentsBinding;
import com.almusand.kawfira.kwafira.home.ui.previous_orders.adapter.OrdersAdapter;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.List;


public class PreviousOrders extends BaseFragment<FragmentAppointmentsBinding, PreviousOrdersVM> {
    private FragmentAppointmentsBinding mFragmentBinding;
    private PreviousOrdersVM previousOrdersVM;
    GlobalPreferences gp;
    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_appointments;
    }

    @Override
    public PreviousOrdersVM getViewModel() {

        previousOrdersVM = ViewModelProviders.of(requireActivity()).get(PreviousOrdersVM.class);
        return previousOrdersVM;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gp = new GlobalPreferences(getContext());
        mFragmentBinding = getViewDataBinding();
        previousOrdersVM.initAppointments(gp.getUserAuth());
        previousOrdersVM.getResLiveData().observe(getViewLifecycleOwner(),resListUpdateObserver);
        setUp();
    }

    Observer<List<OrderModel>> resListUpdateObserver = resList -> {
        if(resList.size()>0){
            mFragmentBinding.empty.setVisibility(View.GONE);
        }
        OrdersAdapter adapter = new OrdersAdapter(previousOrdersVM);
        mFragmentBinding.appointmentsList.setAdapter(adapter);
        adapter.renewItems(resList);
    };
    private void setUp() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        mFragmentBinding.appointmentsList.setLayoutManager(mLayoutManager);
        mFragmentBinding.appointmentsList.setItemAnimator(new DefaultItemAnimator());
    }


}