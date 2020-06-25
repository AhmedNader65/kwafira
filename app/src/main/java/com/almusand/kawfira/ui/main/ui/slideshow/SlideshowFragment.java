package com.almusand.kawfira.ui.main.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentSlideshowBinding;
import com.almusand.kawfira.ui.main.ui.slideshow.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class SlideshowFragment extends BaseFragment<FragmentSlideshowBinding, SlideshowViewModel> {
    FragmentSlideshowBinding binding;
    SlideshowViewModel viewModel;

    private SlideshowViewModel slideshowViewModel;

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_slideshow;
    }

    @Override
    public SlideshowViewModel getViewModel() {
        viewModel = ViewModelProviders.of(requireActivity()).get(SlideshowViewModel.class);
        return viewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        List<String> list = new ArrayList<>();
        list.add("First Screen");
        list.add("Second Screen");
        list.add("Third Screen");
        list.add("Fourth Screen");

        binding.ViewPager.setAdapter(new ViewPagerAdapter(list, binding.ViewPager,viewModel));

        binding.indicator.createIndicators(4, 0);
        final Observer<Integer> scoreObserver = position -> {
            Log.e("observe Position",""+((position-1)%list.size()));
            //Update the textview that holds the score
        };
        binding.ViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                binding.indicator.createIndicators(5, ((position)%list.size()));

            }
        });

//      Observe the live data
        viewModel.getPositon().observe(getActivity(),scoreObserver);

//        binding.indicator.setViewPager(binding.ViewPager);

    }
}