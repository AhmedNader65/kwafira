package com.almusand.kawfira.kwafira.home.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.kwafira.home.HomeActivityViewModel;
import com.almusand.kawfira.kwafira.home.ui.home.bottomsheet.BottomSheetFragment;
import com.almusand.kawfira.kwafira.home.ui.home.orders.OrdersAdapter;
import com.almusand.kawfira.kwafira.home.ui.home.orders.ordersFragment;
import com.almusand.kawfira.kwafira.home.ui.home.status.UnavailableFragment;
import com.almusand.kawfira.kwafira.orderProcess.timer.KwafiraCounter;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.util.List;


public class HomeKwafiraFragment extends Fragment implements GestureDetector.OnGestureListener, View.OnTouchListener {
    private GestureDetector gestureDetector;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HomeActivityViewModel viewModel;

    private String mParam1;
    private String mParam2;
    private FragmentTransaction fragmentTransaction;
    private LinearLayout layout;
    TextView acceptedORderNum;
    OrderModel acceptedModel;
    GlobalPreferences gp;

    public HomeKwafiraFragment() {
        // Required empty public constructor
    }


    public static HomeKwafiraFragment newInstance(String param1, String param2) {
        HomeKwafiraFragment fragment = new HomeKwafiraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        gp = new GlobalPreferences(getContext());
        viewModel = ViewModelProviders.of(requireActivity()).get(HomeActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_kwafira, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gestureDetector = new GestureDetector(getContext(), this);
        layout = view.findViewById(R.id.swipeup_status);
        acceptedORderNum = view.findViewById(R.id.orderNum);
        viewModel.getCurrentOrder(new GlobalPreferences(getContext()).getUserAuth(), "incomplete");
        viewModel.getAcceptedOrder().observe(getViewLifecycleOwner(), orderUpdateObserver);
        viewModel.getUserData().observe(getViewLifecycleOwner(), userObserver);

        viewModel.isAvailable().observe(getViewLifecycleOwner(), aBoolean -> {


            chooseFragment(aBoolean);

        });

    }

    Observer<List<OrderModel>> orderUpdateObserver = resList -> {
        layout.setOnTouchListener(this);
        if (resList.size() > 0) {
            viewModel.setIsAvailable(false, gp.getUserAuth());
            acceptedModel = resList.get(0);
            acceptedORderNum.setText("طلبات قيد التنفيذ  (" + (resList.size() >= 1 ? 1 : 0) + ")");
            if (acceptedModel.getStatus().equals("payment")) {
                startActivity(KwafiraCounter.newIntent(getContext()).putExtra("type", "payment").putExtra("order", acceptedModel));
            }
        }

    };
    private User user;
    Observer<User> userObserver = user -> {
        this.user = user;
        chooseFragment(user.getAvailable().equals("1"));
    };

    private void chooseFragment(boolean aBoolean) {
        FragmentManager fm = getChildFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        if (aBoolean) {

            Fragment oldFragment = fm.findFragmentByTag(ordersFragment.class.getName());
            if (oldFragment == null)
                fragmentTransaction.replace(R.id.statusContainer, ordersFragment.newInstance(), ordersFragment.class.getName());
//            try {
//                ordersFragment ordersFragment = (ordersFragment) getChildFragmentManager().getFragments().get(1);
//            }catch (Exception e) {
//                e.printStackTrace();
//                getChildFragmentManager().beginTransaction().remove(getChildFragmentManager().getFragments().get(1));
//            }
        } else {
            Fragment oldFragment = getChildFragmentManager().findFragmentByTag(UnavailableFragment.class.getName());
            if (oldFragment == null) {
                fragmentTransaction.replace(R.id.statusContainer, UnavailableFragment.newInstance(user), UnavailableFragment.class.getName());
                Log.e("user", user == null ? "NULL" : "not null");
            }
        }

        fragmentTransaction.commit();
        fm.executePendingTransactions();
    }


    public void showBottomSheet() {
        Fragment oldFragment = getChildFragmentManager().findFragmentByTag("BOttom sheet");
        if (oldFragment == null) {
            BottomSheetFragment dialogFragment;
            if (acceptedModel != null) {
                dialogFragment = BottomSheetFragment.newInstance(acceptedModel);
            } else {

                dialogFragment = BottomSheetFragment.newInstance();
            }
            dialogFragment.show(getChildFragmentManager(), "BOttom sheet");

        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (distanceY > 0) {
            // you are going up
            try {
                if (acceptedModel != null)
                    showBottomSheet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // you are going down
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

}