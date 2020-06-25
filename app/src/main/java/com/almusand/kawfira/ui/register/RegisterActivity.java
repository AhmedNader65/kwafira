package com.almusand.kawfira.ui.register;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.ActivityRegisterBinding;
import com.almusand.kawfira.ui.verify.VerificationActivity;
import com.almusand.kawfira.ui.map.MapActivity;
import com.almusand.kawfira.utils.GlobalPreferences;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding,RegisterViewModel> implements RegisterNavigator {

    private ActivityRegisterBinding mActivityRegisterBinding;
    private RegisterViewModel viewModel;
    ViewModelProviderFactory factory;

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(RegisterViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = getViewDataBinding();
        viewModel.setNavigator(this);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void register() {
        String name = mActivityRegisterBinding.name.getText().toString();
        String email = mActivityRegisterBinding.email.getText().toString();
        String phone = mActivityRegisterBinding.num.getText().toString();
        String role = "client";
        String password = mActivityRegisterBinding.password.getText().toString();
        String token = "";
        if (viewModel.isEmailAndPasswordValid(phone,email, password)) {
            hideKeyboard();
            viewModel.onClickRegister(name,email,phone, password,role,token);
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openMainActivity(LoginModel model) {
        (new GlobalPreferences(this)).storeUserInfo((User) model.getResponse(),model.getAccess_token());
        Intent intent = MapActivity.newIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openVerifyActivity(User user) {
        Intent intent = VerificationActivity.newIntent(RegisterActivity.this)
                .putExtra("user",user);
        startActivity(intent);
        finish();
    }
}
