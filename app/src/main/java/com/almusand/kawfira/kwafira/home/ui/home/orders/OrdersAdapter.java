package com.almusand.kawfira.kwafira.home.ui.home.orders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseViewHolder;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.KwafiraOrderItemBinding;
import com.almusand.kawfira.databinding.OrderItemBinding;
import com.almusand.kawfira.kwafira.home.HomeActivityViewModel;
import com.almusand.kawfira.kwafira.home.ui.KwafiraMapsActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.orders.OrdersViewModel;
import com.almusand.kawfira.ui.main.ui.orders.adapter.OrderViewModel;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pub.devrel.easypermissions.EasyPermissions;

public class OrdersAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "DataAdapter";
    private List<OrderModel> data;
    Context context;
    GlobalPreferences gp;
    HomeActivityViewModel mainVM;

    public OrdersAdapter(HomeActivityViewModel mainVM) {
        this.data = new ArrayList<>();
        this.mainVM = mainVM;

    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        KwafiraOrderItemBinding ViewBinding = KwafiraOrderItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        context = parent.getContext();
        gp = new GlobalPreferences(context);
        return new ReviewsViewHolder(ViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    public void renewItems(List<OrderModel> sliderItems) {
        this.data = sliderItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public class ReviewsViewHolder extends BaseViewHolder implements Navigator{
        private static final int CALL_PERMISSION_REQUEST_CODE = 3;
        KwafiraOrderViewModel viewModel;
        KwafiraOrderItemBinding itemBinding;
        private OrderModel orderModel;

        public ReviewsViewHolder(@NonNull KwafiraOrderItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
            views  = new ArrayList<>();
            viewsId  = new ArrayList<>();
        }

        ArrayList<View> views ;
        ArrayList<Integer> viewsId ;
        @Override
        public void onBind(int position) {
            orderModel = data.get(position);
            viewModel = new KwafiraOrderViewModel(new GlobalPreferences(context).getLanguage(),orderModel);
            viewModel.setNavigator(this);
            User client = orderModel.getClient();
            if (client.getImage() != null) {
                Picasso.get().load(client.getImage()).placeholder(R.drawable.userphoto).into(itemBinding.img);
            }
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
            removeExtraViews();
            if (orderModel.getServices_data().length > 1) {
                int id = itemBinding.serviceLL.getId();
                int oldId = itemBinding.serviceLL.getId();
                for (int i = 1; i < orderModel.getServices_data().length; i++) {

                    ServicesModel service = orderModel.getServices_data()[i];
                    try{
                        LinearLayout v = (LinearLayout) views.get(i-1);
                        Log.e("creating view","copying view +"+ i);
                        TextView serviceName = v.findViewById(viewsId.get(i-1));
                        if(new GlobalPreferences(context).getLanguage().equals("en")) {
                            serviceName.setText(service.getTitle_en());
                        }else {
                            serviceName.setText(service.getTitle_ar());
                        }
                        id = v.getId();
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("creating view","Creating view"+i);
                        View v = LayoutInflater.from(context).inflate(R.layout.service_layout, null);
                        TextView serviceName = v.findViewById(R.id.serviceName);
                        if(new GlobalPreferences(context).getLanguage().equals("en")) {
                            serviceName.setText(service.getTitle_en());
                        }else {
                            serviceName.setText(service.getTitle_ar());
                        }
                        serviceName.setId(itemBinding.serviceName.getId() + i);
                        Random rng = new Random();
                        id += 233;
                        if(v.findViewById(id)!=null){
                            id =  rng.nextInt() & Integer.MAX_VALUE;
                        }
                        v.setId(id);
                        this.views.add(v);
                        this.viewsId.add(serviceName.getId());
                        itemBinding.constraintLayout.addView(v);
                        ConstraintSet set1 = new ConstraintSet();
                        set1.clone(itemBinding.constraintLayout);
                        set1.connect(v.getId(), ConstraintSet.TOP, oldId, ConstraintSet.BOTTOM, 0);
//                    set1.connect(v.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
                        if(gp.getLanguage().equals("en")) {
                            set1.connect(v.getId(), ConstraintSet.LEFT, oldId, ConstraintSet.LEFT, 0);
                        }else{
                            set1.connect(v.getId(), ConstraintSet.RIGHT, oldId, ConstraintSet.RIGHT, 0);

                        }
                        //                    set1.connect(mImage.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
                        set1.applyTo(itemBinding.constraintLayout);

                    }
                    oldId =id;
//

                }
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(itemBinding.constraintLayout);
                constraintSet.connect(R.id.accept, ConstraintSet.TOP, id, ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(itemBinding.constraintLayout);
            }else{
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(itemBinding.constraintLayout);
                constraintSet.connect(R.id.accept, ConstraintSet.TOP, R.id.serviceLL, ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(itemBinding.constraintLayout);
            }
            itemBinding.accept.setOnClickListener(v -> {
                viewModel.acceptOrder(gp.getUserAuth(),orderModel.getId()+"");
            });
        }
        private void removeExtraViews() {
            int count = views.size();
            boolean hasNoViews = false;
            for (int i = 0; i < count; i++) {
                try {
                    ServicesModel m = orderModel.getServices_data()[i + 1];
                } catch (Exception e) {
                    if(i ==0){
                        hasNoViews = true;
                    }
                    itemBinding.constraintLayout.removeView(views.get(views.size() - 1));
                    views.remove(views.size() - 1);
                    viewsId.remove(viewsId.size() - 1);
                }
            }
            if(hasNoViews){

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(itemBinding.constraintLayout);
                constraintSet.connect(R.id.line2, ConstraintSet.TOP, R.id.serviceLL, ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(itemBinding.constraintLayout);
            }
        }


        @Override
        public void refresh() {
            mainVM.initOrders(gp.getUserAuth());
            notifyDataSetChanged();

            ProcessPhoenix.triggerRebirth(context);

        }

        @Override
        public void showOnMap() {
            context.startActivity(KwafiraMapsActivity.newIntent(context).putExtra("lat",orderModel.getLatitude()).putExtra("lng",orderModel.getLongitude()));
        }

        @Override
        public void showToast(String s) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }

    }
}
