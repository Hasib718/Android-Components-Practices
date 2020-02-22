package com.hasib.addingsound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageView playIcon;
    AudioManager audioManagerSound;
    AudioManager audioManagerAudio;
    SeekBar volumeControl;
    SeekBar audioControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.taki_taki);
        playIcon = findViewById(R.id.playIcon);

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
                } else {
                    mediaPlayer.pause();
                    playIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            }
        });

        volumeControl = (SeekBar) findViewById(R.id.seekBarSound);
        audioManagerSound = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManagerSound.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManagerSound.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("SeekBar Value", Integer.toString(progress));

                audioManagerSound.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        audioControl = (SeekBar) findViewById(R.id.seekBarAudio);
        audioControl.setMax(mediaPlayer.getDuration());
        audioControl.setProgress(mediaPlayer.getCurrentPosition());

        //for tunning audio at a fixxed rate
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioControl.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 10000);

        audioControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
