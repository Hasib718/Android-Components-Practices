package com.example.spinnerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] select_qualification = {
                "Select Qualification", "10th / Below", "12th", "Diploma", "UG",
                "PG", "Phd"};
        AppCompatSpinner spinner = findViewById(R.id.spinner);

        ArrayList<State> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            State stateVO = new State();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        SpinnerAdapter myAdapter = new SpinnerAdapter(MainActivity.this, 0,
                listVOs);
        spinner.setAdapter(myAdapter);
    }
}
