package com.almusand.kawfira.ui.main.ui.about;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentAboutusBinding;
import com.almusand.kawfira.databinding.FragmentSuggestionsBinding;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.almusand.kawfira.utils.CommonUtils.getPath;

public class AboutUsFragment extends BaseFragment<FragmentAboutusBinding, AboutUsViewModel> {

    private AboutUsViewModel aboutUsViewModel;
    FragmentAboutusBinding fragmentBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_aboutus;
    }

    @Override
    public AboutUsViewModel getViewModel() {
        aboutUsViewModel = ViewModelProviders.of(requireActivity()).get(AboutUsViewModel.class);
        return aboutUsViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding = getViewDataBinding();
        aboutUsViewModel.getAbout(new GlobalPreferences(getContext()).getLanguage());
    }

}