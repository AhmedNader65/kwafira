package com.almusand.kawfira.ui.map.fragments.bottomsheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.almusand.kawfira.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private OnStatusFragmentInteractionListener mListener;

    // TODO: Customize parameters
    public static BottomSheetFragment newInstance() {
        final BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onDetach() {
        mListener.onBottomFragmentDetach();
        super.onDetach();
        mListener = null;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BottomSheetFragment.OnStatusFragmentInteractionListener) {
            mListener = (BottomSheetFragment.OnStatusFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddressFragmentInteractionListener");
        }
    }


    public interface OnStatusFragmentInteractionListener {
        // TODO: Update argument type and name
        void onBottomFragmentDetach();
    }

}
