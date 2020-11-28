package com.almusand.kawfira.ui.map.fragments.status;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.chat.ChatActivity;
import com.almusand.kawfira.databinding.KwafiraStatusFragmentBinding;
import com.almusand.kawfira.ui.kwafiraReviewProfile.KwafiraRevProfile;
import com.almusand.kawfira.ui.main.ui.orders.CurrentOrdersNavigator;
import com.almusand.kawfira.ui.main.ui.orders.OrdersViewModel;
import com.almusand.kawfira.ui.main.ui.orders.adapter.OrdersAdapter;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class StatusFragment extends BaseFragment<KwafiraStatusFragmentBinding, OrdersViewModel> implements
        GestureDetector.OnGestureListener, View.OnTouchListener, CurrentOrdersNavigator ,ClientStatusNavigator{
    private static final int CALL_PERMISSION_REQUEST_CODE = 4;
    private OrdersViewModel mStatusViewModel;
    private KwafiraStatusFragmentBinding mFragmentBinding;
    ViewModelProviderFactory factory;
    private GestureDetector gestureDetector;

    OrderModel order;

    private OnStatusFragmentInteractionListener mListener;

    public StatusFragment() {
        // Required empty public constructor
    }

    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentBinding = getViewDataBinding();
        mStatusViewModel.setNavigator(this);
        mStatusViewModel.initOrders(new GlobalPreferences(getContext()).getUserAuth(), "incomplete");
        mStatusViewModel.getResLiveData().observe(getViewLifecycleOwner(), resListUpdateObserver);

        gestureDetector = new GestureDetector(getContext(),this);
        mFragmentBinding.swipeupStatus.setOnTouchListener(this);
        mFragmentBinding.containerKawfiraSelection.setOnClickListener(v -> clickedOnProfile());

        mFragmentBinding.call.setOnClickListener(v -> {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.CALL_PHONE)) {
                EasyPermissions.requestPermissions((Activity) getContext(), getContext().getString(R.string.permission_call_required_toast), CALL_PERMISSION_REQUEST_CODE, Manifest.permission.CALL_PHONE);
            } else {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + order.getKwafera().getPhone()));
                getContext().startActivity(intent);
            }
        });
        mFragmentBinding.chat.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), ChatActivity.class)
                    .putExtra("id",order.getKwafera().getId())
                    .putExtra("name",order.getKwafera().getName())
                    .putExtra("img",order.getKwafera().getImage()));
        });
        //here data must be an instance of the class MarsDataProvider
    }


    Observer<List<OrderModel>> resListUpdateObserver = resList -> {
        if(resList.size()>0) {
            if (resList.get(0).getKwafera() != null) {
                mFragmentBinding.searching.setVisibility(View.GONE);
                mFragmentBinding.containerKawfiraSelection.setVisibility(View.VISIBLE);
                StatusViewModel viewModel = new StatusViewModel(resList.get(0), getContext());
                mFragmentBinding.setViewModel(viewModel);
                this.order = resList.get(0);
            } else {

                mFragmentBinding.searching.setVisibility(View.VISIBLE);
                mFragmentBinding.containerKawfiraSelection.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.kwafira_status_fragment;
    }

    @Override
    public OrdersViewModel getViewModel() {

        mStatusViewModel = ViewModelProviders.of(this,factory).get(OrdersViewModel.class);
        return mStatusViewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStatusFragmentInteractionListener) {
            mListener = (OnStatusFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddressFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void clickedOnProfile() {
            Intent intent = KwafiraRevProfile.newIntent(getContext())
                    .putExtra("Kwafira",order.getKwafera());
            startActivity(intent);
    }

    @Override
    public void showStatusFragment() {

    }

    @Override
    public void openPayment(OrderModel orderModel) {

    }

    @Override
    public void showSearchingForKwafira() {

    }


    public interface OnStatusFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStatusFragmentInteraction();
    }



    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (distanceY > 0){
            // you are going up
            mListener.onStatusFragmentInteraction();
        } else {
            // you are going down
            Toast.makeText(getContext(), "scrolling down", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return gestureDetector.onTouchEvent(event);
    }
}
