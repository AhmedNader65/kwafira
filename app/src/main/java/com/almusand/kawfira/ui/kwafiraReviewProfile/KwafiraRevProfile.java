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
import android.view.View;
import android.widget.LinearLayout;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityKwafiraRevProfileBinding;
import com.almusand.kawfira.ui.register.RegisterActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Optional;

public class KwafiraRevProfile extends BaseActivity<ActivityKwafiraRevProfileBinding,KwafiraRevViewModel> implements KwafiraRevNavigator {

    ActivityKwafiraRevProfileBinding kwafiraRevProfileBinding;
    KwafiraRevViewModel model;
    private User kwafira;

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
        kwafira = (User) getIntent().getSerializableExtra("Kwafira");
        setUp();
    }

    private void setUp() {
        ReviewsAdapter adapter = new ReviewsAdapter();
        GlobalPreferences pf = new GlobalPreferences(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        kwafiraRevProfileBinding.reviews.setLayoutManager(mLayoutManager);
        kwafiraRevProfileBinding.reviews.setItemAnimator(new DefaultItemAnimator());
        model.getData(kwafira.getId(),pf.getUserAuth()).observe(this, reviewModel -> {
            adapter.setData(reviewModel.getReviews());
        });
        kwafiraRevProfileBinding.reviews.setAdapter(adapter);
        kwafiraRevProfileBinding.username.setText(kwafira.getName());
            try{
            Picasso.get().load(kwafira.getImage()).placeholder(R.drawable.userphoto).into(kwafiraRevProfileBinding.userPic);
        }catch (Exception e){}
        try{
            kwafiraRevProfileBinding.rateText.setText(String.format("%.2f",Float.valueOf(kwafira.getOverall_rate())));
            kwafiraRevProfileBinding.listitemrating.setRating(Float.parseFloat(kwafira.getOverall_rate()));
        }catch (Exception e){
            kwafiraRevProfileBinding.listitemrating.setVisibility(View.INVISIBLE);
            kwafiraRevProfileBinding.rateText.setText("لا توجد تقييمات");
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, KwafiraRevProfile.class);
        return intent;
    }

}
