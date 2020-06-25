package com.almusand.kawfira.ui.main.ui.suggestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentSuggestionsBinding;

public class SuggestionsFragment extends BaseFragment<FragmentSuggestionsBinding,SuggestionsViewModel> {

    private SuggestionsViewModel suggestionsViewModel;
    FragmentSuggestionsBinding fragmentBinding;

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_suggestions;
    }

    @Override
    public SuggestionsViewModel getViewModel() {
        suggestionsViewModel = ViewModelProviders.of(requireActivity()).get(SuggestionsViewModel.class);
        return suggestionsViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding = getViewDataBinding();
    }
}