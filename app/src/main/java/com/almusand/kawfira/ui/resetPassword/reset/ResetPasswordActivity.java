package com.almusand.kawfira.ui.resetPassword.reset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityResetPasswordBinding;
import com.almusand.kawfira.ui.login.LoginActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.resetPassword.forgot.ForgotPasswordActivity;
import com.almusand.kawfira.ui.verify.VerificationActivity;
import com.almusand.kawfira.utils.CommonUtils;

public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding,ResetViewModel> implements ResetNavigator {
    ActivityResetPasswordBinding binding;
    ResetViewModel viewModel;
    String code;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public ResetViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ResetViewModel.class);
        return  viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        code = getIntent().getStringExtra("code");
    }

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, ResetPasswordActivity.class);
        return intent;
    }

    @Override
    public void savePassword(String password) {

        if(viewModel.isPasswordValid(password)) {
            viewModel.onStorePassword(password, code);
        }else{
            showToast("كلمة المرور قصيرة جداً");
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void storeSuccess() {
        Intent intent = LoginActivity.newIntent(ResetPasswordActivity.this);
        startActivity(intent);
    }

    @Override
    public void handleError(Throwable t) {

    }

    @Override
    public void failedStore() {
        this.finish();
    }
}