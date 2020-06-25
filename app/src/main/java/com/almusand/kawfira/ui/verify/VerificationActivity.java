package com.almusand.kawfira.ui.verify;

import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityVerificationBinding;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.resetPassword.reset.ResetPasswordActivity;
import com.almusand.kawfira.utils.CommonUtils;

public class VerificationActivity extends BaseActivity<ActivityVerificationBinding,VerificationViewModel> implements VerificationNavigator {

    User user;
    ActivityVerificationBinding binding;
    VerificationViewModel viewModel;

    boolean isReset = false;
    private String phone;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verification;
    }

    @Override
    public VerificationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(VerificationViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
           isReset= getIntent().getBooleanExtra("reset",false);
        }catch (Exception e){};

        user = (User) getIntent().getSerializableExtra("user");
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        phone = getIntent().getStringExtra("phone");
            binding.textView4.setText(phone);


    }

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, VerificationActivity.class);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void verify(String code) {
        viewModel.whichToLoad(code,isReset);
    }

    @Override
    public void verifyFailed() {
        binding.etOtp.setText("");
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resend() {
        viewModel.resend(phone);
    }

    @Override
    public void resendSuccess() {
        binding.timer.setVisibility(View.VISIBLE);
        binding.resendBtn.setBackgroundResource(R.drawable.rounded_solid_grey);
        binding.resendBtn.setEnabled(false);
        CommonUtils.countDownTimer(binding.timer, 60000,binding.resendBtn);
    }

    @Override
    public void openMainActivity(LoginModel model) {
        Intent intent = HomeActivity.newIntent(VerificationActivity.this)
                .putExtra("user",user);
        startActivity(intent);
        finish();
    }

    @Override
    public void openResetActivity(String code) {
        Intent intent = ResetPasswordActivity.newIntent(VerificationActivity.this)
                .putExtra("code",code);
        startActivity(intent);
    }

    @Override
    public void SendVerifyRequest(String code) {
        viewModel.sendVerify(code,user.getToken());

    }
}