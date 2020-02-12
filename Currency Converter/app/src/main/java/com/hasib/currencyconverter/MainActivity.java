package com.hasib.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view) {
        Log.i("Convert", "Button Pressed");
        EditText amount = (EditText) findViewById(R.id.inputAmountBox);
        double dub = Double.parseDouble(amount.getText().toString());

        Toast.makeText(MainActivity.this, String.valueOf(dub*84.93), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
