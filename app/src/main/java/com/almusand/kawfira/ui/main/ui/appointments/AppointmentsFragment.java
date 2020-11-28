package com.almusand.kawfira.ui.main.ui.appointments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentAppointmentsBinding;
import com.almusand.kawfira.ui.main.ui.appointments.adapter.AppointmentsAdapter;
import com.almusand.kawfira.ui.main.ui.appointments.schedule.SchedulingFragment;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.List;


public class AppointmentsFragment extends BaseFragment<FragmentAppointmentsBinding,AppointmentsViewModel> implements SchedulingFragment.OnSchedulingFragmentInteractionListener,AppointmentsAdapter.onEditClickedListener {
    private FragmentAppointmentsBinding mFragmentBinding;
    private AppointmentsViewModel appointmentsViewModel;
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
    public AppointmentsViewModel getViewModel() {

        appointmentsViewModel = ViewModelProviders.of(requireActivity()).get(AppointmentsViewModel.class);
        return appointmentsViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gp = new GlobalPreferences(getContext());
        mFragmentBinding = getViewDataBinding();
        appointmentsViewModel.initAppointments(gp.getUserAuth());
        appointmentsViewModel.getResLiveData().observe(getViewLifecycleOwner(),resListUpdateObserver);
        setUp();
    }

    Observer<List<ReservationModel>> resListUpdateObserver = resList -> {
        if(resList.size()>0){
            mFragmentBinding.empty.setVisibility(View.GONE);
        }
        AppointmentsAdapter adapter = new AppointmentsAdapter(appointmentsViewModel,this);
        mFragmentBinding.appointmentsList.setAdapter(adapter);
        adapter.renewItems(resList);
    };
    private void setUp() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        mFragmentBinding.appointmentsList.setLayoutManager(mLayoutManager);
        mFragmentBinding.appointmentsList.setItemAnimator(new DefaultItemAnimator());
    }

    private void showEditFragment(String id){

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.bottom_fragment_container, SchedulingFragment.newInstance(id)).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onEditClick(String id) {
        showEditFragment(id);
    }

    @Override
    public void onSchedulingFragmentInteraction() {
        FragmentManager fm = getChildFragmentManager();
        fm.popBackStack();
        appointmentsViewModel.initAppointments(gp.getUserAuth());
    }
}