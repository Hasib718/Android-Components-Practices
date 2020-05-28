package com.example.medicinetimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.example.medicinetimer.adapters.TimeDoseRecyclerViewAdapter;
import com.example.medicinetimer.container.Medicine;
import com.example.medicinetimer.container.MedicineDose;
import com.example.medicinetimer.fragments.ReminderDialogFragment;
import com.example.medicinetimer.listeners.OnTimeListClickEvents;
import com.example.medicinetimer.listeners.OnTimeListItemSelectionEvents;
import com.example.medicinetimer.support.ObjectSerializer;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;


public class MedicineAddingActivity extends AppCompatActivity implements OnTimeListClickEvents {

    private static final String TAG = "MedicineAddingActivity";

    private TextInputLayout medicineNameInputLayout;
    private TextInputEditText medicineNameInput;
    private MaterialTextView medicineTimeInput, medicineStartDateInput;

    private RecyclerView medicineTimeDoseRecyclerView;
    private TimeDoseRecyclerViewAdapter adapter;
    private ArrayList<MedicineDose> medicineDoses = new ArrayList<>(Arrays.asList(new MedicineDose("8:00 AM", 1.00)));
    private ArrayList<ArrayList<String>> timeTable = new ArrayList<>();
    private ArrayList<ArrayList<String>> timeTableClone;

    private RadioGroup durationRadioGroup, daysRadioGroup;

    private MenuItem saveOption;

    private Medicine medicine = new Medicine();

    private ReminderDialogFragment dialogFragment;

    private OnTimeListItemSelectionEvents onTimeListItemSelectionEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_adding);

        initViews();

        String date = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        medicineStartDateInput.setText(date);
        medicine.setStartingDay(date);

        medicineTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+timeTable);
                timeTable.clear();

                retrieveData();

                dialogFragment = new ReminderDialogFragment(MedicineAddingActivity.this, timeTable.get(0));
                dialogFragment.show(getSupportFragmentManager(), "Reminder Time");
            }
        });

    }

    private void retrieveData() {
        SharedPreferences sharedPreferences = MedicineAddingActivity.this.getSharedPreferences("timeTable", Context.MODE_PRIVATE);
        try {
            timeTable.add((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("timeTableStart", null)));
            timeTable.add((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("timeTableFreq", null)));
            timeTable.add((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("timeTableInt", null)));

            Log.d(TAG, "onCreate: "+timeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        medicineNameInputLayout = findViewById(R.id.nameTextInputLayout);
        medicineNameInput = findViewById(R.id.medicineNameInput);
        medicineTimeInput = findViewById(R.id.reminderTimeInput);
        medicineStartDateInput = findViewById(R.id.medicineStartDateInput);

        initTimeDoseRecyclerView();

        durationRadioGroup = findViewById(R.id.radioGroup1);
        daysRadioGroup = findViewById(R.id.radioGroup2);
    }

    private void initTimeDoseRecyclerView() {
        medicineTimeDoseRecyclerView = findViewById(R.id.timeDoseRecyclerViewTable);
        adapter = new TimeDoseRecyclerViewAdapter(this, medicineDoses);
        medicineTimeDoseRecyclerView.setAdapter(adapter);
        medicineTimeDoseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items_medicine_adding, menu);

        saveOption = menu.findItem(R.id.item_save);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_save:
                startActivity(new Intent(MedicineAddingActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeClickListener(int position, String selected) {
        Log.d(TAG, "onTimeClickListener: "+selected);

        if (position == 4 && selected.equals("......")) {
            onTimeListItemSelectionEvents.onItemSelectedListener(timeTable.get(1));
        } else if (position == 9 && selected.equals("......")) {
            onTimeListItemSelectionEvents.onItemSelectedListener(timeTable.get(2));
        }else if (!selected.equals("Frequency")) {
            if (!selected.equals("Intervals")) {
                medicineTimeInput.setText(selected);
                dialogFragment.dismiss();
            }
        }
    }

    public void setOnTimeListItemSelectionEvents(OnTimeListItemSelectionEvents onTimeListItemSelectionEvents) {
        this.onTimeListItemSelectionEvents = onTimeListItemSelectionEvents;
    }
}
