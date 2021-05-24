package com.hasib.petzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hasib.petzone.databinding.ItemRequestBinding;
import com.hasib.petzone.db.Food;
import com.hasib.petzone.db.FoodTransactionRequest;

import org.jetbrains.annotations.NotNull;

public class RequestAdapter extends ListAdapter<FoodTransactionRequest, RequestAdapter.RequestViewHolder> {
    private static final String TAG = RequestAdapter.class.getSimpleName();
    private static final DiffUtil.ItemCallback<FoodTransactionRequest> DIFF_CALLBACK = new DiffUtil.ItemCallback<FoodTransactionRequest>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull FoodTransactionRequest oldItem, @NonNull @NotNull FoodTransactionRequest newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull FoodTransactionRequest oldItem, @NonNull @NotNull FoodTransactionRequest newItem) {
            return oldItem.getTransactionId().equalsIgnoreCase(newItem.getTransactionId());
        }
    };

    public Context context;

    public RequestAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemRequestBinding binding = ItemRequestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RequestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RequestViewHolder holder, int position) {
        FoodTransactionRequest transaction = getItem(position);
        holder.binding.foodRequestId.setText(transaction.getTransactionId());
        holder.binding.itemRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        ItemAdapter adapter = new ItemAdapter(context) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
                Food food = getItem(position);
                holder.binding.foodTitle.setText(food.getTitle());
                holder.binding.foodPrice.setText(String.valueOf(food.getPrice()));
                holder.binding.foodQuantity.setVisibility(View.GONE);
                holder.binding.foodImage.setVisibility(View.GONE);

                holder.binding.itemAdd.setVisibility(View.GONE);

                holder.binding.itemRemove.setVisibility(View.GONE);
                holder.binding.itemCount.setText(String.valueOf(food.getRequestCount()));
                holder.binding.itemCount.setClickable(false);
            }
        };
        holder.binding.itemRecyclerView.setAdapter(adapter);
        adapter.submitList(transaction.getFoods());

        holder.binding.buttonLayout.setVisibility(View.GONE);
    }

    public FoodTransactionRequest getTransactionAt(int position) {
        return getItem(position);
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        public ItemRequestBinding binding;

        public RequestViewHolder(ItemRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
