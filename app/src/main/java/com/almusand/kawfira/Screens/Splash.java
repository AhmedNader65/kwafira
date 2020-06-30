package com.almusand.kawfira.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.almusand.kawfira.R;
import com.almusand.kawfira.kwafira.home.KwafiraMainActivity;
import com.almusand.kawfira.kwafira.identity.VerifyIdActivity;
import com.almusand.kawfira.kwafira.reviewing.ReviewingActivity;
import com.almusand.kawfira.ui.kwafiraReviewProfile.KwafiraRevProfile;
import com.almusand.kawfira.ui.login.LoginActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.map.MapActivity;
import com.almusand.kawfira.utils.GlobalPreferences;

public class Splash extends AppCompatActivity {

    Thread splash_thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash);

//        threead();

        Intent i;
        if ((new GlobalPreferences(getApplicationContext())).getUSER_Logged()) {
            if ((new GlobalPreferences(getApplicationContext())).getUserRole().equals("client"))
                i = new Intent(Splash.this, HomeActivity.class);
            else if (new GlobalPreferences(getApplicationContext()).isKawfiraVerfied())
                i = new Intent(Splash.this, KwafiraMainActivity.class);
            else if (new GlobalPreferences(getApplicationContext()).isCertSent())
                i = new Intent(Splash.this, ReviewingActivity.class);
            else
                i = new Intent(Splash.this, VerifyIdActivity.class);
        } else {
            i = new Intent(Splash.this, LoginActivity.class);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        Splash.this.finish();
    }


//    public void threead() {
//        splash_thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    int waited = 0;
//                    // Splash screen pause time
//                    while (waited < 2500) {
//                        sleep(100);
//                        waited += 100;
//                    }
//                    Intent i;
//                    if ((new GlobalPreferences(getApplicationContext())).getUSER_Logged()) {
//                        if ((new GlobalPreferences(getApplicationContext())).getUserRole().equals("client"))
//                            i = new Intent(Splash.this, HomeActivity.class);
//                        else
//                            i = new Intent(Splash.this, VerifyIdActivity.class);
//                    } else {
//                        i = new Intent(Splash.this, LoginActivity.class);
//                    }
//                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(i);
//                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                    Splash.this.finish();
//                } catch (InterruptedException e) {
//                    // do nothing
//                } finally {
//                    Splash.this.finish();
//                }
//
//            }
//        };
//        splash_thread.start();
//    }
}
