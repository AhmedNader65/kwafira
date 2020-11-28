package com.almusand.kawfira.kwafira.home.ui.previous_orders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseViewHolder;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.databinding.AppointmentItemBinding;
import com.almusand.kawfira.databinding.OldOrderKwafiraBinding;
import com.almusand.kawfira.kwafira.home.ui.previous_orders.PreviousOrdersVM;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "DataAdapter";
    private List<OrderModel> data;
    Context context;
    GlobalPreferences gp;
    PreviousOrdersVM mainVM;
    public OrdersAdapter(PreviousOrdersVM previousOrdersVM) {
        mainVM = previousOrdersVM;
        this.data = new ArrayList<>();
    }
    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OldOrderKwafiraBinding ViewBinding = OldOrderKwafiraBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        gp = new GlobalPreferences(parent.getContext());
        context = parent.getContext();
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
        PrevOrderVM viewModel;
        OldOrderKwafiraBinding itemBinding;
        public ReviewsViewHolder(@NonNull OldOrderKwafiraBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final OrderModel appointmentModel = data.get(position);
            viewModel = new PrevOrderVM(gp.getLanguage(),appointmentModel);
            viewModel.setNavigator(this);
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }

        @Override
        public void refresh() {
            mainVM.initAppointments(gp.getUserAuth());
            notifyDataSetChanged();
        }
    }
    public interface onEditClickedListener{
        void onEditClick(String id);
    }
}
