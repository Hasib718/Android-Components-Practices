package com.hasib.changingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        backButton = (ImageButton) findViewById(R.id.backButton);
        final Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        final Intent listIntent = new Intent(getApplicationContext(), ListActivity.class);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(listIntent);
            }
        });

        backButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(mainIntent);
                return true;
            }
        });

        Intent receivingIntent = getIntent();
        Toast.makeText(this, receivingIntent.getStringExtra("tappedString"), Toast.LENGTH_SHORT).show();

    }
}
