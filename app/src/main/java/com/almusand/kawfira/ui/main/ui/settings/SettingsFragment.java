package com.almusand.kawfira.ui.main.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentSettingsBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.editingActivities.lang.ChangeLanguageActivity;
import com.almusand.kawfira.ui.main.ui.editingActivities.email.ChangeEmailActivity;
import com.almusand.kawfira.ui.main.ui.editingActivities.name.ChangeNameAndImgActivity;
import com.almusand.kawfira.ui.main.ui.editingActivities.password.ChangePasswordActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class SettingsFragment extends BaseFragment<FragmentSettingsBinding,SettingsViewModel> {

    private SettingsViewModel settingsViewModel;
    FragmentSettingsBinding binding;
    GlobalPreferences gp;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        settingsViewModel =
                ViewModelProviders.of(requireActivity()).get(SettingsViewModel.class);
        return settingsViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gp = new GlobalPreferences(getContext());
        binding = getViewDataBinding();
        settingsViewModel.getUserData().observe(getViewLifecycleOwner(),userObserver);
        settingsViewModel.getUser(gp.getUserAuth());

        binding.editName.setOnClickListener(v -> {
            getActivity().startActivityForResult(new Intent(getContext(), ChangeNameAndImgActivity.class)
                    .putExtra("img",img)
                    .putExtra("name",name),HomeActivity.SETTINGS_CODE);
        });
        binding.editEmail.setOnClickListener(v -> {
            getActivity().startActivityForResult(new Intent(getContext(), ChangeEmailActivity.class)
                    .putExtra("email",email),HomeActivity.SETTINGS_CODE);
        });
        binding.editPassword.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getContext(), ChangePasswordActivity.class));
        });
        binding.editLanguage.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getContext(), ChangeLanguageActivity.class));
        });
    }

    private String img,name;
    private String email;
    Observer<User> userObserver = user -> {
        binding.language.setText(gp.getLanguage().equals("en")?"English":"العربية");
        this.img = user.getImage();
        this.name=user.getName();
        this.email=user.getEmail();
        binding.email.setText(user.getEmail());
        binding.username.setText(user.getName());
        binding.phone.setText(user.getPhone());
        Picasso.get().load(user.getImage()).placeholder(R.drawable.userphoto).into(binding.profileImg);
    };

    public void refresh() {
        settingsViewModel.getUser(gp.getUserAuth());
    }
}