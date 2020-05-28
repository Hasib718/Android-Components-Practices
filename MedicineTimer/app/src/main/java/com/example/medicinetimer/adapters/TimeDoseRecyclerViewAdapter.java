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
import com.example.medicinetimer.container.MedicineDose;
import com.example.medicinetimer.listeners.OnTimeListClickEvents;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class TimeDoseRecyclerViewAdapter extends RecyclerView.Adapter<TimeDoseRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "TimeDoseRecyclerViewAda";

    private Context mContext;
    private List<MedicineDose> mDoseTimeList = new ArrayList<>();
    private List<String> mTimeList = new ArrayList<>();

    protected OnTimeListClickEvents onTimeListClickEvents;

    public TimeDoseRecyclerViewAdapter(Context mContext, List<MedicineDose> mDoseTimeList) {
        this.mContext = mContext;
        this.mDoseTimeList = mDoseTimeList;
    }

    public TimeDoseRecyclerViewAdapter(List<String> mTimeList, Context mContext) {
        this.mContext = mContext;
        this.mTimeList = mTimeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_medicine_time_dose, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.time.setText(mDoseTimeList.get(position).getTime());
        holder.dose.setText(mDoseTimeList.get(position).getDose());

        Log.d(TAG, "onBindViewHolder: " + mDoseTimeList.size());
        if (position == mDoseTimeList.size() - 1) {
            holder.underlineView.setVisibility(View.GONE);
        } else {
            holder.underlineView.setVisibility(View.VISIBLE);
        }

        holder.timeDoseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeListClickEvents.onTimeDoseClickListener(position, mDoseTimeList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDoseTimeList.isEmpty()) {
            return mTimeList.size();
        } else {
            return mDoseTimeList.size();
        }
    }

    public void clearData() {
        mDoseTimeList.clear();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<MedicineDose> getmDoseTimeList() {
        return mDoseTimeList;
    }

    public void setmDoseTimeList(List<MedicineDose> mDoseTimeList) {
        this.mDoseTimeList = mDoseTimeList;
    }

    public List<String> getmTimeList() {
        return mTimeList;
    }

    public void setmTimeList(List<String> mTimeList) {
        this.mTimeList = mTimeList;
    }

    public void setOnTimeListClickEvents(OnTimeListClickEvents onTimeListClickEvents) {
        this.onTimeListClickEvents = onTimeListClickEvents;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView time, dose;
        public ConstraintLayout timeDoseLayout;
        public View underlineView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            dose = itemView.findViewById(R.id.dose);
            timeDoseLayout = itemView.findViewById(R.id.timeDoseLayout);
            underlineView = itemView.findViewById(R.id.underLineView);
        }
    }
}
