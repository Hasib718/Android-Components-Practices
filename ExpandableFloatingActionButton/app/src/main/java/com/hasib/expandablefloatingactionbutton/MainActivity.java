package com.hasib.expandablefloatingactionbutton;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Animation rotateOpen, rotateClose, fromBottom, toBottom;

    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        findViewById(R.id.addBtn).setOnClickListener(v -> {
            onAddButtonClicked();
        });

        findViewById(R.id.callBtn).setOnClickListener(v -> {
            Toast.makeText(this, "Call Button Clicked", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.messageBtn).setOnClickListener(v -> {
            Toast.makeText(this, "Message Button Clicked", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.emailBtn).setOnClickListener(v -> {
            Toast.makeText(this, "Email Button Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void onAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }

    private void setClickable(boolean clicked) {
        if (!clicked) {
            findViewById(R.id.callBtn).setClickable(true);
            findViewById(R.id.messageBtn).setClickable(true);
            findViewById(R.id.emailBtn).setClickable(true);
        } else {
            findViewById(R.id.callBtn).setClickable(false);
            findViewById(R.id.messageBtn).setClickable(false);
            findViewById(R.id.emailBtn).setClickable(false);
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            findViewById(R.id.callBtn).setVisibility(View.VISIBLE);
            findViewById(R.id.messageBtn).setVisibility(View.VISIBLE);
            findViewById(R.id.emailBtn).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.callBtn).setVisibility(View.INVISIBLE);
            findViewById(R.id.messageBtn).setVisibility(View.INVISIBLE);
            findViewById(R.id.emailBtn).setVisibility(View.INVISIBLE);
        }
    }

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            findViewById(R.id.callBtn).startAnimation(fromBottom);
            findViewById(R.id.messageBtn).startAnimation(fromBottom);
            findViewById(R.id.emailBtn).startAnimation(fromBottom);
            findViewById(R.id.addBtn).startAnimation(rotateOpen);
        } else {
            findViewById(R.id.callBtn).startAnimation(toBottom);
            findViewById(R.id.messageBtn).startAnimation(toBottom);
            findViewById(R.id.emailBtn).startAnimation(toBottom);
            findViewById(R.id.addBtn).startAnimation(rotateClose);
        }
    }
}