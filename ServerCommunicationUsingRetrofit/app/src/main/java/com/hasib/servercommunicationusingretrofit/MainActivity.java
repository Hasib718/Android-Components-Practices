package com.hasib.servercommunicationusingretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.0.7:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.signup).setOnClickListener(view -> handleSignupDialog());

    }

    private void handleSignupDialog() {

        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button signupBtn = view.findViewById(R.id.signup);
        final EditText nameEdit = view.findViewById(R.id.nameEdit);
        final EditText emailEdit = view.findViewById(R.id.emailEdit);
        final EditText passwordEdit = view.findViewById(R.id.passwordEdit);

        signupBtn.setOnClickListener(view1 -> {

            HashMap<String, String> map = new HashMap<>();

            map.put("name", nameEdit.getText().toString());
            map.put("email", emailEdit.getText().toString());
            map.put("password", passwordEdit.getText().toString());

            Call<Void> call = retrofitInterface.executeSignup(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.code() == 404) {
                        Toast.makeText(MainActivity.this,
                                "Data field are null or network error or Error", Toast.LENGTH_LONG).show();
                    } else if (response.code() == 201) {
                        Toast.makeText(MainActivity.this,
                                "Data sent successfully", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        });

    }
}
