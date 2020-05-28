package com.example.medicinetimer.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.medicinetimer.R;
import com.example.medicinetimer.container.MedicineDose;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class TimeDosePickerDialogFragment extends AppCompatDialogFragment {

    private static final String TAG = "TimeDosePickerDialogFragment";

    private Context mContext;
    private MedicineDose medicineDose;

    private TimePicker timePicker;
    private AppCompatImageButton minusButton, plusButton;
    private MaterialTextView doseCount;
    private MaterialButton cancelButton, setButton;

    public TimeDosePickerDialogFragment(Context mContext, MedicineDose medicineDose) {
        this.mContext = mContext;
        this.medicineDose = medicineDose;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_time_dose_picker, null);

        initViews(view);
    }

    private void initViews(View view) {
    }
}
