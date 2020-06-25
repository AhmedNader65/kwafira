package com.almusand.kawfira.ui.map.fragments.type;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.FragmentTypeSelectionBinding;
import com.almusand.kawfira.ui.map.fragments.schedule.SchedulingViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;

public class TypeSelectionFragment extends BaseFragment<FragmentTypeSelectionBinding, TypeViewModel> implements TypeNavigator {
    ViewModelProviderFactory factory;
    private TypeViewModel mTypeViewModel;
    private FragmentTypeSelectionBinding mFragmentBinding;
    private OnTypeFragmentInteractionListener mListener;
    GlobalPreferences gp;
    public TypeSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTypeViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentBinding = getViewDataBinding();
        gp = new GlobalPreferences(getContext());
        mFragmentBinding.schedule.setOnClickListener(v -> mListener.onTypeFragmentInteraction(0));
        mFragmentBinding.confirmSelection.setOnClickListener(v -> mListener.onTypeFragmentInteraction(1));
        mFragmentBinding.price.setText(gp.getCost()+" ريال");
    }


    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type_selection;
    }

    @Override
    public TypeViewModel getViewModel() {

        mTypeViewModel =ViewModelProviders.of(requireActivity()).get(TypeViewModel.class);

        return mTypeViewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTypeFragmentInteractionListener) {
            mListener = (OnTypeFragmentInteractionListener) context;
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




    public interface OnTypeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTypeFragmentInteraction(int i);
    }
}
