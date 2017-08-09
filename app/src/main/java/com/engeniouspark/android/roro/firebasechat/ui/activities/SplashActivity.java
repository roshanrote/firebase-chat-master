package com.engeniouspark.android.roro.firebasechat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.engeniouspark.android.roro.firebasechat.R;
import com.engeniouspark.android.roro.firebasechat.utils.Constants;
import com.engeniouspark.android.roro.firebasechat.utils.SessionData;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_MS = 2000;
    private Handler mHandler;
    private Runnable mRunnable;
    SessionData sessionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionData = new SessionData(SplashActivity.this);

        mHandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                // check if user is already logged in or not
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // if logged in redirect the user to user listing activity

                    if (sessionData.getObjectAsString("user").equals("admin"))
                    {
                        UserListingActivity.startActivity(SplashActivity.this);
                    }
                    else
                    {


                        Intent intent = new Intent(SplashActivity.this, ChatActivity.class);
                        intent.putExtra(Constants.ARG_RECEIVER, "mahendra@gmail.com");
                        intent.putExtra(Constants.ARG_RECEIVER_UID, "RWO8u8lES4hTvQ8cFsCLIXsfl8e2");
                        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, "djhXW4d4bmw:APA91bEITnLW-KAezYHzlUANYZA2-2iCe96po3KEt5wDJRJTP9lwzF6Hss6rnZ3qextofV-53O42S5BKNcRLa9c7Sntz6q6wls6nvGKSAAvtVJpjGXLdqi8eztThoRiswkvBzIStmFb9");
                        startActivity(intent);
                    }


                } else {
                    // otherwise redirect the user to login activity
                    LoginActivity.startIntent(SplashActivity.this);
                }
                finish();
            }
        };

        mHandler.postDelayed(mRunnable, SPLASH_TIME_MS);
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, SPLASH_TIME_MS);
    }*/
}
