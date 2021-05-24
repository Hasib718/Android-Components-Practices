package com.hasib.petzone;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hasib.petzone.databinding.ItemFoodBinding;
import com.hasib.petzone.db.Food;

import org.jetbrains.annotations.NotNull;

public class ItemAdapter extends ListAdapter<Food, ItemAdapter.ItemViewHolder> {
    private static final String TAG = ItemAdapter.class.getSimpleName();
    private static final DiffUtil.ItemCallback<Food> DIFF_CALLBACK = new DiffUtil.ItemCallback<Food>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Food oldItem, @NonNull @NotNull Food newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Food oldItem, @NonNull @NotNull Food newItem) {
            return oldItem.getTitle().equalsIgnoreCase(newItem.getTitle()) &&
                    oldItem.getPrice() == newItem.getPrice();
        }
    };

    public Context context;

    public ItemAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        Food food = getItem(position);
        holder.binding.foodTitle.setText(food.getTitle());
        holder.binding.foodPrice.setText(String.valueOf(food.getPrice()));
        holder.binding.foodQuantity.setText(String.valueOf(food.getQuantity()));
        holder.binding.foodImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                context.getResources().getIdentifier(food.getImage(), "drawable", context.getPackageName()), context.getTheme()));

        holder.binding.itemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int get = Integer.parseInt(holder.binding.itemCount.getText().toString());
                if (get >= 0) {
                    String str = String.valueOf(get + 1);
                    holder.binding.itemCount.setText(str);
                    getItem(position).setRequestCount(get + 1);
                }
            }
        });

        holder.binding.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int get = Integer.parseInt(holder.binding.itemCount.getText().toString());
                if (get > 0) {
                    String str = String.valueOf(get - 1);
                    holder.binding.itemCount.setText(str);
                    getItem(position).setRequestCount(get - 1);
                }
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemFoodBinding binding;

        public ItemViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
