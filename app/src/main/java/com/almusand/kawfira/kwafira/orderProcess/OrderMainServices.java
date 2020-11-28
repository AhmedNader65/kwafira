package com.almusand.kawfira.kwafira.orderProcess;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityOrderMainServicesBinding;
import com.almusand.kawfira.kwafira.home.ui.previous_orders.adapter.OrdersAdapter;
import com.almusand.kawfira.kwafira.orderProcess.adapter.ServicesAdapter;
import com.almusand.kawfira.kwafira.orderProcess.timer.KwafiraCounter;
import com.almusand.kawfira.kwafira.reviewing.ReviewingActivity;
import com.almusand.kawfira.ui.counterActivity.CounterActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

import java.util.Arrays;

public class OrderMainServices extends BaseActivity<ActivityOrderMainServicesBinding,OrderMainServicesVM> implements Navigator, ServicesAdapter.onServiceListener {

    private static final int START_SERVICE_CODE = 5;
    OrderModel order ;
    ActivityOrderMainServicesBinding binding;
    OrderMainServicesVM vm;
    GlobalPreferences gp;
    int id = 0;
    int sessionId = 0;
    private ServicesAdapter adapter;
    private String name;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_main_services;
    }

    @Override
    public OrderMainServicesVM getViewModel() {
        vm = ViewModelProviders.of(this).get(OrderMainServicesVM.class);
        return vm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = getViewDataBinding();
        order = (OrderModel) getIntent().getSerializableExtra("order");
        vm.setNavigator(this);
        gp = new GlobalPreferences(this);
        setUp();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, OrderMainServices.class);
        return intent;
    }

    private void setUp() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.services.setLayoutManager(mLayoutManager);
        binding.services.setItemAnimator(new DefaultItemAnimator());
        adapter = new ServicesAdapter(order,Arrays.asList(order.getServices_data()),this);
        binding.services.setAdapter(adapter);
    }

    @Override
    public void start(int id, String name) {
        showLoading();
        this.id = id;
        this.name = name;
        vm.startService(gp.getUserAuth(), String.valueOf(order.getId()),id+"");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==START_SERVICE_CODE&&resultCode==RESULT_OK){
            vm.updateService((SessionModel)data.getSerializableExtra("session"),order);
        }
    }

    @Override
    public void updateAdapter(OrderModel order) {
        adapter.renewItems(Arrays.asList(order.getServices_data()));
    }

    @Override
    public void setSession(SessionModel session) {
        hideLoading();
        order.addSession(session);
        sessionId = session.getId();
        Log.e("Session","Session id >> "+sessionId);
        startActivityForResult(KwafiraCounter.newIntent(this).putExtra("id",sessionId)
                .putExtra("type","start")
                .putExtra("order_id",order.getId()).putExtra("title",name),START_SERVICE_CODE);
    }

    @Override
    public void endOrderClicked() {
        vm.endOrder(gp.getUserAuth(),order.getId()+"");
    }

    @Override
    public void endOrder(OrderModel order) {
        startActivity(KwafiraCounter.newIntent(this).putExtra("type","payment").putExtra("order",order));
    }

    @Override
    public void failed() {
        hideLoading();
    }
}