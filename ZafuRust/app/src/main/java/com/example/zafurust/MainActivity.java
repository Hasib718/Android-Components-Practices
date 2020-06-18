package com.example.zafurust;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int CHOOSE_IMAGE_REQUEST = 101;
    private GridLayout itemGrid;
    private Uri uriProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemGrid = findViewById(R.id.item_grid);

        setGridEvents(itemGrid);
    }

    private void setGridEvents(GridLayout itemGrid) {
        for (int i=0; i<itemGrid.getColumnCount(); i++) {
            final int finalI = i;
            ((MaterialCardView) itemGrid.getChildAt(i)).setOnClickListener(v -> {
                switch (finalI) {
                    case 0:
                        showImageChooser();
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, RetrieveActivity.class));
                        break;
                }
            });
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSE_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            Log.d(TAG, "onActivityResult: "+uriProfileImage.toString());

            Intent intent = new Intent(MainActivity.this, ExtractionActivity.class);
            intent.putExtra("imageUri", uriProfileImage.toString());
            startActivity(intent);
        }
    }
}