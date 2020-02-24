package com.hasib.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer = null;

    public void pressingButton(View view) {
        int id = view.getId();

        String buttonIdName = view.getResources().getResourceEntryName(id);

        int resourceId = getResources().getIdentifier(buttonIdName, "raw", "com.hasib.gridlayout");

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(this, resourceId);
        mediaPlayer.start();

        Log.i("Button Tapped", Integer.toString(resourceId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
