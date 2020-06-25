package com.almusand.kawfira.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.almusand.kawfira.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by amitshekhar on 07/07/17.
 */

public final class CommonUtils {
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    private CommonUtils() {

    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static boolean isPhoneValid(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static void countDownTimer(TextView view,long millis,TextView resendBtn){
        new CountDownTimer(millis, 1000) {

            public void onTick(long millisUntilFinished) {
                (view).setText("الوقت المتبقى للارسال مرة اخرى " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                view.setVisibility(View.GONE);
                resendBtn.setEnabled(true);
            }

        }.start();
    }

    public static Date stringToDate(String dtStart) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ar"));
        Date date = format.parse(dtStart);
        return date;
    }

    public static String getAMORPMInAR(String amOrPM) {
        Log.e("amOrPm",amOrPM);
        String amOrPm = amOrPM;
            switch (amOrPM){
                case "AM":
                    return  "صباحاً";
                case "PM":
                    return "مساءً";
                case "am":
                    return  "صباحاً";
                case "pm":
                    return "مساءً";
            }
        Log.e("amOrPm","returning");
        return amOrPm;
    }

    public static String getDateInAr(String dayOfTheWeek) {
        switch (dayOfTheWeek){
            case "Saturday":
                return "السبت";
            case "Sunday":
                return "الأحد";
            case "Monday":
                return "الإثنين";
            case "Tuesday":
                return "الثلاثاء";
            case "Wednesday":
                return "الأربعاء";
            case "Thursday":
                return "الخميس";

            case "Friday":
                return "الجمعة";

        }
        return dayOfTheWeek;
    }

    public static String getMonthInAr(String month) {
        switch (month){
            case "Jan":
                return "يناير";
            case "Feb":
                return "فبراير";
            case "Mar":
                return "مارس";
            case "Apr":
                return "أبريل";
            case "May":
                return "مايو";
            case "Jun":
                return "يونيو";
            case "Jul":
                return "يوليو";
            case "Aug":
                return "أغسطس";
            case "Sep":
                return "سبتمبر";
            case "Oct":
                return "اكتوبر";
            case "Nov":
                return "نوفمبر";
            case "Dec":
                return "ديسمبر";
        }
        return month;
    }
}
