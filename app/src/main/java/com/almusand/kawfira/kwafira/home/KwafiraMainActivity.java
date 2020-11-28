package com.almusand.kawfira.kwafira.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityMainKwafiraBinding;
import com.almusand.kawfira.kwafira.home.ui.home.status.StatusNavigator;
import com.almusand.kawfira.ui.login.LoginActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class KwafiraMainActivity extends BaseActivity<ActivityMainKwafiraBinding, HomeActivityViewModel>  implements StatusNavigator {
    GlobalPreferences gp ;
    ActivityMainKwafiraBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    HomeActivityViewModel baseViewModel;
    private TextView username,userEmail;
    private ImageView userImg;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, KwafiraMainActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_kwafira;
    }

    @Override
    public HomeActivityViewModel getViewModel() {
        baseViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        return baseViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        showLoading();
        gp = new GlobalPreferences(this);
        baseViewModel.setNavigator(this);
        baseViewModel.getUserData().observe(this,userObserver);
        baseViewModel.getUser(gp.getUserAuth());
        gp.storeKawfiraVerfied();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_kwafira, R.id.nav_gallery, R.id.nav_orders)
                .setDrawerLayout(binding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        View header = binding.navView.getHeaderView(0);

        username = header.findViewById(R.id.name);
        userImg = header.findViewById(R.id.img);

        userEmail = header.findViewById(R.id.email);

        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.logout.setOnClickListener(v -> {
            // LOGOUT
            logout();
        });

        binding.navView.setNavigationItemSelectedListener(menuItem -> {
            int id=menuItem.getItemId();
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem,navController);
            //This is for closing the drawer after acting on it
            binding.drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });
    }

    private void logout() {
        Log.e("clicked","logout");
        baseViewModel.logout(gp.getUserAuth());

    }

    private String img,name;
    private String email;
    Observer<User> userObserver = user -> {
        hideLoading();
        this.img = user.getImage();
        this.name=user.getName();
        this.email=user.getEmail();

        userEmail.setText(email);
        username.setText(name);
        if(img!=null) {
            if (img.length() > 4) {

                Picasso.get().load(img)
                        .placeholder(R.drawable.userphoto).into(userImg);
            }
        }
        gp.storeUserInfo(user,gp.getUserAuth());
    };

    public void toogle(View view)
    {
        binding.drawerLayout.openDrawer(GravityCompat.END);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void updatedSuccessfuly(User available) {

    }

    @Override
    public void revertAndShowToast(String msg) {

    }

    @Override
    public void update(boolean isChecked) {

    }

    @Override
    public void dontUpdate() {

    }

    @Override
    public void successfullyLogout() {
        startActivity(LoginActivity.newIntent(this));
        gp.storeLogged(false);

        gp.clearSharedPreferences();
        this.finish();
    }
}