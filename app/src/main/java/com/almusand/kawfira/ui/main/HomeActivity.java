package com.almusand.kawfira.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityHomeBinding;
import com.almusand.kawfira.ui.main.ui.home.HomeFragment;
import com.almusand.kawfira.ui.main.ui.settings.SettingsFragment;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeActivityViewModel> {

    public static final int OPENSERVICE_CODE = 1;
    public static final int OPENALLSERVICE_CODE = 2;
    public static final int SETTINGS_CODE = 4;
    private AppBarConfiguration mAppBarConfiguration;
    GlobalPreferences pf;
    HomeActivityViewModel viewModel;
    ActivityHomeBinding binding;
    private TextView username;
    private ImageView userImg;
    private TextView userEmail;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        pf = new GlobalPreferences(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_kwafira, R.id.nav_gallery,R.id.nav_orders, R.id.nav_orders,
                R.id.nav_tools, R.id.nav_settings, R.id.nav_send)
                .setDrawerLayout(binding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.name);
        userImg = header.findViewById(R.id.img);
        if(pf.getUSER_Imge().length()>4){

            Picasso.get().load(pf.getUSER_Imge())
                    .placeholder(R.drawable.userphoto).into(userImg);
        }
         userEmail = header.findViewById(R.id.email);

        userEmail.setText(pf.getUSER_Email());
        username.setText(pf.getUserName());
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void toogle(View view)
    {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        {                Log.e("request code","not checked "+requestCode);

            super.onActivityResult(requestCode, resultCode, data);
            // check if the request code is same as what is passed  here it is 2
            if (requestCode == OPENSERVICE_CODE||requestCode==OPENALLSERVICE_CODE) {
                Log.e("request code","checked and in  "+requestCode);
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

                HomeFragment homeFragment =
                        (HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);

                if(homeFragment!=null){
                    homeFragment.refreshCart();
                }else{
                    Log.e("homeFragment"," null");

                }
            }else if(requestCode == SETTINGS_CODE){

                if(pf.getUSER_Imge().length()>4){

                    Picasso.get().load(pf.getUSER_Imge())
                            .placeholder(R.drawable.userphoto).into(userImg);
                }
                userEmail.setText(pf.getUSER_Email());
                username.setText(pf.getUserName());

                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

                SettingsFragment settingsFragment =
                        (SettingsFragment) (navHostFragment.getChildFragmentManager().getFragments().get(0));
                if(settingsFragment!=null){
                    settingsFragment.refresh();
                }
            }
        }
    }

    public void showDialog(String تنية_هام, String s, String تأكيد_الإلغاء, Object o) {
    }
}
