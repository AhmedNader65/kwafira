package com.almusand.kawfira.ui.kwafiraReviewProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityKwafiraRevProfileBinding;
import com.almusand.kawfira.ui.register.RegisterActivity;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.Optional;

public class KwafiraRevProfile extends BaseActivity<ActivityKwafiraRevProfileBinding,KwafiraRevViewModel> implements KwafiraRevNavigator {

    ActivityKwafiraRevProfileBinding kwafiraRevProfileBinding;
    KwafiraRevViewModel model;

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kwafira_rev_profile;
    }

    @Override
    public KwafiraRevViewModel getViewModel() {
        model = ViewModelProviders.of(this).get(KwafiraRevViewModel.class);
        return model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kwafiraRevProfileBinding = getViewDataBinding();
        model.setNavigator(this);
        setUp();
    }

    private void setUp() {
        ReviewsAdapter adapter = new ReviewsAdapter();
        GlobalPreferences pf = new GlobalPreferences(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        kwafiraRevProfileBinding.reviews.setLayoutManager(mLayoutManager);
        kwafiraRevProfileBinding.reviews.setItemAnimator(new DefaultItemAnimator());
        model.getData(pf.getUserId(),pf.getUserAuth()).observe(this, reviewModel -> {
            adapter.setData(reviewModel.getReviews());
        });
        kwafiraRevProfileBinding.reviews.setAdapter(adapter);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, KwafiraRevProfile.class);
        return intent;
    }

}
