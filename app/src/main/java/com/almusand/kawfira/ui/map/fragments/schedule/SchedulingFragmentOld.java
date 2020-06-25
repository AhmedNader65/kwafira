package com.almusand.kawfira.ui.map.fragments.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.FragmentSchedulingBinding;


public class SchedulingFragmentOld
//        extends BaseFragment<FragmentSchedulingBinding, SchedulingViewModel> implements ScheduleNavigator
        {
//    private SchedulingViewModel mSchedulingViewModel;
//    private FragmentSchedulingBinding mFragmentBinding;
//    ViewModelProviderFactory factory;
//
//
//    private OnSchedulingFragmentInteractionListener mListener;
//
//    public SchedulingFragmentOld() {
//        // Required empty public constructor
//    }
//
//    public static SchedulingFragmentOld newInstance(String param1, String param2) {
//        SchedulingFragmentOld fragment = new SchedulingFragmentOld();
//        return fragment;
//    }
//
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        // Inflate the layout for this fragment
//
//        mFragmentBinding = getViewDataBinding();
//        mSchedulingViewModel.setNavigator(this);
//
//        mFragmentBinding.fluidSlider.setPositionListener(new Function1<Float, Unit>() {
//            @Override
//            public Unit invoke(Float pos) {
//                final String value = mSchedulingViewModel.getTimerText((int)(1 + 23 * pos));
//                mFragmentBinding.fluidSlider.setBubbleText(value);
//                mFragmentBinding.time.setText(value);
//                return Unit.INSTANCE;
//            }
//        });
//        mFragmentBinding.fluidSlider.setPosition(0);
//        mFragmentBinding.amTV.setOnClickListener(v -> amClicked());
//        mFragmentBinding.pmTV.setOnClickListener(v -> pmClicked());
//        mFragmentBinding.saturdayTV.setOnClickListener(v -> saturdaySelected());
//        mFragmentBinding.sundayTV.setOnClickListener(v -> sundaySelected());
//        mFragmentBinding.mondayTV.setOnClickListener(v -> mondaySelected());
//        mFragmentBinding.tuesdayTV.setOnClickListener(v -> tuesdaySelected());
//        mFragmentBinding.wednesdayTV.setOnClickListener(v -> wednesdaySelected());
//        mFragmentBinding.thursdayTV.setOnClickListener(v -> thursdaySelected());
//        mFragmentBinding.fridayTV.setOnClickListener(v -> fridaySelected());
//        mFragmentBinding.confirmDate.setOnClickListener(v -> toConfirmAndShowKawafiraToast());
//    }
//
//
//    @Override
//    public int getBindingVariable() {
//        return com.almusand.kawfira.BR.viewModel;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_scheduling;
//    }
//
//    @Override
//    public SchedulingViewModel getViewModel() {
//
//        mSchedulingViewModel =ViewModelProviders.of(requireActivity()).get(SchedulingViewModel.class);
//        return mSchedulingViewModel;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnSchedulingFragmentInteractionListener) {
//            mListener = (OnSchedulingFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnAddressFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    @Override
//    public void pmClicked() {
//        mFragmentBinding.pmTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.pmTV.setTextColor(getResources().getColor(R.color.white));
//        mFragmentBinding.amTV.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
//        mFragmentBinding.amTV.setBackgroundResource(0);
//    }
//    @Override
//    public void amClicked() {
//        mFragmentBinding.amTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.amTV.setTextColor(getResources().getColor(R.color.white));
//        mFragmentBinding.pmTV.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
//        mFragmentBinding.pmTV.setBackgroundResource(0);
//    }
//
//    public void resetAllDaysViews(){
//        mFragmentBinding.saturdayTV.setBackgroundResource(R.drawable.rounded_solid_grey);
//        mFragmentBinding.saturdayTV.setTextColor(getResources().getColor(R.color.black));
//        mFragmentBinding.sundayTV.setBackgroundResource(R.drawable.rounded_solid_grey);
//        mFragmentBinding.sundayTV.setTextColor(getResources().getColor(R.color.black));
//        mFragmentBinding.mondayTV.setBackgroundResource(R.drawable.rounded_solid_grey);
//        mFragmentBinding.mondayTV.setTextColor(getResources().getColor(R.color.black));
//        mFragmentBinding.tuesdayTV.setBackgroundResource(R.drawable.rounded_solid_grey);
//        mFragmentBinding.tuesdayTV.setTextColor(getResources().getColor(R.color.black));
//        mFragmentBinding.wednesdayTV.setBackgroundResource(R.drawable.rounded_solid_grey);
//        mFragmentBinding.wednesdayTV.setTextColor(getResources().getColor(R.color.black));
//        mFragmentBinding.thursdayTV.setBackgroundResource(R.drawable.rounded_solid_grey);
//        mFragmentBinding.thursdayTV.setTextColor(getResources().getColor(R.color.black));
//        mFragmentBinding.fridayTV.setBackgroundResource(R.drawable.rounded_solid_grey);
//        mFragmentBinding.fridayTV.setTextColor(getResources().getColor(R.color.black));
//
//    }
//    @Override
//    public void saturdaySelected() {
//        resetAllDaysViews();
//        mFragmentBinding.saturdayTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.saturdayTV.setTextColor(getResources().getColor(R.color.white));
//
//    }
//
//    @Override
//    public void sundaySelected() {
//        resetAllDaysViews();
//        mFragmentBinding.sundayTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.sundayTV.setTextColor(getResources().getColor(R.color.white));
//
//    }
//
//    @Override
//    public void mondaySelected() {
//
//        resetAllDaysViews();
//        mFragmentBinding.mondayTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.mondayTV.setTextColor(getResources().getColor(R.color.white));
//    }
//
//    @Override
//    public void tuesdaySelected() {
//
//        resetAllDaysViews();
//        mFragmentBinding.tuesdayTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.tuesdayTV.setTextColor(getResources().getColor(R.color.white));
//    }
//
//    @Override
//    public void wednesdaySelected() {
//
//        resetAllDaysViews();
//        mFragmentBinding.wednesdayTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.wednesdayTV.setTextColor(getResources().getColor(R.color.white));
//    }
//
//    @Override
//    public void thursdaySelected() {
//
//        resetAllDaysViews();
//        mFragmentBinding.thursdayTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.thursdayTV.setTextColor(getResources().getColor(R.color.white));
//    }
//
//    @Override
//    public void fridaySelected() {
//
//        resetAllDaysViews();
//        mFragmentBinding.fridayTV.setBackgroundResource(R.drawable.rounded_solid_prim);
//        mFragmentBinding.fridayTV.setTextColor(getResources().getColor(R.color.white));
//    }
//
//    @Override
//    public void toConfirmAndShowKawafiraToast() {
//        mListener.onSchedulingFragmentInteraction();
//    }
//
//    public interface OnSchedulingFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onSchedulingFragmentInteraction();
//    }
}
