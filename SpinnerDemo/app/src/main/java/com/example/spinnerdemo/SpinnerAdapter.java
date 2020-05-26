package com.example.spinnerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    private static final String TAG = "SpinnerAdapter";
    private Context mContext;
    private ArrayList<State> listState;
    private SpinnerAdapter adapter;
    private boolean isFromView = false;

    public SpinnerAdapter(Context mContext, int resource, List<State> objects) {
        super(mContext, resource, objects);
        this.mContext = mContext;
        this.listState = (ArrayList<State>) objects;
        this.adapter = this;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = convertView.findViewById(R.id.degreeText);
            holder.mCheckBox = convertView.findViewById(R.id.degreeCheckBox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(listState.get(position).getTitle());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(getPosition).setSelected(isChecked);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private AppCompatTextView mTextView;
        private AppCompatCheckBox mCheckBox;
    }
}
