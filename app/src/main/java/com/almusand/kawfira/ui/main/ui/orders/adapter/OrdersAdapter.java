package com.almusand.kawfira.ui.main.ui.orders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseViewHolder;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.databinding.OrderItemBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.orders.OrdersViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "DataAdapter";
    private List<OrderModel> data;
    Context context;
    GlobalPreferences gp;
    OrdersViewModel mainVM;
    public OrdersAdapter(OrdersViewModel mainVM) {
        this.data = new ArrayList<>();
        this.mainVM = mainVM;
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

    public class ReviewsViewHolder extends BaseViewHolder implements Navigator {
        OrderViewModel viewModel;
        OrderItemBinding itemBinding;

        public ReviewsViewHolder(@NonNull OrderItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final OrderModel orderModel = data.get(position);
            viewModel = new OrderViewModel(orderModel);
            viewModel.setNavigator(this);
            itemBinding.delete.setOnClickListener(v -> {
                ((HomeActivity)context).showDialog("تنبية هام","هل أنت متأكد من الغاء الطلب؟","تأكيد الإلغاء", v1 -> {
                    viewModel.cancelOrder(gp.getUserAuth(),orderModel.getId()+"");

                    ((HomeActivity)context).dismissDialogNow();
                });
            });

            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }

        @Override
        public void refresh() {
            mainVM.initOrders(gp.getUserAuth());
            notifyDataSetChanged();

        }
    }
}
