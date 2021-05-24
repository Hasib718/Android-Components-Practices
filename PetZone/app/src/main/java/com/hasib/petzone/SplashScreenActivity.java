package com.hasib.petzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i=1; i<=100; i++) {
                        Thread.sleep(20);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                if (sharedPreferences.contains("active") && sharedPreferences.getBoolean("active", false)) {
//                    if (sharedPreferences.contains("category") && sharedPreferences.getString("category", "CUSTOMER").equalsIgnoreCase("CUSTOMER")) {
//                        Intent intent = new Intent(SplashScreenActivity.this, CustomerActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    }
//                } else {
//                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                }
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
            }
        }).start();
    }
}