package com.almusand.kawfira.ui.main.ui.editingActivities.name;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

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

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityChangeNameAndImgBinding;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.squareup.picasso.Picasso;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.almusand.kawfira.utils.CommonUtils.getPath;

public class ChangeNameAndImgActivity extends BaseActivity<ActivityChangeNameAndImgBinding,ChangeNameViewModel>
        implements EasyPermissions.PermissionCallbacks, ChangeNameNavigator{
    private static final int PICKFILE_RESULT_CODE = 3;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 4;
    ActivityChangeNameAndImgBinding binding;
    ChangeNameViewModel viewModel;
    private Uri fileUri;
    private String filePath;
    GlobalPreferences gp;
    String name,img;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_name_and_img;
    }

    @Override
    public ChangeNameViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ChangeNameViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(this);
        try{
           name = getIntent().getStringExtra("name");
        }catch (Exception e){
           name = gp.getUserName();
        }
        try{
           img = getIntent().getStringExtra("img");
        }catch (Exception e){
           img = gp.getUSER_Imge();
        }
        Picasso.get().load(img).placeholder(R.drawable.userphoto).into(binding.img);
        binding.editText.setText(name);
        viewModel.setNavigator(this);
        binding.save.setOnClickListener(v -> {
            viewModel.validateAndSave(fileUri,gp.getUserAuth(),binding.editText.getText().toString(),filePath);
        });

        binding.editImg.setOnClickListener(v -> {
            if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                EasyPermissions.requestPermissions(this, getString(R.string.permission_read_external_storage), EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }else {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();

                    filePath = getPath(this,fileUri);
                    binding.img.setImageURI(fileUri);

                    Log.e("uri .getpath",fileUri.getPath());
                    Log.e("uri .tostring",fileUri.toString());
                    Log.e("path",getPath(this,fileUri));
                }

                break;
        }
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
        Toast.makeText(this, getString(R.string.wrong_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveToPref(String toString, String img) {
        gp.storeUserImgAndName(img,toString);
        this.finish();
    }

}