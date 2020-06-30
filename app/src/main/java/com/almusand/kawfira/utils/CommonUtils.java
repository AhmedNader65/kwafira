package com.almusand.kawfira.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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

    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Log.i("URI",uri+"");
        String result = uri+"";
        // DocumentProvider
        //  if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isKitKat && (result.contains("media.documents"))) {
            String[] ary = result.split("/");
            int length = ary.length;
            String imgary = ary[length-1];
            final String[] dat = imgary.split("%3A");
            final String docId = dat[1];
            final String type = dat[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
            } else if ("audio".equals(type)) {
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    dat[1]
            };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String getFileName(Context context,Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
