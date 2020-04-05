package com.hasib.newsreader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<String> mTitles = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, List<String> mTitles) {
        this.mTitles = mTitles;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.newsTitle.setText(mTitles.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = MainActivity.articlesDB.rawQuery("SELECT * FROM articles WHERE id='4'", null);
                Log.d(TAG, "onClick: "+c.getString(c.getColumnIndex("title")));

                Toast.makeText(mContext, c.getString(c.getColumnIndex("title")), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parentLayout;
        TextView newsTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.paren_layout);
            newsTitle = itemView.findViewById(R.id.newsTitle);
        }
    }
}
