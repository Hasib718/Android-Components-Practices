package com.hasib.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private Button goStopButton;
    private TextView textView;
    private MediaPlayer mediaPlayer = null;
    private CountDownTimer countDownTimer;


    public void pressedButton(View view) {
        if (goStopButton.isPressed() && goStopButton.getText().equals("go")) {
            seekBar.setEnabled(false);
            goStopButton.setText("reset");

            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    long minutes = (millisUntilFinished/1000) / 60;
                    long seconds = (millisUntilFinished/1000) - (minutes * 60);
                    if (seconds <= 9) {
                        textView.setText(Long.toString(minutes) + ":0"+Long.toString(seconds));
                    } else {
                        textView.setText(Long.toString(minutes) + ":" + Long.toString(seconds));
                    }
                }

                @Override
                public void onFinish() {
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.nfs_tune_for_alarm);
                        mediaPlayer.start();
                    } else if(mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.start();
                    }
                }
            }.start();

        } else if (goStopButton.isPressed() && goStopButton.getText().equals("reset")) {
            countDownTimer.cancel();
            goStopButton.setText("go");
            seekBar.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.timeText);
        goStopButton = (Button) findViewById(R.id.goStopButton);

        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress/60;
                int seconds = progress - (minutes*60);
                if (seconds <= 9){
                    textView.setText(Integer.toString(minutes) +":0" +Integer.toString(seconds));
                } else {
                    textView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
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
