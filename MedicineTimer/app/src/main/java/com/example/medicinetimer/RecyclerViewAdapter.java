package com.example.medicinetimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Medicine> mMedicines;
    private Context mContext;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.medicineName.setText(mMedicines.get(position).getName());
        holder.medicineTime.setText(mMedicines.get(position).getTimes().get(position));
        holder.activityState.setEnabled(mMedicines.get(position).isActivityState());
    }

    @Override
    public int getItemCount() {
        return mMedicines.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView medicineName, medicineTime;
        SwitchMaterial activityState;
        MaterialCardView medicineLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicineName);
            medicineTime = itemView.findViewById(R.id.medicineTime);
            activityState = itemView.findViewById(R.id.medicineActivityState);
            medicineLayout = itemView.findViewById(R.id.medicineLayout);
        }
    }
}
