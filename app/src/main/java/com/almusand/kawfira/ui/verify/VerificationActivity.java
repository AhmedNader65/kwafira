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
import com.almusand.kawfira.kwafira.KwafiraServicesChoices;
import com.almusand.kawfira.kwafira.identity.VerifyIdActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.resetPassword.reset.ResetPasswordActivity;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;

public class VerificationActivity extends BaseActivity<ActivityVerificationBinding, VerificationViewModel> implements VerificationNavigator {

    User user;
    ActivityVerificationBinding binding;
    VerificationViewModel viewModel;
    GlobalPreferences gp;
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
        gp = new GlobalPreferences(this);
        try {
            isReset = getIntent().getBooleanExtra("reset", false);
        } catch (Exception e) {
        }

        user = (User) getIntent().getSerializableExtra("user");
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        phone = user.getPhone();
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
        viewModel.whichToLoad(code, isReset);
    }

    @Override
    public void verifyFailed() {

        hideLoading();
        binding.etOtp.setText("");
    }

    @Override
    public void showToast(String msg) {

        hideLoading();
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
        CommonUtils.countDownTimer(binding.timer, 60000, binding.resendBtn);
    }

    @Override
    public void openMainActivity(LoginModel model) {
        hideLoading();
        gp.storeUserInfo(model.getResponse(), model.getAccess_token());
        Intent intent;
        intent = HomeActivity.newIntent(VerificationActivity.this)
                .putExtra("user", model.getResponse());


        startActivity(intent);
        finish();
    }

    @Override
    public void openVerifyIdActivity(LoginModel model) {
        hideLoading();
        (new GlobalPreferences(this)).storeUserInfo(model.getResponse(),model.getAccess_token());
        (new GlobalPreferences(this)).storeLogged(true);
        gp.storeUserInfo(model.getResponse(), model.getAccess_token());
        Intent intent;
        intent = KwafiraServicesChoices.newIntent(VerificationActivity.this)
                .putExtra("user", model.getResponse());
        startActivity(intent);
        finish();
    }

    @Override
    public void openResetActivity(String code) {
        hideLoading();
        Intent intent = ResetPasswordActivity.newIntent(VerificationActivity.this)
                .putExtra("code", code);
        startActivity(intent);
    }

    @Override
    public void SendVerifyRequest(String code) {
        showLoading();
        viewModel.sendVerify(code, user.getToken());

    }
}