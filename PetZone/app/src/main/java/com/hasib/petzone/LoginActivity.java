package com.hasib.petzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;

import com.hasib.petzone.databinding.ActivityLoginBinding;
import com.hasib.petzone.db.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ActivityLoginBinding binding;
    private AppViewModel viewModel;

    private SharedPreferences sharedPreferences;

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AppViewModel.class);
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList = users;
            }
        });
        Log.d(TAG, "onCreate: "+userList.toString());
//        binding.signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final User user = getInformationFromUser();
//                if (user != null) {
//                    users.stream().filter(user1 -> user1.getEmail().equalsIgnoreCase(user.getEmail())).findFirst().ifPresent(user1 -> {
//                        Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    });
//
//                    }
//                }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private User getInformationFromUser() {
        String email = binding.usernameText.getText().toString().trim();
        String password = binding.passwordText.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.usernameText.setError("Valid Email required");
            binding.usernameText.requestFocus();
            return null;
        } else {
            binding.usernameText.setError(null);
        }

        if (password.length() < 8) {
            binding.passwordTextLayout.setError("Minimum length 8");
            binding.passwordText.requestFocus();
            return null;
        } else {
            binding.passwordTextLayout.setError(null);
        }

        return new User(null, null, null, email, password);
    }
}