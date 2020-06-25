package com.almusand.kawfira.ui.resetPassword.forgot;

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
import com.almusand.kawfira.databinding.ActivityForgotPasswordBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.resetPassword.reset.ResetPasswordActivity;
import com.almusand.kawfira.ui.verify.VerificationActivity;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding,ForgotViewModel> implements ForgotNavigator {
    ActivityForgotPasswordBinding binding;
    ForgotViewModel viewModel;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public ForgotViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ForgotViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= getViewDataBinding();
        viewModel.setNavigator(this);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void forgotSuccess() {
        Intent intent = VerificationActivity.newIntent(ForgotPasswordActivity.this);
        intent.putExtra("reset",true);
        intent.putExtra("phone",binding.num.getText().toString());
        startActivity(intent);
    }

    @Override
    public void handleError(Throwable t) {

    }

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        return intent;
    }
}