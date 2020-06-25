package com.almusand.kawfira.ui.main.ui.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.categories.CategoriesModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ItemCategoriesBinding;
import com.almusand.kawfira.databinding.ItemServicesBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.serviceDetails.ServiceDetailsActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    onCartListener mListner;
    Context context;
    private List<ServicesModel> mData;
    GlobalPreferences pf;
    public ServicesAdapter(onCartListener mListner) {
        this.mListner = mListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        pf = new GlobalPreferences(context);
        ItemServicesBinding servicesBinding = ItemServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements ServiceAdapterNavigator {
        ItemServicesBinding itemBinding;
        ServiceAdapterViewModel itemViewModel;
        ServicesModel model;
        ViewHolder(ItemServicesBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }


        public void onBind(int position) {
            model = mData.get(position);
            itemViewModel = new ServiceAdapterViewModel();
            itemViewModel.setNavigator(this);
            itemViewModel.initCart(pf,model.getId());
            itemViewModel.getCartNumber().observe((LifecycleOwner) context,cartObserver);
            itemViewModel.getCartFromPf(pf);

            itemBinding.txtCat.setText(model.getTitle_ar());
            itemBinding.price.setText(model.getInitial_price()+" ريال");
            Picasso.get().load(model.getImage()).into(itemBinding.imgitemCat);
            itemBinding.imgitemCat.setOnClickListener(v -> {
                Intent intent = ServiceDetailsActivity.newIntent(context)
                        .putExtra("service",model);
                ((Activity) context).startActivityForResult(intent, HomeActivity.OPENSERVICE_CODE);
            });
            itemBinding.btn.setOnClickListener(v -> {
                itemViewModel.clicked();
            });
        }

        Observer<Integer> cartObserver = cartCount -> {
            itemViewModel.shouldShow(cartCount);
        };

        @Override
        public void onOrderService() {

            itemBinding.btn.setBackgroundResource(R.drawable.rounded_solid_accent_delete_order);
            itemBinding.btn.setText(" ×  احذف");
            pf.increaseCartCounter(model.getInitial_price());
            itemViewModel.getCartFromPf(pf);
            pf.saveInCart(model.getId());

        }

        @Override
        public void onCancelService() {

            itemBinding.btn.setBackgroundResource(R.drawable.rounded_solid_prim);
            itemBinding.btn.setText(" +  اطلب");
            pf.decreaseCartCounter(model.getInitial_price());
            itemViewModel.getCartFromPf(pf);
            pf.removeFromCart(model.getId());
        }

        @Override
        public void showCart() {
            mListner.showCart();
        }

        @Override
        public void hideCart() {
            mListner.hideCart();
        }

        @Override
        public void inCart() {
            itemBinding.btn.setBackgroundResource(R.drawable.rounded_solid_accent_delete_order);
            itemBinding.btn.setText(" ×  احذف");
        }

        @Override
        public void NotInCart() {
            itemBinding.btn.setBackgroundResource(R.drawable.rounded_solid_prim);
            itemBinding.btn.setText(" +  اطلب");
        }
    }

    public interface onCartListener{
        void showCart();
        void hideCart();
    }

}