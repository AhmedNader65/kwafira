package com.almusand.kawfira.kwafira.identity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityVerifyIdBinding;
import com.almusand.kawfira.kwafira.identity.certFragment.CertFragment;
import com.almusand.kawfira.kwafira.identity.idFragment.IdFragment;
import com.almusand.kawfira.kwafira.reviewing.ReviewingActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class VerifyIdActivity extends BaseActivity<ActivityVerifyIdBinding,VerifyIdViewModel> implements VerifyIdNavigator, IdFragment.onUserInteract, CertFragment.onUserInteract {
    ActivityVerifyIdBinding binding;
    VerifyIdViewModel viewModel;
    GlobalPreferences gp;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_id;
    }

    @Override
    public VerifyIdViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(VerifyIdViewModel.class);
        return viewModel;
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, VerifyIdActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        gp = new GlobalPreferences(this);
        setUp();
        viewModel.checkStatus(gp.isIdSent(),getIntent().getBooleanExtra("idSent",false));
    }

    private void setUp() {

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),getLifecycle(),this,this,this);
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.viewPager.setUserInputEnabled(false);

        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> {
                    if(position==0) {
                        tab.setText(R.string.verify_text1);
                    }else
                        tab.setText(R.string.verify_text2);
                }).attach();
        LinearLayout tabStrip = ((LinearLayout)binding.tabs.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
    }

    @Override
    public void onUserIdDone() {
        TabLayout.Tab tab = binding.tabs.getTabAt(1);
        tab.select();
        gp.storeUserIdSent();
    }

    @Override
    public void openNextFragment() {
        onUserIdDone();
    }

    @Override
    public void onUserCertDone() {
        gp.storeUserCertSent();
        startActivity(ReviewingActivity.newIntent(this));
        this.finish();
    }
}