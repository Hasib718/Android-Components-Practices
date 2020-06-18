package com.example.zafurust;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.NavUtils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RetrieveActivity extends AppCompatActivity {

    private static final String TAG = "RetrieveActivity";

    private AppCompatImageView imageView;
    private TextInputEditText imageText;

    private String imageCode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageGet);
        imageText = findViewById(R.id.image_code);

        ConversionFromString conversion = new ConversionFromString();

        findViewById(R.id.process).setOnClickListener(v -> {
            Log.d(TAG, "onCreate: Here");
            if (!imageText.getText().toString().isEmpty()) {
                conversion.execute(imageText.getText().toString());
            }
        });

    }

    class ConversionFromString extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            return ImageSupport.stringToBitMap(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            ((AppCompatImageView) findViewById(R.id.imageGet)).setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}