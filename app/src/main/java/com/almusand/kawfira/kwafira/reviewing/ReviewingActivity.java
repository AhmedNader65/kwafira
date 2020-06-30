package com.almusand.kawfira.kwafira.reviewing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.almusand.kawfira.R;
import com.almusand.kawfira.utils.GlobalPreferences;

public class ReviewingActivity extends AppCompatActivity {


    public ReviewingActivity() {
        super(R.layout.activity_reviewing);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ReviewingActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GlobalPreferences(this).storeUserCertSent();
    }
}