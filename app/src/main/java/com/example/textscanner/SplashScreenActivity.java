package com.example.textscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private final Timer timer = new Timer();
    private boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        active = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (active) {
//                    if (ParserUser.getCurrentUser() == null) {
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);

//                    } else {
//                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
                    finish();
                }
            }
        }, 5000);
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}