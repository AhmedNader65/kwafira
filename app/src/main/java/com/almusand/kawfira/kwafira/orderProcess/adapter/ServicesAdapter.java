package com.almusand.kawfira.kwafira.orderProcess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ItemListStartServicesBinding;
import com.almusand.kawfira.kwafira.orderProcess.OrderMainServicesVM;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    Context context;
    private List<ServicesModel> mData;
    GlobalPreferences pf;

    onServiceListener mlistner;
    OrderModel order;
    public ServicesAdapter(OrderModel order,List<ServicesModel> mData, onServiceListener mlistner) {
        this.mData = mData;
        this.mlistner = mlistner;
        this.order = order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        pf = new GlobalPreferences(context);
        ItemListStartServicesBinding servicesBinding = ItemListStartServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(servicesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (mData.size() > 0) {
            viewHolder.onBind(i);
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void renewItems(List<ServicesModel> servicesModels) {
        this.mData = servicesModels;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mData.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ServicesModel servicesModels) {
        this.mData.add(servicesModels);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<ServicesModel> servicesModels) {
        this.mData.addAll(servicesModels);
        notifyDataSetChanged();
    }

    public void refreshCart() {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ItemListStartServicesBinding itemBinding;
        OrderMainServicesVM itemViewModel;
        ServicesModel model;
        ViewHolder(ItemListStartServicesBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }


        public void onBind(int position) {
            model = mData.get(position);
            itemViewModel = new OrderMainServicesVM();

            itemBinding.txtCat.setText(model.getTitle_ar());
            itemBinding.price.setText(model.getInitial_price()+" ريال");
            Picasso.get().load(model.getImage()).placeholder(R.drawable.userphoto).into(itemBinding.imgitemCat);

            itemBinding.btn.setOnClickListener(v -> {

                mlistner.start(model.getId(),model.getTitle_ar());
            });

            int seconds = 0;
            if(order.getSessions().size()>0){
                double price = 0;
                for(SessionModel sessionModel :order.getSessions()){
                    if(sessionModel.getService_id() == model.getId()){
                        if(sessionModel.getPrice()!=null) {
                            itemBinding.done.setVisibility(View.VISIBLE);
                            itemBinding.time.setVisibility(View.VISIBLE);
                            price += Double.valueOf(sessionModel.getPrice());
                            itemBinding.price.setText(String.format("%.2f", price) + " ريال");
                            seconds += sessionModel.getDuration();
                            int secondsV = seconds;
                            int minutes = secondsV / 60;
                            secondsV = secondsV % 60;
                            int hours = minutes / 60;
                            minutes = minutes % 60;
                            itemBinding.time.setText(String.format("%02d:%02d:%02d", hours, minutes, secondsV));
                        }
                    }
                }

            }else{

                itemBinding.done.setVisibility(View.GONE);
                itemBinding.time.setVisibility(View.GONE);
            }

        }

    }

    public interface onServiceListener{
        void start(int id,String name);
    }

}