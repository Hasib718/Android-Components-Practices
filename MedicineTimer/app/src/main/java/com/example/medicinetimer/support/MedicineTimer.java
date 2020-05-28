package com.example.medicinetimer.support;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicineTimer extends Application {

    private static final String TAG = "MedicineTimer";

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("timeTable", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("exist", true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            List<String> timeTableStart = new ArrayList<>(Arrays.asList("Frequency", "Once a day", "Twice a day", "3 times a day", "......",
                    "Intervals", "Every hours", "Every 2 hours", "Every 3 hours", "......"));
            List<String> timeTableFreq = new ArrayList<>(Arrays.asList("Frequency", "Once a day", "Twice a day", "3 times a day", "4 times a day",
                    "5 times a day", "6 times a day", "7 times a day", "8 times a day", "9 times a day", "10 times a day", "11 times a day", "12 times a day"));
            List<String> timeTableInt = new ArrayList<>(Arrays.asList("Interval", "Every hour", "Every 2 hours", "Every 3 hours", "Every 4 hours",
                    "Every 5 hours", "Every 6 hours", "Every 7 hours", "Every 8 hours", "Every 9 hours", "Every 10 hours", "Every 11 hours", "Every 12 hours"));

            editor.putBoolean("exist", false);

            try {
                editor.putString("timeTableStart", ObjectSerializer.serialize((Serializable) timeTableStart));
                editor.putString("timeTableFreq", ObjectSerializer.serialize((Serializable) timeTableFreq));
                editor.putString("timeTableInt", ObjectSerializer.serialize((Serializable) timeTableInt));

                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
