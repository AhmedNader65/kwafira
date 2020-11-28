package com.almusand.kawfira.ui.counterActivity;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityCounterBinding;
import com.almusand.kawfira.kwafira.orderProcess.timer.CounterViewModel;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.squareup.picasso.Picasso;

public class CounterActivity extends BaseActivity<ActivityCounterBinding, com.almusand.kawfira.kwafira.orderProcess.timer.CounterViewModel> implements com.almusand.kawfira.kwafira.orderProcess.timer.CounterNavigator {

    com.almusand.kawfira.kwafira.orderProcess.timer.CounterViewModel counterViewModel;
    ActivityCounterBinding mActivityBinding;
    GlobalPreferences gp;
    private OrderModel model;
    private int orderid;

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_counter;
    }

    @Override
    public CounterViewModel getViewModel() {
        counterViewModel = ViewModelProviders.of(this).get(com.almusand.kawfira.kwafira.orderProcess.timer.CounterViewModel.class);
        return counterViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("CounterActivity", "On CREATE " + counterViewModel.startTime);
        mActivityBinding = getViewDataBinding();
        counterViewModel.setNavigator(this);
        gp = new GlobalPreferences(this);
        String type = getIntent().getStringExtra("type");
        counterViewModel.checkType(type);
    }


    @Override
    public void updateUI(String format) {
        mActivityBinding.counter.setText(format);
    }

    @Override
    public void start() {
        mActivityBinding.costMoney.setVisibility(View.GONE);
        String title = getIntent().getStringExtra("service_title_ar");
        long millis = getIntent().getLongExtra("start", System.currentTimeMillis()) - (getIntent().getIntExtra("full_duration", 0) * 1000);
        mActivityBinding.title.setText(title);
        counterViewModel.init(millis);

    }


    @Override
    public void stop(SessionModel sessionModel) {

        String title = getIntent().getStringExtra("service_title_ar");
        mActivityBinding.label.setText("تم انتهاء السيشن");
        mActivityBinding.title.setText(title);
//        mActivityBinding.costMoney.setVisibility(View.VISIBLE);

        int seconds = (getIntent().getIntExtra("full_duration", 0));
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        mActivityBinding.counter.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

//        mActivityBinding.costMoney.setText("مدة الخدمة حتى الآن "+ String.format("%02d:%02d:%02d",hours, minutes, seconds));
    }

    @Override
    public void payment() {
        orderid = getIntent().getIntExtra("Order_id", 0);
        int seconds = getIntent().getIntExtra("time", 0);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;

        mActivityBinding.counter.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        mActivityBinding.title.setText("تم انتهاء العمل");
        mActivityBinding.costTV.setVisibility(View.VISIBLE);
        mActivityBinding.costTV.setText("تكلفة العمل");
        mActivityBinding.costMoney.setVisibility(View.VISIBLE);
        mActivityBinding.costMoney.setText(getIntent().getDoubleExtra("price", 0) + "  ريال");
        mActivityBinding.paymentLayout.setVisibility(View.VISIBLE);
        mActivityBinding.payBtn.setOnClickListener(v -> {
            counterViewModel.payOrder(gp.getUserAuth(), orderid, "cash");
        });
    }

    @Override
    public void paymentFromStart() {
        model = (OrderModel) getIntent().getSerializableExtra("order");
        orderid = model.getId();
        int seconds = model.getDuration();
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;

        mActivityBinding.counter.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        mActivityBinding.label.setText("تم انتهاء العمل");
        mActivityBinding.title.setText("");
        mActivityBinding.costTV.setVisibility(View.VISIBLE);
        mActivityBinding.costTV.setText("تكلفة العمل");
        mActivityBinding.costMoney.setVisibility(View.VISIBLE);
        mActivityBinding.costMoney.setText(model.getPrice() + "  ريال");
        mActivityBinding.paymentLayout.setVisibility(View.VISIBLE);
        mActivityBinding.payBtn.setOnClickListener(v -> {
            counterViewModel.payOrder(gp.getUserAuth(), orderid, "cash");
        });
    }

    @Override
    public void applyCoupon(String coupon) {
        counterViewModel.applyCoupon(gp.getUserAuth(), orderid, coupon);
    }

    @Override
    public void updatePrice(OrderModel order, double v) {
        this.model = order;
        mActivityBinding.costMoney.setText(v + "  ريال");
        Toast.makeText(this, "تم تطبيق العرض", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void paid(OrderModel order) {
        this.model = order;
        mActivityBinding.paymentLayout.setVisibility(View.GONE);
        mActivityBinding.waiting.setVisibility(View.VISIBLE);

    }

    @Override
    public void confirmedPayment() {
        OrderModel order = this.model;
        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String img = getIntent().getStringExtra("img");
        String rate = getIntent().getStringExtra("rate");
        int order_id = getIntent().getIntExtra("order_id",0);

        mActivityBinding.rateLayout.setVisibility(View.VISIBLE);
        if (img != null) {
            Picasso.get().load(img).placeholder(R.drawable.userphoto).into(mActivityBinding.userPic);
        }
        mActivityBinding.kwafiraName.setText(name);
        if (rate != null) {
            mActivityBinding.rate.setText(String.format("%.2f",Float.valueOf(order.getKwafera().getOverall_rate())));
            mActivityBinding.listitemrating.setRating(Float.parseFloat(rate));
        } else {
            mActivityBinding.listitemrating.setNumStars(0);
            mActivityBinding.rate.setText(R.string.no_rate);
        }

        mActivityBinding.review.setOnClickListener(v -> {
            if(mActivityBinding.rateContent.getText().toString().length()>1) {
                showLoading();
                counterViewModel.review(gp.getUserAuth(), mActivityBinding.rateContent.getText().toString(), Integer.parseInt(id)
                        , mActivityBinding.rateKwafiraRB.getRating(), order_id);
            }else{
                Toast.makeText(this, getString(R.string.error_empty_message), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void rated() {
        hideLoading();
        Toast.makeText(this, R.string.suc_rate, Toast.LENGTH_SHORT).show();

        ProcessPhoenix.triggerRebirth(this);
    }
}
