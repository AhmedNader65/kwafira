package com.almusand.kawfira.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.ActivityLoginBinding;
import com.almusand.kawfira.kwafira.KwafiraServicesChoices;
import com.almusand.kawfira.kwafira.home.KwafiraMainActivity;
import com.almusand.kawfira.kwafira.identity.VerifyIdActivity;
import com.almusand.kawfira.kwafira.reviewing.ReviewingActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.resetPassword.forgot.ForgotPasswordActivity;
import com.almusand.kawfira.ui.verify.VerificationActivity;
import com.almusand.kawfira.ui.register.RegisterActivity;
import com.almusand.kawfira.utils.GlobalPreferences;


public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel> implements LoginNavigator{


    ViewModelProviderFactory factory;
    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {

        mLoginViewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);
        return mLoginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);


    }
    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void login() {
        String number = mActivityLoginBinding.num.getText().toString();
        String password = mActivityLoginBinding.password.getText().toString();
        if (mLoginViewModel.isEmailAndPasswordValid(number, password)) {
            hideKeyboard();
            showLoading();
            mLoginViewModel.onClickLogin(number, password);
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void register() {

        Intent intent = RegisterActivity.newIntent(LoginActivity.this)
                .putExtra("role","client");

        startActivity(intent);
        finish();
    }

    @Override
    public void registerKwafira() {

        Intent intent = RegisterActivity.newIntent(LoginActivity.this)
                .putExtra("role","kwafera");
        startActivity(intent);
        finish();
    }

    @Override
    public void openVerifyIdForKwafira(LoginModel user) {
        (new GlobalPreferences(this)).storeUserInfo(user.getResponse(),user.getAccess_token());
        (new GlobalPreferences(this)).storeLogged(true);
        Log.e(user.getResponse().getNational_id(),(user.getResponse().getNational_id() != null)+"");
        Intent intent;
        if(user.getResponse().getNational_id() != null){
             intent = VerifyIdActivity.newIntent(LoginActivity.this).putExtra("idSent", user.getResponse().getNational_id() != null);
        }else{
             intent = KwafiraServicesChoices.newIntent(LoginActivity.this);

        }
        startActivity(intent);
        finish();
    }

    @Override
    public void openUnderReviewActivity(LoginModel user) {
        (new GlobalPreferences(this)).storeUserInfo(user.getResponse(),user.getAccess_token());
        (new GlobalPreferences(this)).storeLogged(true);
        startActivity(ReviewingActivity.newIntent(this));
        finish();
    }

    @Override
    public void openVerifyActivity(User user) {

        Intent intent = VerificationActivity.newIntent(LoginActivity.this)
                .putExtra("user",user);
        startActivity(intent);
    }

    @Override
    public void openMainActivity(LoginModel model) {
        (new GlobalPreferences(this)).storeUserInfo(model.getResponse(),model.getAccess_token());
        (new GlobalPreferences(this)).storeLogged(true);
        Intent intent = HomeActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivityForKwafira(LoginModel model) {
        (new GlobalPreferences(this)).storeUserInfo(model.getResponse(),model.getAccess_token());
        (new GlobalPreferences(this)).storeLogged(true);
        Intent intent = KwafiraMainActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void wrongInfo() {
        hideLoading();
        Toast.makeText(this, R.string.wrongInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void forget() {
        Intent intent = ForgotPasswordActivity.newIntent(LoginActivity.this);
        startActivity(intent);
    }

}
