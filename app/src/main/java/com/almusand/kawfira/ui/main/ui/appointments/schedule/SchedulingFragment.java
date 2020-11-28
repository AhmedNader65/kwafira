package com.almusand.kawfira.ui.main.ui.appointments.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.R;
import com.almusand.kawfira.ViewModelProviderFactory;
import com.almusand.kawfira.databinding.FragmentSchedulingBinding;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SchedulingFragment extends BaseFragment<FragmentSchedulingBinding, SchedulingViewModel> implements ScheduleNavigator {
    private SchedulingViewModel mSchedulingViewModel;
    private FragmentSchedulingBinding mFragmentBinding;
    ViewModelProviderFactory factory;
    String date,time;

    private OnSchedulingFragmentInteractionListener mListener;
    final Calendar myCalendar = Calendar.getInstance();

    public SchedulingFragment() {
        // Required empty public constructor
    }

    public static SchedulingFragment newInstance(String param1) {
        SchedulingFragment fragment = new SchedulingFragment();

        Bundle args = new Bundle();
        args.putString("id", param1);
        fragment.setArguments(args);
        return fragment;
    }

    String reservationId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reservationId = getArguments().getString("id");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment

        mFragmentBinding = getViewDataBinding();
        mSchedulingViewModel.setNavigator(this);


        mFragmentBinding.confirmDate.setOnClickListener(v -> validateDate());
        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };


        mFragmentBinding.dateContainer.setOnClickListener(v -> {
           DatePickerDialog datePicker = new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
           datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
           datePicker.show();
        });

        mFragmentBinding.timeContainer.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    Date date1 = null;
                    try {
                        time = selectedHour+":"+selectedMinute;
                        date1 = new SimpleDateFormat("HH:mm",new Locale("ar")).parse(selectedHour+":"+selectedMinute);
                        String timeOfDay = (String) DateFormat.format("hh:mm", date1); // Thursday
                        String AMOrPM = (String) DateFormat.format("a", date1); // Thursday
                        mFragmentBinding.time.setText(timeOfDay + " "+ CommonUtils.getAMORPMInAR(new GlobalPreferences(getContext()).getLanguage(),AMOrPM));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, hour, minute, false);//Yes 24 hour time

            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });
    }

    private void validateDate() {

        mSchedulingViewModel.validateDate(mFragmentBinding.date.getText().toString()
                ,getString(R.string.default_date)
                ,mFragmentBinding.time.getText().toString()
                ,getString(R.string.default_time),reservationId,new GlobalPreferences(getContext()).getUserAuth()
        ,date,time);
    }

    private void updateLabel() {

        String myFormat = "EEE - dd MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("ar"));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        date =sdf2.format(myCalendar.getTime());
        mFragmentBinding.date.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public int getBindingVariable() {
        return com.almusand.kawfira.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scheduling;
    }

    @Override
    public SchedulingViewModel getViewModel() {

        mSchedulingViewModel =ViewModelProviders.of(requireActivity()).get(SchedulingViewModel.class);
        return mSchedulingViewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnSchedulingFragmentInteractionListener)
            mListener = (OnSchedulingFragmentInteractionListener) getParentFragment();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void showDateToast() {
        Toast.makeText(getContext(), R.string.choose_date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTimeToast() {
        Toast.makeText(getContext(), R.string.choose_time, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void finishFragment() {
        mListener.onSchedulingFragmentInteraction();
    }

    public interface OnSchedulingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSchedulingFragmentInteraction();
    }
}
