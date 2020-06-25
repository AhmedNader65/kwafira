package com.almusand.kawfira.ui.main.ui.home.offerDetails;

import androidx.lifecycle.ViewModelProviders;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityOfferDetailsBinding;
import com.github.islamkhsh.BR;
import com.squareup.picasso.Picasso;

public class OfferDetailsActivity extends BaseActivity<ActivityOfferDetailsBinding,OfferDetailsViewModel> implements OfferDetailsNavigator {

    ActivityOfferDetailsBinding binding;
    OfferDetailsViewModel viewModel;
    SliderModel offer;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_offer_details;
    }

    @Override
    public OfferDetailsViewModel getViewModel() {
       viewModel = ViewModelProviders.of(this).get(OfferDetailsViewModel.class);
       return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        offer = (SliderModel) getIntent().getSerializableExtra("offer");
        binding.Desc.setText(offer.getDescription_ar());
        binding.title.setText(offer.getTitle_ar());
        Picasso.get().load(offer.getImage()).into(binding.sliderImg);
        viewModel.setNavigator(this);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        return intent;
    }

    @Override
    public void onCopyClicked() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("voucherCode", offer.getCoupon());
        clipboard.setPrimaryClip(clip);
        showDialog("تم نسخ كود الخصم","قم بإدخال كود الخصم عند عملية الدفع",null,null);
    }
}