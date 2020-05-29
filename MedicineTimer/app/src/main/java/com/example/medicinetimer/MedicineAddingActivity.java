package com.example.medicinetimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinetimer.adapters.TimeDoseRecyclerViewAdapter;
import com.example.medicinetimer.container.Medicine;
import com.example.medicinetimer.container.MedicineDose;
import com.example.medicinetimer.fragments.ReminderDialogFragment;
import com.example.medicinetimer.fragments.TimeDosePickerDialogFragment;
import com.example.medicinetimer.listeners.OnPickerDialogActionButtonEvents;
import com.example.medicinetimer.listeners.OnTimeListClickEvents;
import com.example.medicinetimer.listeners.OnTimeListItemSelectionEvents;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;


public class MedicineAddingActivity extends AppCompatActivity implements OnTimeListClickEvents,
        TimePicker.OnTimeChangedListener, OnPickerDialogActionButtonEvents {

    private static final String TAG = "MedicineAddingActivity";

    private TextInputLayout medicineNameInputLayout;
    private TextInputEditText medicineNameInput;
    private MaterialTextView medicineTimeInput, medicineStartDateInput;

    private RecyclerView medicineTimeDoseRecyclerView;
    private TimeDoseRecyclerViewAdapter adapter;
    private ArrayList<MedicineDose> selectedMedication = new ArrayList<>(Arrays.asList(new MedicineDose("8:00 AM", 1.00)));
    private ArrayList<ArrayList<String>> timeTable = new ArrayList<>();

    private RadioGroup durationRadioGroup, daysRadioGroup;

    private MenuItem saveOption;

    private Medicine medicine = new Medicine();
    private MedicineDose medicineDose = new MedicineDose();
    private int position;

    private ReminderDialogFragment dialogFragment;

    private OnTimeListItemSelectionEvents onTimeListItemSelectionEvents;

    private TimeDosePickerDialogFragment timeDialogFragment;

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
                timeTable.clear();
                retrieveTimeTableData();

                dialogFragment = new ReminderDialogFragment(MedicineAddingActivity.this, timeTable.get(0));
                dialogFragment.show(getSupportFragmentManager(), "Reminder Time");
            }
        });

    }

    private void retrieveTimeTableData() {
        try (InputStream is = getAssets().open("Time Table.json")) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject object = new JSONObject(json);

            for (int i = 0; i < object.getJSONArray("iterator").length(); i++) {
                String[] array = object.getJSONArray(object.getJSONArray("iterator").getString(i)).join(",").split("\",\"");
                array[0] = array[0].replace("\"", "");
                array[array.length - 1] = array[array.length - 1].replace("\"", "");
                timeTable.add(new ArrayList<String>(Arrays.asList(array)));
            }
        } catch (IOException | JSONException e) {
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
        adapter = new TimeDoseRecyclerViewAdapter(this, selectedMedication);
        medicineTimeDoseRecyclerView.setAdapter(adapter);
        medicineTimeDoseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnTimeListClickEvents(this);
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

                retrieveTimeDoseTableData(selected);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onTimeDoseClickListener(int position, MedicineDose medicineDose) {
        Log.d(TAG, "onTimeDoseClickListener: " + medicineDose.toString());

        timeDialogFragment = new TimeDosePickerDialogFragment(this, medicineDose);
        timeDialogFragment.show(getSupportFragmentManager(), "Picker");

        timeDialogFragment.setOnPickerDialogActionButtonEvents(MedicineAddingActivity.this);
    }

    private void retrieveTimeDoseTableData(@NotNull String selected) {
        try (InputStream is = getAssets().open("Time Dose Table.json")) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject object = new JSONObject(json);
            JSONArray array;

            if (selected.matches("(.*)day(.*)")) {
                array = object.getJSONArray("frequency");
            } else {
                array = object.getJSONArray("intervals");
            }

            selectedMedication.clear();
            for (int j = 0; j < array.length(); j++) {
                if (array.getJSONObject(j).has(selected)) {
                    for (int i = 0; i < array.getJSONObject(j).getJSONArray(selected).length(); i++) {
                        selectedMedication.add(new MedicineDose(array.getJSONObject(j).getJSONArray(selected).getJSONObject(i).getString("time"),
                                array.getJSONObject(j).getJSONArray(selected).getJSONObject(i).getDouble("dose")));
                    }
                }
            }
            Log.d(TAG, "retrieveTimeDoseTableData: " + selectedMedication.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void setOnTimeListItemSelectionEvents(OnTimeListItemSelectionEvents onTimeListItemSelectionEvents) {
        this.onTimeListItemSelectionEvents = onTimeListItemSelectionEvents;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        medicineDose.setTime(new StringBuilder()
                .append(typeCastedHour(hourOfDay))
                .append(":")
                .append(precedingZero(minute))
                .append(" ")
                .append(getConvention(hourOfDay))
                .toString());
    }

    @NotNull
    private String precedingZero(int minute) {
        if (minute < 10) {
            return Integer.toString(minute);
        } else {
            return "0"+minute;
        }
    }

    @NotNull
    @Contract(pure = true)
    private String getConvention(int hourOfDay) {
        if (hourOfDay > 12) {
            return " PM";
        } else if(hourOfDay == 0) {
            return " AM";
        } else {
            return " AM";
        }
    }

    @NotNull
    private String typeCastedHour(int hourOfDay) {
        if (hourOfDay > 12) {
            return Integer.toString(hourOfDay-12);
        } else if(hourOfDay == 0) {
            return Integer.toString(12);
        } else {
            return Integer.toString(hourOfDay);
        }
    }

    @Override
    public void onCancel() {
        timeDialogFragment.dismiss();
    }

    @Override
    public void onSet(String dose) {
        medicineDose.setDose(dose);
        selectedMedication.set(position, medicineDose);
        adapter.notifyDataSetChanged();
        timeDialogFragment.dismiss();
    }
}
