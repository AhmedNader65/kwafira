package com.almusand.kawfira.kwafira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityKwafiraServicesChoicesBinding;
import com.almusand.kawfira.kwafira.identity.VerifyIdActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

import java.util.List;

public class KwafiraServicesChoices extends BaseActivity<ActivityKwafiraServicesChoicesBinding, KwafiraServicesVM> {
    ActivityKwafiraServicesChoicesBinding binding;
    KwafiraServicesVM vm;

    User user;
    GlobalPreferences gp;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, KwafiraServicesChoices.class);
        return intent;
    }
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kwafira_services_choices;
    }

    @Override
    public KwafiraServicesVM getViewModel() {
        vm = ViewModelProviders.of(this).get(KwafiraServicesVM.class);
        return vm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(this);
        user = (User) getIntent().getSerializableExtra("user");
        vm.getServices().observe(this, servicesListUpdateObserver);
        vm.getUserUpdated().observe(this, userObserver);
        vm.initServices();

        binding.confirm.setOnClickListener(v -> {
            showLoading();
            vm.updateUser(gp.getUserAuth(),servicesAdapter.getCheckedItems());
        });
    }
    private ServicesAdapter servicesAdapter;
    Observer<List<ServicesModel>> servicesListUpdateObserver = servicesList -> {
        servicesAdapter = new ServicesAdapter();
        binding.appointmentsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.appointmentsList.setAdapter(servicesAdapter);
        servicesAdapter.renewItems(servicesList);
    };

    Observer<User> userObserver = user -> {
        hideLoading();
        startActivity(VerifyIdActivity.newIntent(this).putExtra("user",user));
    };
}