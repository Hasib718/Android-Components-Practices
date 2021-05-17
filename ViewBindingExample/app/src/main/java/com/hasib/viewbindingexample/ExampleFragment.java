package com.hasib.viewbindingexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hasib.viewbindingexample.databinding.FragmentExampleBinding;

public class ExampleFragment extends Fragment {
    private FragmentExampleBinding binding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentExampleBinding.inflate(inflater, container, false);

        binding.fragmentTextView1.setText("View Binding");
        if (binding.fragmentTextView2 != null) {
            binding.fragmentTextView2.setText("works in fragments too");
        }
        binding.includeLayout.includeTextView1.setText("and with");
        binding.includeLayout.includeTextView2.setText("include layouts");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
