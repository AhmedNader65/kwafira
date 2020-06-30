package com.almusand.kawfira.ui.main.ui.orders.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseViewHolder;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.OrderItemBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.orders.OrdersViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class OrdersAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "DataAdapter";
    private List<OrderModel> data;
    Context context;
    GlobalPreferences gp;
    OrdersViewModel mainVM;
    String status;

    public OrdersAdapter(OrdersViewModel mainVM, String status) {
        this.data = new ArrayList<>();
        this.mainVM = mainVM;
        this.status = status;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderItemBinding ViewBinding = OrderItemBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public class ReviewsViewHolder extends BaseViewHolder implements Navigator, EasyPermissions.PermissionCallbacks {
        private static final int CALL_PERMISSION_REQUEST_CODE = 3;
        OrderViewModel viewModel;
        OrderItemBinding itemBinding;
        private OrderModel orderModel;

        public ReviewsViewHolder(@NonNull OrderItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        @Override
        public void onBind(int position) {
            orderModel = data.get(position);
            viewModel = new OrderViewModel(orderModel);
            viewModel.setNavigator(this);
            if (orderModel.getKwafera() != null) {
                itemBinding.call.setVisibility(View.VISIBLE);
                itemBinding.chat.setVisibility(View.VISIBLE);
                if (orderModel.getKwafera().getOverall_rate() != null) {
                    itemBinding.star.setVisibility(View.VISIBLE);
                } else {
                    itemBinding.star.setVisibility(View.GONE);
                }
                if (orderModel.getKwafera().getImage() != null) {
                    Picasso.get().load(orderModel.getKwafera().getImage()).into(itemBinding.img);
                }
            } else {
                // No Kwafira assigned
                itemBinding.star.setVisibility(View.GONE);
                itemBinding.call.setVisibility(View.GONE);
                itemBinding.chat.setVisibility(View.GONE);
            }
            itemBinding.call.setOnClickListener(v -> {
                if (!EasyPermissions.hasPermissions(context, Manifest.permission.CALL_PHONE)) {
                    EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.permission_call_required_toast), CALL_PERMISSION_REQUEST_CODE, Manifest.permission.CALL_PHONE);
                } else {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderModel.getKwafera().getPhone()));
                    context.startActivity(intent);
                }
            });
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
            if (orderModel.getServices_data().length > 1) {
                int id = itemBinding.serviceLL.getId();
                int oldId = itemBinding.serviceLL.getId();
                for (int i = 1; i < orderModel.getServices().length; i++) {
                    ServicesModel service = orderModel.getServices_data()[i];
                    View v = LayoutInflater.from(context).inflate(R.layout.service_layout, null);
                    TextView serviceName = v.findViewById(R.id.serviceName);
                    serviceName.setText(service.getTitle_ar());
                    serviceName.setId(itemBinding.serviceName.getId() + i);
                    ++id;
                    v.setId(id);
//
                    itemBinding.constraintLayout.addView(v, 0);
                    ConstraintSet set1 = new ConstraintSet();
                    set1.clone(itemBinding.constraintLayout);
                    set1.connect(v.getId(), ConstraintSet.TOP, oldId, ConstraintSet.BOTTOM, 0);
//                    set1.connect(v.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
                    set1.connect(v.getId(), ConstraintSet.RIGHT, oldId, ConstraintSet.RIGHT, 0);
//                    set1.connect(mImage.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
                    set1.applyTo(itemBinding.constraintLayout);
                    oldId++;
                }
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(itemBinding.constraintLayout);
                constraintSet.connect(R.id.line2, ConstraintSet.TOP, id, ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(itemBinding.constraintLayout);
            }
        }

        @Override
        public void refresh() {
            mainVM.initOrders(gp.getUserAuth(), status);
            notifyDataSetChanged();

        }

        @Override
        public void showCancelDialog() {
            ((HomeActivity) context).showDialog("تنبية هام", "هل أنت متأكد من الغاء الطلب؟", "تأكيد الإلغاء", v1 -> {
                viewModel.cancelOrder(gp.getUserAuth(), orderModel.getId() + "");

                ((HomeActivity) context).dismissDialogNow();
            });
        }

        @Override
        public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderModel.getKwafera().getPhone()));
            context.startActivity(intent);
        }

        @Override
        public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }
    }
}
