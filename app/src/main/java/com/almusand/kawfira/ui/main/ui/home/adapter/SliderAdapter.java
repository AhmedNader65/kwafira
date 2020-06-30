package com.almusand.kawfira.ui.main.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.databinding.ItemSliderBinding;
import com.almusand.kawfira.ui.main.ui.home.offerDetails.OfferDetailsActivity;
import com.github.islamkhsh.CardSliderAdapter;
import com.squareup.picasso.Picasso;


import java.util.List;

public class SliderAdapter extends CardSliderAdapter<SliderAdapter.ViewHolder> {

    Context context;
    private List<SliderModel> mData;


    private int[] colorArray = new int[]{android.R.color.black, android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_red_dark};

    public SliderAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        ItemSliderBinding itemSliderBinding = ItemSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemSliderBinding);
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


    public void renewItems(List<SliderModel> sliderItems) {
        this.mData = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mData.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderModel sliderItem) {
        this.mData.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public void bindVH( ViewHolder viewHolder, int i) {
        if (mData.size() > 0) {
            viewHolder.onBind(i);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSliderBinding itemBinding;

        ViewHolder(ItemSliderBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }


        public void onBind(int position) {

            SliderModel model = mData.get(position);
            itemBinding.sliderTxt.setText(model.getTitle_ar());
            Picasso.get().load(model.getImage()).into(itemBinding.sliderImg);
            itemBinding.container.setOnClickListener(v -> {

                Intent intent = OfferDetailsActivity.newIntent(context)
                        .putExtra("offer",model);
                context.startActivity(intent);
            });
        }
    }

}