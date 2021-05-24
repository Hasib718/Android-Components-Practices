package com.hasib.petzone.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hasib.petzone.AppViewModel;
import com.hasib.petzone.ItemAdapter;
import com.hasib.petzone.databinding.FragmentItemBinding;
import com.hasib.petzone.db.Food;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class AdminItemFragment extends Fragment {
    private static final String TAG = AdminItemFragment.class.getSimpleName();

    private FragmentItemBinding binding;
    private AppViewModel viewModel;

    public AdminItemFragment() {

    }

    public static AdminItemFragment newInstance() {
        AdminItemFragment fragment = new AdminItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.itemRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false));
        ItemAdapter adapter = new ItemAdapter(requireContext()) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull ItemAdapter.ItemViewHolder holder, int position) {
                Food food = getItem(position);
                holder.binding.foodTitle.setText(food.getTitle());
                holder.binding.foodPrice.setText(String.valueOf(food.getPrice()));
                holder.binding.foodQuantity.setVisibility(View.GONE);
                holder.binding.foodImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                        context.getResources().getIdentifier(food.getImage(), "drawable", context.getPackageName()), context.getTheme()));

                holder.binding.countTitle.setText("Item in Storage");
                holder.binding.itemCount.setText(String.valueOf(food.getQuantity()));
                holder.binding.itemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int get = Integer.parseInt(holder.binding.itemCount.getText().toString());
                        if (get >= 0) {
                            String str = String.valueOf(get + 1);
                            holder.binding.itemCount.setText(str);
                            getItem(position).setQuantity(get + 1);
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
                            getItem(position).setQuantity(get - 1);
                        }
                    }
                });
            }
        };
        binding.itemRecyclerView.setAdapter(adapter);
        viewModel.getFoods().observe(requireActivity(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                adapter.submitList(foods);
            }
        });

        binding.buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> buyFood = adapter.getCurrentList().stream()
                        .filter(food -> food.getQuantity() > 0)
                        .collect(Collectors.toList());

                Log.d(TAG, "onClick: "+buyFood.toString());

                buyFood.forEach(food -> {
                    viewModel.updateFood(food);
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
