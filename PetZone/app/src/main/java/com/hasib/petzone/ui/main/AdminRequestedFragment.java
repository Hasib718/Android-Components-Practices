package com.hasib.petzone.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hasib.petzone.AppViewModel;
import com.hasib.petzone.ItemAdapter;
import com.hasib.petzone.RequestAdapter;
import com.hasib.petzone.databinding.FragmentRequestedBinding;
import com.hasib.petzone.db.Food;
import com.hasib.petzone.db.FoodTransactionHistory;
import com.hasib.petzone.db.FoodTransactionRequest;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminRequestedFragment extends Fragment {

    private static final String TAG = AdminRequestedFragment.class.getSimpleName();

    private FragmentRequestedBinding binding;
    private AppViewModel viewModel;

    public AdminRequestedFragment() {
        // Required empty public constructor
    }

    public static AdminRequestedFragment newInstance() {
        AdminRequestedFragment fragment = new AdminRequestedFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentRequestedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.requestedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true));
        RequestAdapter adapter = new RequestAdapter(requireContext()) {
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
            }
        };
        binding.requestedRecyclerView.setAdapter(adapter);
        viewModel.getTransactions().observe(requireActivity(), new Observer<List<FoodTransactionRequest>>() {
            @Override
            public void onChanged(List<FoodTransactionRequest> foodTransactionRequests) {
                adapter.submitList(foodTransactionRequests);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
