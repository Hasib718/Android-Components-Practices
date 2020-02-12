package com.hasib.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //This method is linked with "button"
    public void clickFunction(View view) {
        Log.i("Convert", "Button Pressed"); //Button press testing
        EditText amount = (EditText) findViewById(R.id.inputAmountBox); //linked with Text view named "inputAmountBox" id
        double dub = Double.parseDouble(amount.getText().toString()); //converted String to Double

        Toast.makeText(MainActivity.this, String.valueOf(dub*84.93), Toast.LENGTH_LONG).show(); // for short message delivery
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
