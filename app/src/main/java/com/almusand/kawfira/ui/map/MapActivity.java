package com.almusand.kawfira.ui.map;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.ui.map.fragments.MapFragment;
import com.almusand.kawfira.ui.map.fragments.bottomsheet.BottomSheetFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.ActivityMapBinding;
import com.almusand.kawfira.ui.login.LoginActivity;
import com.almusand.kawfira.ui.map.fragments.schedule.SchedulingFragment;
import com.almusand.kawfira.ui.map.fragments.status.StatusFragment;
import com.almusand.kawfira.ui.map.fragments.type.TypeSelectionFragment;
import com.almusand.kawfira.utils.GlobalPreferences;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MapActivity extends BaseActivity<ActivityMapBinding, MapActivityViewModel> implements
        MapsActivityNavigator,

        TypeSelectionFragment.OnTypeFragmentInteractionListener,
        StatusFragment.OnStatusFragmentInteractionListener
        , SchedulingFragment.OnSchedulingFragmentInteractionListener,
        BottomSheetFragment.OnStatusFragmentInteractionListener, MapFragment.onAddressConfirmed {


    ViewModelProviderFactory factory;
    private MapActivityViewModel mMapActivityViewModel;
    private ActivityMapBinding mActivityHomeBinding;
    GlobalPreferences gp;
    String address,homeNum,apartNum;
    private String lat,lng;
//    boolean orderButtonVis = true,confirmButtonVis = false;

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, MapActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public MapActivityViewModel getViewModel() {

        mMapActivityViewModel = ViewModelProviders.of(this, factory).get(MapActivityViewModel.class);
        return mMapActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mActivityHomeBinding = getViewDataBinding();
        mMapActivityViewModel.setNavigator(this);
        gp = new GlobalPreferences(this);
//        mActivityHomeBinding.content.swipeupStatus.setOnTouchListener(this);
        Fragment fragment = new MapFragment(this);
        LoadFragmentSide(fragment);

    }



    public void LoadFragmentSide(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }


    public void Logout(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    private void loadFragmentTop(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.top_fragment_container, fragment).addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

    private void loadFragmentBottom(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.bottom_fragment_container, fragment).addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void ToCallKawafira() {

    }

    @Override
    public void ToChatWithKawafira() {

    }

    @Override
    public void showBottomSheet() {
        BottomSheetFragment dialogFragment = BottomSheetFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "BOttom sheet");

    }

    @Override
    public void ToOpenKawafiraProfile() {

    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void scheduling() {

        loadFragmentBottom(new SchedulingFragment());
    }

    @Override
    public void confirm() {
        mMapActivityViewModel.postOrder(gp.getUserAuth()
        ,lat
        ,lng
        ,gp.getAllIds()
        ,address
        ,homeNum
        ,apartNum);
        loadFragmentBottom(new StatusFragment());
    }

    @Override
    public void onSchedulingFragmentInteraction(String s) {
//        Log.e("time",s);
        mMapActivityViewModel.postReservation(gp.getUserAuth()
                ,s
                ,lat
                ,lng
                ,gp.getAllIds()
                ,address
                ,homeNum
                ,apartNum);
        loadFragmentBottom(new StatusFragment());
    }

    @Override
    public void onStatusFragmentInteraction() {
        mMapActivityViewModel.shouldShow();

    }

    @Override
    public void onTypeFragmentInteraction(int i) {
        mMapActivityViewModel.confirmOrSchedule(i);
    }

    @Override
    public void onBottomFragmentDetach() {
        Log.e("bottom", "detach");
        mMapActivityViewModel.setBottomSheet(false);
    }

    @Override
    public void onAddressClicked(String address, String homeNum, String apart,String lat,String lng) {
        this.address = address;
        this.homeNum = homeNum;
        this.apartNum = apart;
        this.lat = lat;
        this.lng = lng;
        getSupportFragmentManager().popBackStack();
        loadFragmentBottom(new TypeSelectionFragment());
    }
}
