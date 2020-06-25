package com.almusand.kawfira.ui.kwafiraReviewProfile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseViewHolder;
import com.almusand.kawfira.databinding.ReviewItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "DataAdapter";
    private List<ReviewModel> data;

    public ReviewsAdapter() {
    }

    public void setData(List<ReviewModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewItemBinding ViewBinding = ReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ReviewsViewHolder(ViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public class ReviewsViewHolder extends BaseViewHolder {
        ReviewViewModel viewModel;
        ReviewItemBinding itemBinding;
        public ReviewsViewHolder(@NonNull ReviewItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final ReviewModel reviewModel = data.get(position);
            viewModel = new ReviewViewModel(reviewModel);
            if(viewModel == null)
            Log.e("View Model null","NULL");
            if(itemBinding == null)
                Log.e("itemBinding null","NULL");
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
            Picasso.get().load(reviewModel.getImg_url()).into(itemBinding.userPic);


        }
    }
}
