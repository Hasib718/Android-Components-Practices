package com.hasib.viewbindingexample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hasib.viewbindingexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textView1.setText("View Binding");
        binding.textView2.setText("is working");

        binding.buttonOpenFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new ExampleFragment())
                .addToBackStack(null)
                .commit();
    }
}