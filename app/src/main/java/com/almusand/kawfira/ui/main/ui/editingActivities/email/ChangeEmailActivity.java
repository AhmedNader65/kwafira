package com.almusand.kawfira.ui.main.ui.editingActivities.email;

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
import com.almusand.kawfira.databinding.ActivityChangeEmailBinding;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class ChangeEmailActivity extends BaseActivity<ActivityChangeEmailBinding, ChangeEmailViewModel>
        implements EasyPermissions.PermissionCallbacks, ChangeEmailNavigator {
    private static final int PICKFILE_RESULT_CODE = 3;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 4;
    ActivityChangeEmailBinding binding;
    ChangeEmailViewModel viewModel;
    private Uri fileUri;
    private String filePath;
    GlobalPreferences gp;
    String email,img;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_email;
    }

    @Override
    public ChangeEmailViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ChangeEmailViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(this);
        try{
           email = getIntent().getStringExtra("email");
        }catch (Exception e){
           email = gp.getUserName();
        }
        binding.editText.setText(email);
        viewModel.setNavigator(this);
        binding.save.setOnClickListener(v -> {
            viewModel.validateAndSave(gp.getUserAuth(),binding.editText.getText().toString());
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveToPref(String toString, String img) {
        gp.storeUserImgAndName(img,toString);
    }

    @Override
    public void saveEmailToPref(String email) {
        gp.storeEmail(email);
        this.finish();
    }
}