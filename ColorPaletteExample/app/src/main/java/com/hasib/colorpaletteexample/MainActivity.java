package com.hasib.colorpaletteexample;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.palette.graphics.Palette;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LinearLayout rootLayout;
    private AppCompatTextView textViewTitle, textViewBody;
    private AppCompatImageView imageView;

    private Palette.Swatch vibrantSwatch;
    private Palette.Swatch lightVibrantSwatch;
    private Palette.Swatch darkVibrantSwatch;
    private Palette.Swatch mutedSwatch;
    private Palette.Swatch lightMutedSwatch;
    private Palette.Swatch darkMutedSwatch;

    private int swatchNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.root_layout);
        textViewTitle = findViewById(R.id.text_view_title);
        textViewBody = findViewById(R.id.text_view_body);
        imageView = findViewById(R.id.image_view);

        extractPalette();
    }

    private void extractPalette() {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        /**
         * by default it generates 16 color swatch.. but we can increase it tho..
         * using maximumColorCount(int val) method we can control the number of generating colors..
         * for landscape images 8 - 16 number is recommended..
         * for facial images 24 - 32 can do the work
         *
         * The higher the value is the more time it takes to generate
         */

        Palette.from(bitmap).maximumColorCount(32).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                vibrantSwatch = palette.getVibrantSwatch();
                lightVibrantSwatch = palette.getLightVibrantSwatch();
                darkVibrantSwatch = palette.getDarkVibrantSwatch();
                mutedSwatch = palette.getMutedSwatch();
                lightMutedSwatch = palette.getLightMutedSwatch();
                darkMutedSwatch = palette.getDarkMutedSwatch();
            }
        });
    }

    public void nextSwatch(View v) {
        Palette.Swatch currentSwatch = null;

        switch (swatchNumber) {
            case 0:
                currentSwatch = vibrantSwatch;
                textViewTitle.setText("Vibrant");
                break;
            case 1:
                currentSwatch = lightVibrantSwatch;
                textViewTitle.setText("Light Vibrant");
                break;
            case 2:
                currentSwatch = darkVibrantSwatch;
                textViewTitle.setText("Dark Vibrant");
                break;
            case 3:
                currentSwatch = mutedSwatch;
                textViewTitle.setText("Muted");
                break;
            case 4:
                currentSwatch = lightMutedSwatch;
                textViewTitle.setText("Light Muted");
                break;
            case 5:
                currentSwatch = darkMutedSwatch;
                textViewTitle.setText("Dark Muted");
                break;
        }

        if (currentSwatch != null) {
            rootLayout.setBackgroundColor(currentSwatch.getRgb());
            textViewTitle.setTextColor(currentSwatch.getTitleTextColor());
            textViewBody.setTextColor(currentSwatch.getBodyTextColor());
        } else {
            rootLayout.setBackgroundColor(Color.WHITE);
            textViewTitle.setTextColor(Color.RED);
            textViewBody.setTextColor(Color.RED);
        }

        if (swatchNumber < 5) {
            swatchNumber++;
        } else {
            swatchNumber = 0;
        }
    }

    public void changeImage(View view) {
        if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.naruto).getConstantState())) {
            imageView.setImageDrawable(getDrawable(R.drawable.aot));
        } else {
            imageView.setImageDrawable(getDrawable(R.drawable.naruto));
        }

        extractPalette();
    }
}