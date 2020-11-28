package com.almusand.kawfira.kwafira.orderProcess.timer;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityKwafiraCounterBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.squareup.picasso.Picasso;

public class KwafiraCounter extends BaseActivity<ActivityKwafiraCounterBinding, CounterViewModel> implements CounterNavigator{
    CounterViewModel counterViewModel;
    ActivityKwafiraCounterBinding binding;

    OrderModel order;
    GlobalPreferences gp;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, KwafiraCounter.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kwafira_counter;
    }

    @Override
    public CounterViewModel getViewModel() {
        counterViewModel = ViewModelProviders.of(this).get(CounterViewModel.class);
        return counterViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= getViewDataBinding();
        gp = new GlobalPreferences(this);
        counterViewModel.setNavigator(this);
        String type = getIntent().getStringExtra("type");
        counterViewModel.checkType(type);

    }

    @Override
    public void updateUI(String format) {
        binding.counter.setText(format);
    }

    @Override
    public void start() {
        String title = getIntent().getStringExtra("title");
        binding.title.setText(title);
        counterViewModel.init(System.currentTimeMillis());
        binding.endwork.setOnClickListener(v -> {
            counterViewModel.endService(gp.getUserAuth(),getIntent().getIntExtra("order_id",0)+"",
                    getIntent().getIntExtra("id",0)+"");
        });
    }

    @Override
    public void stop(SessionModel sessionModel) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("session",sessionModel);
        setResult(Activity.RESULT_OK,returnIntent);
        this.finish();
    }

    @Override
    public void payment() {
        order = (OrderModel) getIntent().getSerializableExtra("order");

        int seconds = (order.getDuration());
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int hours = minutes/60;
        minutes = minutes % 60;

        binding.counter.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));
        binding.label.setText("تم انتهاء العمل");
        binding.title.setText("");
        binding.costTV.setVisibility(View.VISIBLE);
        binding.costTV.setText("تكلفة العمل");
        binding.costMoney.setVisibility(View.VISIBLE);
        binding.costMoney.setText(order.getPrice()+"  ريال");
        binding.endwork.setVisibility(View.GONE);
        binding.continueToNext.setVisibility(View.GONE);
        binding.paymentDone.setVisibility(View.VISIBLE);
        binding.paymentDone.setOnClickListener(v -> {
            counterViewModel.confirmPayment(gp.getUserAuth(),order.getId());
        });
    }

    @Override
    public void paymentFromStart() {

    }

    @Override
    public void applyCoupon(String coupon) {

    }

    @Override
    public void updatePrice(OrderModel order, double v) {

    }

    @Override
    public void paid(OrderModel order) {

    }

    @Override
    public void confirmedPayment() {
        binding.rateLayout.setVisibility(View.VISIBLE);
        binding.paymentDone.setEnabled(false);

        User client = order.getClient();
        if (client.getImage() != null) {
            Picasso.get().load(client.getImage()).placeholder(R.drawable.userphoto).into(binding.userPic);
        }
        binding.kwafiraName.setText(client.getName());
        if (client.getOverall_rate() != null) {
            binding.rate.setText(String.format("%.2f",Float.valueOf(client.getOverall_rate())));
            binding.listitemrating.setRating(Float.parseFloat(client.getOverall_rate()));
        } else {
            binding.listitemrating.setNumStars(0);
            binding.rate.setText("لا يوجد تقييم بعد");
        }

        binding.review.setOnClickListener(v -> {
            if(binding.rateContent.getText().toString().length()>1) {
                showLoading();
                counterViewModel.review(gp.getUserAuth(), binding.rateContent.getText().toString(), order.getClient_id()
                        , binding.rateKwafiraRB.getRating(), order.getId());
            }else{
                Toast.makeText(this, getString(R.string.error_empty_message), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void rated() {

        hideLoading();
        Toast.makeText(this, getString(R.string.done), Toast.LENGTH_SHORT).show();

        ProcessPhoenix.triggerRebirth(this);





    }

    @Override
    public void onBackPressed() {
    }
}