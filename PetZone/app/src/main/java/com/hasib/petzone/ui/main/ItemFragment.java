package com.hasib.petzone.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasib.petzone.AppViewModel;
import com.hasib.petzone.ItemAdapter;
import com.hasib.petzone.UserType;
import com.hasib.petzone.databinding.FragmentItemBinding;
import com.hasib.petzone.db.Food;
import com.hasib.petzone.db.FoodTransactionRequest;
import com.hasib.petzone.db.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemFragment extends Fragment {

    private static final String TAG = ItemFragment.class.getSimpleName();

    private FragmentItemBinding binding;
    private AppViewModel viewModel;

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
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
        ItemAdapter adapter = new ItemAdapter(requireContext());
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
                        .filter(food -> food.getRequestCount() > 0)
                        .collect(Collectors.toList());

                Log.d(TAG, "onClick: "+buyFood.toString());

                String message = buyFood.stream()
                        .map(food -> String.format("%s\t\t%s", food.getTitle(), food.getRequestCount()))
                        .collect(Collectors.joining("\n"));

                new AlertDialog.Builder(requireContext())
                        .setTitle("Confirm Purchase")
                        .setMessage(message)
                        .setPositiveButton("Request", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User user = new User("hasib", "01821941015", UserType.CUSTOMER, "sfgsdf", "fgdsfg");
                                FoodTransactionRequest transaction = new FoodTransactionRequest(UUID.randomUUID().toString(), buyFood, user);
                                viewModel.insertFoodTransaction(transaction);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}