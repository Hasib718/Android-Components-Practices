package com.hasib.timestable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private ListView listView;
    private List<Integer> numbersList = new ArrayList<>(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    private List<Integer> timesList = new ArrayList<>(numbersList);
    private ArrayAdapter arrayAdapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        listView = (ListView) findViewById(R.id.timesListView);
        textView = (TextView) findViewById(R.id.textView);

        seekBar.setProgress(10);
        textView.setText(Integer.toString(10));
        arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, timesList);
        listView.setAdapter(arrayAdapter);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    textView.setText(String.valueOf(progress));
                    for (int i=0; i<numbersList.size(); i++) {
                        timesList.set(i, numbersList.get(i)*progress);
//                        Log.i("Changing", Integer.toString(timesList.get(i)));
                    }
                }

                arrayAdapter = new ArrayAdapter(MainActivity.this,
                        android.R.layout.simple_list_item_1, timesList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
