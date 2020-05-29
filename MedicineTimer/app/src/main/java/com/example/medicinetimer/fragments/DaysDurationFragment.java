package com.example.medicinetimer.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.medicinetimer.R;
import com.example.medicinetimer.listeners.OnPickerDialogActionButtonEvents;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DaysDurationFragment extends AppCompatDialogFragment {

    private static final String TAG = "DaysDurationFragment";

    private Context mContext;
    private String days;

    private TextInputEditText daysCount;
    private MaterialButton cancelButton, setButton;
    private AppCompatImageButton minusButton, plusButton;

    private OnPickerDialogActionButtonEvents onPickerDialogActionButtonEvents;

    public DaysDurationFragment(Context mContext, String days) {
        this.mContext = mContext;
        this.days = days;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_duration_days, null);

        initViews(view);
        setDoseCounter();
        setActionButtons();
    }

    public OnPickerDialogActionButtonEvents setOnPickerDialogActionButtonEvents(OnPickerDialogActionButtonEvents onPickerDialogActionButtonEvents) {
        this.onPickerDialogActionButtonEvents = onPickerDialogActionButtonEvents;
    }

    private void setActionButtons() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickerDialogActionButtonEvents.onCancel();
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickerDialogActionButtonEvents.onSet();
            }
        });
    }

    private void setDoseCounter() {
        doseCount.setText(String.format("%.2f", medicineDose.getDoseCount()));

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double dose = Double.parseDouble(doseCount.getText().toString());
                dose -= 0.25;

                if (dose < 0) {
                    dose = 0.00;
                    doseCount.setText(String.format("%.2f", dose));
                    Toast.makeText(mContext, "Dose can't be less then "+dose, Toast.LENGTH_SHORT).show();
                } else {
                    doseCount.setText(String.format("%.2f", dose));
                }
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double dose = Double.parseDouble(doseCount.getText().toString());
                dose += 0.25;

                if (dose > 100) {
                    dose = 100.00;
                    doseCount.setText(String.format("%.2f", dose));
                    Toast.makeText(mContext, "Dose can't be less then "+dose, Toast.LENGTH_SHORT).show();
                } else {
                    doseCount.setText(String.format("%.2f", dose));
                }
            }
        });
    }


    private void initViews(View view) {
        daysCount = view.findViewById(R.id.daysCount);
        minusButton = view.findViewById(R.id.minusButton);
        plusButton = view.findViewById(R.id.plusButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        setButton = view.findViewById(R.id.setButton);
    }
}
