package com.almusand.kawfira.ui.map;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.ui.main.ui.orders.OrdersParentFragment;
import com.almusand.kawfira.ui.map.fragments.MapFragment;
import com.almusand.kawfira.ui.map.fragments.MapViewModel;
import com.almusand.kawfira.ui.map.fragments.MapsNavigator;
import com.almusand.kawfira.ui.map.fragments.bottomsheet.BottomSheetFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.ActivityMapBinding;
import com.almusand.kawfira.ui.login.LoginActivity;
import com.almusand.kawfira.ui.map.fragments.schedule.SchedulingFragment;
import com.almusand.kawfira.ui.map.fragments.status.StatusFragment;
import com.almusand.kawfira.ui.map.fragments.type.TypeSelectionFragment;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MapActivity extends BaseActivity<ActivityMapBinding, MapActivityViewModel> implements
        MapsActivityNavigator,

        TypeSelectionFragment.OnTypeFragmentInteractionListener,
        StatusFragment.OnStatusFragmentInteractionListener
        , SchedulingFragment.OnSchedulingFragmentInteractionListener,
        BottomSheetFragment.OnStatusFragmentInteractionListener, MapFragment.onAddressConfirmed, MapsNavigator {


    ViewModelProviderFactory factory;
    private MapActivityViewModel mMapActivityViewModel;
    private ActivityMapBinding mActivityHomeBinding;
    GlobalPreferences gp;
    String address,homeNum,apartNum;
    private String lat,lng;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView username,userEmail;
    private ImageView userImg;

    MapViewModel viewModel2;
//    boolean orderButtonVis = true,confirmButtonVis = false;

    public static Intent newIntent(Context context) {
        Log.e("Oppened","MAPS");
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
    int x = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel2  = ViewModelProviders.of(this).get(MapViewModel.class);
        viewModel2.setNavigator2(this);
        mActivityHomeBinding = getViewDataBinding();
        mMapActivityViewModel.setNavigator(this);
        gp = new GlobalPreferences(this);
//        mActivityHomeBinding.content.swipeupStatus.setOnTouchListener(this);

        try{
            x =getIntent().getIntExtra("showStatus",0);
            if(x!=0){
                loadFragmentBottom(new StatusFragment());
            }
        }catch (Exception e){

        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_map_kwafira, R.id.nav_gallery,R.id.nav_orders, R.id.nav_orders,
                R.id.nav_tools, R.id.nav_settings, R.id.nav_send)
                .setDrawerLayout(mActivityHomeBinding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        View header = mActivityHomeBinding.navView.getHeaderView(0);
        Bundle args = new Bundle();
        args.putInt("x",x);
        navController.setGraph(R.navigation.mobile_navigation_map, args);
        username = header.findViewById(R.id.name);
        userImg = header.findViewById(R.id.img);

        userEmail = header.findViewById(R.id.email);

        NavigationUI.setupWithNavController(mActivityHomeBinding.navView, navController);

        mActivityHomeBinding.logout.setOnClickListener(v -> {
            // LOGOUT
            logout();
        });

        mActivityHomeBinding.navView.setNavigationItemSelectedListener(menuItem -> {
//            getSupportFragmentManager().popBackStack();
            int id=menuItem.getItemId();
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            if (id==R.id.nav_map_kwafira){
//                LoadFragmentSide(new MapFragment(this,x));
                if(x!=0){
                    loadFragmentBottom(new StatusFragment());
                }
               navController.navigate(R.id.nav_map_kwafira, args);
            }else {
                removeFragment();
                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem,navController);
            }

            /* Clear stack */
            //This is for closing the drawer after acting on it
            mActivityHomeBinding.drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });
    }




    public void LoadFragmentSide(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
            getSupportFragmentManager().popBackStack();
        }
        transaction.replace(R.id.nav_host_fragment, fragment);
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
        fragmentTransaction.replace(R.id.bottom_fragment_container, fragment,"status").addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }
    private void removeFragment() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("status");

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove( fragment);
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
    public void successfullyLogout() {

        startActivity(LoginActivity.newIntent(this));
        gp.storeLogged(false);

        gp.clearSharedPreferences();
        finish();
    }

    @Override
    public void resDone() {
        Toast.makeText(this, getString(R.string.done), Toast.LENGTH_SHORT).show();
        this.finish();
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
//        loadFragmentBottom(new StatusFragment());

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

    public void toogle(View view)
    {
        if(gp.getLanguage().equals("en")) {
            Log.e("CommonUtils.getLocale()",CommonUtils.getLocale()+" END");
            mActivityHomeBinding.drawerLayout.openDrawer(GravityCompat.END);
        }else{
            mActivityHomeBinding.drawerLayout.openDrawer(GravityCompat.END);
            Log.e("CommonUtils.getLocale()",CommonUtils.getLocale()+" START");
        }
    }

    private void logout() {
        Log.e("clicked","logout");
        mMapActivityViewModel.logout(gp.getUserAuth());

    }

    @Override
    public void showToast(String validateText) {

    }

    @Override
    public void showType(String address, String homeNum, String apartNum) {
    }

    @Override
    public void showType(String address, String homeNum, String apartNum, String lat, String lng) {
        onAddressClicked(address,homeNum,apartNum,lat,lng);
    }

}
