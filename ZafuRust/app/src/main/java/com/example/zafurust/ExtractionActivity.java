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
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ExtractionActivity extends AppCompatActivity {

    private static final String TAG = "ExtractionActivity";

    private AppCompatImageView imageView;
    private MaterialTextView imageText;

    private Uri imageUri;
    private Bitmap imageBitmap;
    private String imageCode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extraction);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imagePut);
        imageText = findViewById(R.id.image_text);

        Intent intent = getIntent();
        imageUri = Uri.parse(intent.getExtras().getString("imageUri"));

        ConversionFromBitmap conversion = new ConversionFromBitmap();

        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            imageView.setImageBitmap(imageBitmap);

            imageCode = conversion.execute(imageBitmap).get();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        findViewById(R.id.copy).setOnClickListener(v -> {
            if (!imageCode.isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("image", imageCode);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(ExtractionActivity.this, "Image Code Copied", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ExtractionActivity.this, "Image is not extracted yet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class ConversionFromBitmap extends AsyncTask<Bitmap, Void, String> {

        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            return ImageSupport.bitMapToString(bitmaps[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "onPostExecute: "+s);
            ((MaterialTextView) findViewById(R.id.image_text)).setText(s);
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