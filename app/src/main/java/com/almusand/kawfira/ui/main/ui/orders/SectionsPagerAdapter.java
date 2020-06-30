package com.almusand.kawfira.ui.main.ui.orders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.almusand.kawfira.R;
import com.almusand.kawfira.ui.main.ui.orders.ordersFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Fragment fragment,Context context) {
        super(fragment);
        mContext = context;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return ordersFragment.newInstance("incomplete");
        }
        return ordersFragment.newInstance("complete");
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}