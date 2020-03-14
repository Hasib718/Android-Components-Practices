package com.hasib.changingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void nextActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);

        //sharing data between activities
        intent.putExtra("message", "2nd Layout");

        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
