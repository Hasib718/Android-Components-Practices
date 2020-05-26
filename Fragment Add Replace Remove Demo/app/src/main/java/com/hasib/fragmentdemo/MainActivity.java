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

        /**
         * This switch statement only works with back stack
         */
//        switch (manager.getBackStackEntryCount()) {
//            case 0:
//                fragment = new SampleFragment();
//                break;
//            case 1:
//                fragment = new SampleFragmentTwo();
//                break;
//            case 2:
//                fragment = new SampleFragmentThree();
//                break;
//            default:
//                fragment = new SampleFragment();
//                break;
//        }

        fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof SampleFragment) {
            fragment = new SampleFragmentTwo();
        } else if (fragment instanceof SampleFragmentTwo) {
            fragment = new SampleFragmentThree();
        } else if (fragment instanceof SampleFragmentThree) {
            fragment = new SampleFragment();
        } else {
            fragment = new SampleFragment();
        }

        transaction = manager.beginTransaction();
//        transaction.add(R.id.fragmentContainer, fragment, "Demo Fragment"); /** this add fragment like a stack **/
        transaction.replace(R.id.fragmentContainer, fragment, "Demo Fragment"); /** this will replace a fragment with existing one **/
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Method for removing fragment when there is no fragment back stack
     */
    @Override
    public void onBackPressed() {
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        } else {
            super.onBackPressed();
        }
    }
}
