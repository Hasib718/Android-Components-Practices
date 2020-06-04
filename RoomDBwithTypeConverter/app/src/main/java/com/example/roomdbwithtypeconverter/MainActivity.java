package com.example.roomdbwithtypeconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HashMap<String, Boolean> activeDays = new HashMap<>();

        activeDays.put("Sunday", false);
        activeDays.put("Monday", false);
        activeDays.put("Tuesday", false);
        activeDays.put("Wednesday", false);
        activeDays.put("Thursday", false);
        activeDays.put("Friday", false);
        activeDays.put("Saturday", false);

        AppExecutor
                .getInstance()
                .getDiskIO()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabases
                                .getInstance(MainActivity.this)
                                .getPersonDatabase()
                                .personDao()
                                .insertPerson(new Person("Name", "Dhaka", activeDays));
                    }
                });

        AppExecutor
                .getInstance()
                .getDiskIO()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Person> test = AppDatabases
                                .getInstance(MainActivity.this)
                                .getPersonDatabase()
                                .personDao()
                                .loadAllPersons();

                        Log.d(TAG, "run: "+test.get(0).getActiveDays().get("Tuesday"));
                    }
                });
    }
}