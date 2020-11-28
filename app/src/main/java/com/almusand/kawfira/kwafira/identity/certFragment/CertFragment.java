package com.almusand.kawfira.kwafira.identity.certFragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.CertFragmentBinding;
import com.almusand.kawfira.kwafira.identity.idFragment.IdFragment;
import com.almusand.kawfira.kwafira.identity.idFragment.IdVerifyNavigator;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.almusand.kawfira.utils.CommonUtils.getFileName;
import static com.almusand.kawfira.utils.CommonUtils.getPath;

public class CertFragment extends BaseFragment<CertFragmentBinding,CertViewModel> implements EasyPermissions.PermissionCallbacks, CertVerifyNavigator {
    private static final int PICKFILE_RESULT_CODE = 3;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 4;
    private CertViewModel mViewModel;
    CertFragmentBinding binding;
    private Uri fileUri;
    private String filePath;
    GlobalPreferences gp;
    onUserInteract mListener;

    public static CertFragment newInstance(onUserInteract mListener) {
        return new CertFragment(mListener);
    }

    public CertFragment(onUserInteract mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.cert_fragment;
    }

    @Override
    public CertViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(CertViewModel.class);
        return mViewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(getContext());
        mViewModel.setNavigator(this);
        binding.addPic.setOnClickListener(v -> {
            if (!EasyPermissions.hasPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                EasyPermissions.requestPermissions(this, getString(R.string.permission_read_external_storage), EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }else {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

        binding.button.setOnClickListener(v -> {
            showLoading();
            mViewModel.validateAndSave(gp.getUserAuth(),filePath);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = getPath(getContext(),fileUri);
                    binding.imgText.setText(getFileName(getContext(),fileUri));
                }

                break;
        }
    }

    @Override
    public void showToast(String msg) {
        hideLoading();
        Toast.makeText(getContext(), getString(R.string.wrong_cert), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toNextActivity() {
        hideLoading();
        mListener.onUserCertDone();
    }


    public interface onUserInteract{
        void onUserCertDone();
    }
}