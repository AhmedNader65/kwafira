package com.almusand.kawfira.ui.main.ui.appointments.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Bases.BaseViewHolder;
import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.Models.reservations.ReservationModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.AppointmentItemBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.appointments.AppointmentsViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "DataAdapter";
    private List<ReservationModel> data;
    Context context;
    GlobalPreferences gp;
    AppointmentsViewModel mainVM;
    onEditClickedListener mListner;
    public AppointmentsAdapter(AppointmentsViewModel appointmentsViewModel,onEditClickedListener mListner) {
        mainVM = appointmentsViewModel;
        this.data = new ArrayList<>();
        this.mListner = mListner;
    }
    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentItemBinding ViewBinding = AppointmentItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        gp = new GlobalPreferences(parent.getContext());
        context = parent.getContext();
        return new ReviewsViewHolder(ViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    public void renewItems(List<ReservationModel> sliderItems) {
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
        AppointmentViewModel viewModel;
        AppointmentItemBinding itemBinding;
        public ReviewsViewHolder(@NonNull AppointmentItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final ReservationModel appointmentModel = data.get(position);
            viewModel = new AppointmentViewModel(appointmentModel,context);
            viewModel.setNavigator(this);
            itemBinding.delete.setOnClickListener(v -> {
                ((HomeActivity)context).showDialog(context.getString(R.string.alert),context.getString(R.string.cancel_msg),context.getString(R.string.confirm_cancel), v1 -> {
                    viewModel.cancelOrder(gp.getUserAuth(),appointmentModel.getId()+"");

                    ((HomeActivity)context).dismissDialogNow();
                });
            });
            itemBinding.edit.setOnClickListener(v -> {
                mListner.onEditClick(appointmentModel.getId()+"");
            });
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
