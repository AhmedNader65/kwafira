package com.almusand.kawfira.ui.main.ui.slideshow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.almusand.kawfira.Bases.BaseViewHolder;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.AppointmentItemBinding;
import com.almusand.kawfira.databinding.ItemViewpagerBinding;
import com.almusand.kawfira.ui.main.ui.appointments.adapter.AppointmentViewModel;
import com.almusand.kawfira.ui.main.ui.slideshow.SlideshowModel;
import com.almusand.kawfira.ui.main.ui.slideshow.SlideshowViewModel;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<String> mData;

    private ViewPager2 viewPager2;
    private SlideshowViewModel vm;

    private int[] colorArray = new int[]{android.R.color.black, android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_red_dark};

    public ViewPagerAdapter(List<String> data, ViewPager2 viewPager2, SlideshowViewModel viewModel) {
        this.mData = data;
        this.viewPager2 = viewPager2;
        this.vm = viewModel;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewpagerBinding viewpagerBinding = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(viewpagerBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mData.size()>0) {
            holder.onBind(position % mData.size());
            vm.setPositon(position % mData.size());
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    public class ViewHolder extends BaseViewHolder {
        ViewPagerViewModel viewModel;
        ItemViewpagerBinding itemBinding;

        ViewHolder(ItemViewpagerBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }


        @Override
        public void onBind(int position) {

            String animal = mData.get(position);
            itemBinding.tvTitle.setText(animal);
            itemBinding.container.setBackgroundResource(colorArray[position]);
            itemBinding.btnToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL)
                        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                    else{
                        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                    }
                }
            });
        }
    }

}