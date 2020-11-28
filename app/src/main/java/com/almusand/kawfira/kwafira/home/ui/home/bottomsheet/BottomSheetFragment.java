package com.almusand.kawfira.kwafira.home.ui.home.bottomsheet;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;

import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.chat.ChatActivity;
import com.almusand.kawfira.databinding.FragmentCurrentOrderBinding;
import com.almusand.kawfira.kwafira.home.ui.KwafiraMapsActivity;
import com.almusand.kawfira.kwafira.home.ui.home.orders.KwafiraOrderViewModel;
import com.almusand.kawfira.kwafira.home.ui.home.orders.Navigator;
import com.almusand.kawfira.kwafira.orderProcess.OrderMainServices;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import pub.devrel.easypermissions.EasyPermissions;

public class BottomSheetFragment extends BottomSheetDialogFragment implements BottomSheetNavigator, EasyPermissions.PermissionCallbacks, Navigator {

    private static final int CALL_PERMISSION_REQUEST_CODE = 4;
    // TODO: Customize parameter argument names
    OrderModel order;
    private FragmentCurrentOrderBinding itemBinding;
    private BottomSheetViewModel viewModel;
    private KwafiraOrderViewModel viewModel2;

    // TODO: Customize parameters
    public static BottomSheetFragment newInstance() {
        final BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }

    public static BottomSheetFragment newInstance(OrderModel orderModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orderModel);
        final BottomSheetFragment fragment = new BottomSheetFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        try {
            order = (OrderModel) getArguments().getSerializable("order");
        } catch (NullPointerException e) {
            order = null;
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        itemBinding = FragmentCurrentOrderBinding.inflate(
                inflater, container, false);

        return itemBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (order != null) {
            viewModel = new BottomSheetViewModel(order,getContext());
            viewModel2 = new KwafiraOrderViewModel(null,null);
            viewModel2.setNavigator2(this);
            viewModel.setNavigator(this);
            itemBinding.executePendingBindings();
            itemBinding.setViewModel(viewModel);
            User client = order.getClient();
            if (client.getImage() != null) {
                Picasso.get().load(client.getImage()).placeholder(R.drawable.userphoto).into(itemBinding.img);
            }
            itemBinding.orderNum.setText(R.string.pendingOrder);
            itemBinding.accept.setOnClickListener(v -> {
                startActivity( OrderMainServices.newIntent(getContext()).putExtra("order",order));
            });
            itemBinding.chat.setOnClickListener(v -> {
                getContext().startActivity(new Intent(getContext(),ChatActivity.class)
                .putExtra("id",order.getClient().getId())
                .putExtra("name",order.getClient().getName())
                .putExtra("img",order.getClient().getImage()));
            });
            itemBinding.call.setOnClickListener(v -> {
                if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.CALL_PHONE)) {
                    EasyPermissions.requestPermissions((Activity) getContext(), getContext().getString(R.string.permission_call_required_toast), CALL_PERMISSION_REQUEST_CODE, Manifest.permission.CALL_PHONE);
                } else {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + order.getClient().getPhone()));
                    getContext().startActivity(intent);
                }
            });
            int id = itemBinding.serviceLL.getId();
            int oldId = itemBinding.serviceLL.getId();

            for (int i = 1; i < order.getServices_data().length; i++) {
                ServicesModel service = order.getServices_data()[i];
                View v = LayoutInflater.from(getContext()).inflate(R.layout.service_layout, null);
                TextView serviceName = v.findViewById(R.id.serviceName);
                serviceName.setText(service.getTitle_ar());
                serviceName.setId(itemBinding.serviceName.getId() + i);
                Random rng = new Random();
                id =rng.nextInt() & Integer.MAX_VALUE;
//                if(v.findViewById(id)!=null){
//                    id =  rng.nextInt() & Integer.MAX_VALUE;
//                }
                v.setId(id);
                itemBinding.constraintLayout.addView(v, 0);
                ConstraintSet set1 = new ConstraintSet();
                set1.clone(itemBinding.constraintLayout);
                set1.connect(v.getId(), ConstraintSet.TOP, oldId, ConstraintSet.BOTTOM, 0);
////                    set1.connect(v.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
                if(new GlobalPreferences(getContext()).getLanguage().equals("en")) {
                    set1.connect(v.getId(), ConstraintSet.LEFT, oldId, ConstraintSet.LEFT, 0);
                }else{
                    set1.connect(v.getId(), ConstraintSet.RIGHT, oldId, ConstraintSet.RIGHT, 0);
//
                }
//                //                    set1.connect(mImage.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
                set1.applyTo(itemBinding.constraintLayout);
//
                oldId = id;
            }
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(itemBinding.constraintLayout);
            constraintSet.connect(R.id.map, ConstraintSet.TOP, id, ConstraintSet.BOTTOM, 0);
            constraintSet.applyTo(itemBinding.constraintLayout);
        }

        Log.e("fdsfs","fdsfds");
    }


    @Override
    public void refresh() {
        Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOnMap() {

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void viewOnMap() {
        getContext().startActivity(KwafiraMapsActivity.newIntent(getContext()).putExtra("lat", order.getLatitude()).putExtra("lng", order.getLongitude()));

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + order.getClient().getPhone()));

            getContext().startActivity(intent);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
