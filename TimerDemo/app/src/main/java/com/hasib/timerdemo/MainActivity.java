package com.hasib.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(11000, 1000) {
            public void onTick(long milliSecondUntilDone) {
                //Count down is counting down(every second)

                Log.i("Seconds Left", String.valueOf(milliSecondUntilDone/1000));
            }

            public void onFinish() {
                //Counter is finished

                Log.i("Done!", "Count down Timer Finished");
            }
        }.start();

//        final Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                //Insert code to be run every second
//
//                Log.i("Runnable has run!", "a second must have passed...");
//
//                handler.postDelayed(this, 1000);
//            }
//        };
//
//        handler.post(run);
    }
}
