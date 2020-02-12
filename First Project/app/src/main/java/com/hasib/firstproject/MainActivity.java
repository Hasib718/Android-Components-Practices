package com.hasib.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView background;
    Button button;

    public void clickFunction(View view) {
        Log.i("Test", "Button Clicked");
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        Toast.makeText(MainActivity.this, username.getText(), Toast.LENGTH_LONG).show();

        Log.i("Info", username.getText().toString());
        Log.i("info_pass", password.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for changing image on button click

//        background = (ImageView) findViewById(R.id.firstImage);
//        button = (Button) findViewById(R.id.testButton);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                background.setImageResource(R.drawable.test);
//            }
//        });
    }
}
