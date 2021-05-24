package com.hasib.petzone.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hasib.petzone.AppViewModel;
import com.hasib.petzone.RequestAdapter;
import com.hasib.petzone.databinding.FragmentRequestedBinding;
import com.hasib.petzone.db.FoodTransactionRequest;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RequestedFragment extends Fragment {

    private static final String TAG = RequestedFragment.class.getSimpleName();

    private FragmentRequestedBinding binding;
    private AppViewModel viewModel;

    public RequestedFragment() {
        // Required empty public constructor
    }

    public static RequestedFragment newInstance() {
        RequestedFragment fragment = new RequestedFragment();
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
        RequestAdapter adapter = new RequestAdapter(requireContext());
        binding.requestedRecyclerView.setAdapter(adapter);
        viewModel.getTransactions().observe(requireActivity(), new Observer<List<FoodTransactionRequest>>() {
            @Override
            public void onChanged(List<FoodTransactionRequest> foodTransactionRequests) {
                adapter.submitList(foodTransactionRequests);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteFoodTransaction(adapter.getTransactionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(requireContext(), "Request Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.requestedRecyclerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}