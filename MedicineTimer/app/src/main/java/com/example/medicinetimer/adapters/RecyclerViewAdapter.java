package com.example.medicinetimer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinetimer.R;
import com.example.medicinetimer.container.Medicine;
import com.example.medicinetimer.listeners.OnMedicineListClickEvents;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private List<Medicine> mMedicines;
    private Context mContext;

    private OnMedicineListClickEvents medicineListClickEvents;

    public RecyclerViewAdapter(Context mContext, List<Medicine> mMedicines) {
        this.mMedicines = mMedicines;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_medicine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.medicineName.setText(mMedicines.get(position).getName());
        holder.medicineTime.setText(mMedicines.get(position).getTimes());
        holder.activityState.setChecked(mMedicines.get(position).isActivityState());

        holder.nameTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+position);

                medicineListClickEvents.onMedicineClickListener(position, mMedicines.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMedicines.size();
    }

    public void setMedicineListClickEvents(OnMedicineListClickEvents medicineListClickEvents) {
        this.medicineListClickEvents = medicineListClickEvents;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView medicineName, medicineTime;
        SwitchMaterial activityState;
        MaterialCardView medicineLayout;
        ConstraintLayout nameTimeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicineName);
            medicineTime = itemView.findViewById(R.id.medicineTime);
            activityState = itemView.findViewById(R.id.medicineActivityState);
            medicineLayout = itemView.findViewById(R.id.medicineLayout);
            nameTimeLayout = itemView.findViewById(R.id.nameTimeLayout);
        }
    }
}
