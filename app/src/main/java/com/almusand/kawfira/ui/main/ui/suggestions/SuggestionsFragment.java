package com.almusand.kawfira.ui.main.ui.suggestions;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.BR;
import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentSuggestionsBinding;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.almusand.kawfira.utils.CommonUtils.getPath;

public class SuggestionsFragment extends BaseFragment<FragmentSuggestionsBinding,SuggestionsViewModel>
        implements SuggestionsNavigator,EasyPermissions.PermissionCallbacks {

    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    private static final int PICKFILE1_RESULT_CODE = 11;
    private static final int PICKFILE2_RESULT_CODE = 12;
    private static final int PICKFILE3_RESULT_CODE = 13;
    private SuggestionsViewModel suggestionsViewModel;
    FragmentSuggestionsBinding fragmentBinding;
    private Uri fileUri1,fileUri2,fileUri3;
    private String filePath1,filePath2,filePath3;

    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_suggestions;
    }

    @Override
    public SuggestionsViewModel getViewModel() {
        suggestionsViewModel = ViewModelProviders.of(requireActivity()).get(SuggestionsViewModel.class);
        return suggestionsViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding = getViewDataBinding();
        suggestionsViewModel.setNavigator(this);
        fragmentBinding.addDocument1.setOnClickListener(v -> {
            openFilePicker(PICKFILE1_RESULT_CODE);
        });
        fragmentBinding.addDocument2.setOnClickListener(v -> {
            openFilePicker(PICKFILE2_RESULT_CODE);
        });
        fragmentBinding.addDocument3.setOnClickListener(v -> {
            openFilePicker(PICKFILE3_RESULT_CODE);
        });

        fragmentBinding.confirm.setOnClickListener(v -> {
            showLoading();
            suggestionsViewModel.confirm(new GlobalPreferences(getContext()).getUserAuth(),fragmentBinding.textArea.getText().toString(),filePath1,filePath2,filePath3);
        });
    }

    private void openFilePicker(int code) {
        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_read_external_storage), EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }else {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("image/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(chooseFile, code);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE1_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri1 = data.getData();

                    filePath1 = getPath(getContext(),fileUri1);
                    fragmentBinding.addDocument1.setImageURI(fileUri1);
                }
                break;
            case PICKFILE2_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri2 = data.getData();

                    filePath2 = getPath(getContext(),fileUri2);
                    fragmentBinding.addDocument2.setImageURI(fileUri2);
                }
                break;
            case PICKFILE3_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri3 = data.getData();

                    filePath3 = getPath(getContext(),fileUri3);
                    fragmentBinding.addDocument3.setImageURI(fileUri3);
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
    public void showToast(String msg) {
        hideLoading();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessToast(String msg) {

        hideLoading();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        fragmentBinding.textArea.setText("");
        fragmentBinding.addDocument1.setImageResource(R.drawable.icon_add_circle);
        fragmentBinding.addDocument2.setImageResource(R.drawable.icon_add_circle);
        fragmentBinding.addDocument3.setImageResource(R.drawable.icon_add_circle);
        filePath3 = null;
        filePath2 = null;
        filePath1 = null;

    }
}