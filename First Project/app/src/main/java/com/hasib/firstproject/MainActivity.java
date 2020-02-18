package com.hasib.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void transition(View view) {
        ImageView out = (ImageView) findViewById(R.id.animeOut);
        ImageView in = (ImageView) findViewById(R.id.animeIn);

        out.animate()
                .translationXBy(1000f)
                .translationYBy(1000f)
                .alpha(1)
                .rotationBy(3600)
                .setDuration(2000);
        //in.animate().alpha(1f).setDuration(3000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView out = (ImageView) findViewById(R.id.animeOut);

        out.setTranslationX(-1000f);
        out.setTranslationY(-1000f);
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
