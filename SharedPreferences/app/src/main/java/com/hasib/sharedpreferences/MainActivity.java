package com.hasib.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.hasib.sharedpreferences", Context.MODE_PRIVATE);

//        List<String> stringList = new ArrayList<>(Arrays.asList("Toufiq", "Wadud", "Partho", "Antor"));
//
//        try {
//            sharedPreferences.edit().putString("stringList", ObjectSerializer.serialize((Serializable) stringList)).apply();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        List<String> newStringList = new ArrayList<>();
        try {
            newStringList = (ArrayList) ObjectSerializer.deserialize(sharedPreferences.getString("stringList", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("newStringList", newStringList.toString());

//        sharedPreferences.edit().putString("username", "Hasib").apply();
//
//        Log.i("username", sharedPreferences.getString("username", ""));
    }
}
