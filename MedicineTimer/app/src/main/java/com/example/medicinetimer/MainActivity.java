package com.example.medicinetimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.medicinetimer.adapters.RecyclerViewAdapter;
import com.example.medicinetimer.container.Medicine;
import com.example.medicinetimer.listeners.OnMedicineListClickEvents;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMedicineListClickEvents {

    private static final String TAG = "MainActivity";

    private FloatingActionButton medicineAddingButton;

    private List<Medicine> medicines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();

        medicineAddingButton = findViewById(R.id.medicineAddingButton);
        medicineAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MedicineAddingActivity.class));
            }
        });
    }

    private void initRecyclerView() {
        medicines.add(new Medicine("Test", true, new ArrayList<String>(Arrays.asList("8:00PM", "9:00PM")), "WEd", "Today"));

        RecyclerView medicineList = findViewById(R.id.medicineList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, medicines);
        medicineList.setAdapter(adapter);
        medicineList.setLayoutManager(new LinearLayoutManager(this));

        adapter.setMedicineListClickEvents(this);
    }

    @Override
    public void onMedicineClickListener(int position, Medicine medicine) {
        Log.d(TAG, "onMedicineClickListener: "+position);
    }
}
