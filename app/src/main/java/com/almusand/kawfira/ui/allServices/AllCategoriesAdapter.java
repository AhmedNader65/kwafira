package com.almusand.kawfira.ui.allServices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Models.categories.CategoriesModel;
import com.almusand.kawfira.databinding.ItemCategoriesBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder> {

    Context context;
    private List<CategoriesModel> mData;
    public AllCategoriesAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        ItemCategoriesBinding categoriesBinding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(categoriesBinding);
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
                return mData.size();
            }
        }
        return 0;
    }


    public void renewItems(List<CategoriesModel> categoriesModels) {
        this.mData = categoriesModels;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mData.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(CategoriesModel categoriesModel) {
        this.mData.add(categoriesModel);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoriesBinding itemBinding;

        ViewHolder(ItemCategoriesBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }


        public void onBind(int position) {

            CategoriesModel model = mData.get(position);
            itemBinding.txtCat.setText(model.getName_ar());
            Picasso.get().load(model.getImage()).into(itemBinding.imgitemCat);
            itemBinding.getRoot().setOnClickListener(v -> {

                Intent intent = ServicesActivity.newIntent(context)
                        .putExtra("type","category")
                        .putExtra("id",model.getId())
                        .putExtra("title",model.getName_ar());
                ((Activity) context).startActivityForResult(intent, HomeActivity.OPENALLSERVICE_CODE);
            });
        }
    }

}