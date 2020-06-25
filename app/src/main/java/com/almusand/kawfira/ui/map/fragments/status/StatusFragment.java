package com.almusand.kawfira.ui.map.fragments.status;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.KwafiraStatusFragmentBinding;
import com.almusand.kawfira.ui.kwafiraReviewProfile.KwafiraRevProfile;
public class StatusFragment extends BaseFragment<KwafiraStatusFragmentBinding, StatusViewModel> implements GestureDetector.OnGestureListener, View.OnTouchListener {
    private StatusViewModel mStatusViewModel;
    private KwafiraStatusFragmentBinding mFragmentBinding;
    ViewModelProviderFactory factory;
    private GestureDetector gestureDetector;


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
        gestureDetector = new GestureDetector(getContext(),this);
        mFragmentBinding.swipeupStatus.setOnTouchListener(this);
        mFragmentBinding.containerKawfiraSelection.setOnClickListener(v -> clickedOnProfile());
        //here data must be an instance of the class MarsDataProvider
    }


    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.kwafira_status_fragment;
    }

    @Override
    public StatusViewModel getViewModel() {

        mStatusViewModel = ViewModelProviders.of(this,factory).get(StatusViewModel.class);
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
            Intent intent = KwafiraRevProfile.newIntent(getContext());
            startActivity(intent);
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
