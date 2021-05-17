package com.hasib.databindingexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.hasib.databindingexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SimpleViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SimpleViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
    }
}