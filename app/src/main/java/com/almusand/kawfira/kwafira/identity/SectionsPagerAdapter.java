package com.almusand.kawfira.kwafira.identity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.almusand.kawfira.R;
import com.almusand.kawfira.kwafira.identity.certFragment.CertFragment;
import com.almusand.kawfira.kwafira.identity.idFragment.IdFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    IdFragment.onUserInteract mListenerId;
    CertFragment.onUserInteract mListenerCert;

    public SectionsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context mContext,IdFragment.onUserInteract mListenerId
    ,CertFragment.onUserInteract mListenerCert) {
        super(fragmentManager, lifecycle);
        this.mListenerId = mListenerId;
        this.mListenerCert = mListenerCert;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return IdFragment.newInstance(mListenerId);
        }
        return CertFragment.newInstance(mListenerCert);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}