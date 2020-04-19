package com.hasib.fragmentdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FragmentTransaction transaction;
    private FragmentManager manager;

    private TextView fragmentTransactionCount;
    private Button fragmentTransactionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentTransactionButton = findViewById(R.id.fragmentTransactionButton);
        fragmentTransactionCount = findViewById(R.id.fragmentTransactionCount);

        manager = getSupportFragmentManager();

        fragmentTransactionCount.setText("Fragment count in Back Stack "+manager.getBackStackEntryCount());
        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                fragmentTransactionCount.setText("Fragment count in Back Stack "+manager.getBackStackEntryCount());
            }
        });

        Log.d(TAG, "onCreate: Back Stack Entry Count "+manager.getBackStackEntryCount());
        
        fragmentTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment();
            }
        });
    }

    private void addFragment() {
        Fragment fragment;

        switch (manager.getBackStackEntryCount()) {
            case 0:
                fragment = new SampleFragment();
                break;
            case 1:
                fragment = new SampleFragmentTwo();
                break;
            case 2:
                fragment = new SampleFragmentThree();
                break;
            default:
                fragment = new SampleFragment();
                break;
        }
        transaction = manager.beginTransaction();

        transaction.add(R.id.fragmentContainer, fragment, "Demo Fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
