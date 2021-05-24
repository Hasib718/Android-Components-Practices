package com.hasib.petzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.hasib.petzone.databinding.ActivitySignUpBinding;
import com.hasib.petzone.db.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = SignUpActivity.class.getSimpleName();

    private ActivitySignUpBinding binding;

    private List<User> userList;

    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AppViewModel.class);
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList = users;
            }
        });

        binding.backToMain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        binding.newSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = getInformationFromUser();
                if (user != null) {
                    if (userList.stream().anyMatch(user1 -> user.getEmail().equalsIgnoreCase(user1.getEmail()))) {
                        Toast.makeText(SignUpActivity.this, "User is already registered!", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.insertUser(user);
                        if (user.getType() == UserType.CUSTOMER) {
                            sharedPreferences.edit().putString("category", "CUSTOMER").apply();
                        } else {
                            sharedPreferences.edit().putString("category", "ADMIN").apply();
                        }
                        sharedPreferences.edit().putBoolean("active", true).apply();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private User getInformationFromUser() {
        String name = binding.nameText.getText().toString().trim();
        String email = binding.emailText.getText().toString().trim();
        String mobile = binding.mobileNoText.getText().toString().trim();
        String password = binding.passwordText.getText().toString().trim();
        String str = "";
        for (int i=0; i<binding.checkBoxLayout.getChildCount(); i++) {
            if (((CheckBox) binding.checkBoxLayout.getChildAt(i)).isChecked()) {
                str = ((CheckBox) binding.checkBoxLayout.getChildAt(i)).getText().toString();
            }
        }
        UserType type;
        if (str.compareTo("CUSTOMER") == 0) {
            type = UserType.CUSTOMER;
        } else {
            type = UserType.ADMIN;
        }

        if (name.isEmpty()) {
            binding.nameTextInputLayout.setError("Name required");
            binding.nameText.requestFocus();
            return null;
        } else {
            binding.nameTextInputLayout.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailTextLayout.setError("Valid Email required");
            binding.emailTextLayout.requestFocus();
            return null;
        } else {
            binding.emailTextLayout.setError(null);
        }

        if (mobile.length() < 11) {
            binding.mobileNoTextInputLayout.setError("Valid Number required");
            binding.mobileNoText.requestFocus();
            return null;
        } else {
            binding.mobileNoTextInputLayout.setError(null);
        }

        if (password.length() < 8) {
            binding.passwordTextLayout.setError("Minimum length 8");
            binding.passwordText.requestFocus();
            return null;
        } else {
            binding.passwordTextLayout.setError(null);
        }

        return new User(name, mobile, type, email, password);
    }
}