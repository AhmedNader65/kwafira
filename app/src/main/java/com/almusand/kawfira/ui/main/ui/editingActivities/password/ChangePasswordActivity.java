package com.almusand.kawfira.ui.main.ui.editingActivities.password;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityChangeNameAndImgBinding;
import com.almusand.kawfira.databinding.ActivityChangePasswordBinding;
import com.almusand.kawfira.ui.main.ui.editingActivities.name.ChangeNameNavigator;
import com.almusand.kawfira.ui.main.ui.editingActivities.name.ChangeNameViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.squareup.picasso.Picasso;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding,ChangePasswordViewModel>
        implements ChangePasswordNavigator{
    private static final int PICKFILE_RESULT_CODE = 3;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 4;
    ActivityChangePasswordBinding binding;
    ChangePasswordViewModel viewModel;
    private Uri fileUri;
    private String filePath;
    GlobalPreferences gp;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public ChangePasswordViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(this);

        viewModel.setNavigator(this);
        binding.save.setOnClickListener(v -> {
            viewModel.onStorePassword(gp.getUserAuth(),binding.oldPassword.getText().toString(),binding.newPassword.getText().toString());
//            viewModel.validateAndSave(fileUri,gp.getUserAuth(),binding.editText.getText().toString(),filePath);
        });


    }




    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveToPref(String toString, String img) {
        gp.storeUserImgAndName(img,toString);
        this.finish();
    }

}