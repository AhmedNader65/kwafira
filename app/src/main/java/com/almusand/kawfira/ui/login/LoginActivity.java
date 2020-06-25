package com.almusand.kawfira.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.ActivityLoginBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.resetPassword.forgot.ForgotPasswordActivity;
import com.almusand.kawfira.ui.verify.VerificationActivity;
import com.almusand.kawfira.ui.map.MapActivity;
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
            mLoginViewModel.onClickLogin(number, password);
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void register() {

        Intent intent = RegisterActivity.newIntent(LoginActivity.this);
        startActivity(intent);
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
        (new GlobalPreferences(this)).storeUserInfo((User) model.getResponse(),model.getAccess_token());
        (new GlobalPreferences(this)).storeLogged(true);
        Intent intent = HomeActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void forget() {
        Intent intent = ForgotPasswordActivity.newIntent(LoginActivity.this);
        startActivity(intent);
    }
}
