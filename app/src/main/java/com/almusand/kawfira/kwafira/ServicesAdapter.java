package com.almusand.kawfira.kwafira;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ItemServicesBinding;
import com.almusand.kawfira.databinding.ServicesItemKwafiraBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.home.adapter.ServiceAdapterNavigator;
import com.almusand.kawfira.ui.main.ui.home.adapter.ServiceAdapterViewModel;
import com.almusand.kawfira.ui.serviceDetails.ServiceDetailsActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    Context context;
    private List<ServicesModel> mData;
    GlobalPreferences pf;
    ArrayList<String> ids = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        pf = new GlobalPreferences(context);
        ServicesItemKwafiraBinding servicesBinding = ServicesItemKwafiraBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        if (mData != null) {
            if (mData.size() > 0) {
                if(mData.size()>5){
                    return 5;
                }
                return mData.size();
            }
        }
        return 0;
    }


    public ArrayList<String> getCheckedItems() {
        return  ids;
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
        ServicesItemKwafiraBinding itemBinding;
        ServicesModel model;
        ViewHolder(ServicesItemKwafiraBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }


        public void onBind(int position) {
            model = mData.get(position);
            itemBinding.serviceName.setText(model.getTitle_ar());
            Picasso.get().load(model.getImage()).placeholder(R.drawable.userphoto).placeholder(R.drawable.userphoto).into(itemBinding.imgitemCat);
            itemBinding.checked.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked){
                    ids.add(model.getId()+"");
                }else{
                    ids.remove(model.getId()+"");
                }
            });
        }


    }


}