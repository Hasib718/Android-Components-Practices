package com.hasib.braintrain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button startingButton;
    private RelativeLayout mainLayout;
    private Button playAgainButton;
    private TextView timerText;
    private TextView qustionsText;
    private TextView scoreText;
    private GridLayout answerOptionGrid;
    private int correctAnswer;
    private int totalTriedQuestions;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;
    private TextView checkingPurpose;
    private TextView resultFinalText;
    final Random random = new Random();
    private int rand;

    public void playActivity(View view) {
        if (startingButton.isPressed()) {
            startingButton.setVisibility(Button.INVISIBLE);
            mainLayout.setVisibility(RelativeLayout.VISIBLE);
        } else {
            playAgainButton.setVisibility(View.INVISIBLE);
        }
        generateQuestions();
        new CountDownTimer(2100 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(Long.toString(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                checkingPurpose.setText("finished");
                resultFinalText.setText("Your Score: " + correctAnswer + "/" + totalTriedQuestions);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void generateQuestions() {
        totalTriedQuestions++;

        int a = random.nextInt(100);
        int b = random.nextInt(100);
        qustionsText.setText(Integer.toString(a) + " + " + Integer.toString(b));

        rand = random.nextInt(4);
        ((Button) answerOptionGrid.getChildAt(rand)).setText(Integer.toString(a + b));
        for (int j = 0; j < 4; j++) {
            if (rand != j) {
                ((Button) answerOptionGrid.getChildAt(j))
                        .setText(Integer.toString(random.nextInt(100) + random.nextInt(100)));
            }
        }
    }

    public void answerJudge(View view) {
        if (rand == Integer.parseInt(view.getTag().toString())) {
            correctAnswer++;
            resultFinalText.setText("Correct!");
//            resultFinalText.animate().alpha(1f).setDuration(400);
//            resultFinalText.animate().alpha(0f).setDuration(400);
        } else {
            resultFinalText.setText("Wrong!");
//            resultFinalText.animate().alpha(1f).setDuration(400);
//            resultFinalText.animate().alpha(0f).setDuration(400);
        }
        scoreText.setText(Integer.toString(correctAnswer) + "/" + Integer.toString(totalTriedQuestions));
        generateQuestions();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startingButton = (Button) findViewById(R.id.startingButton);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        timerText = (TextView) findViewById(R.id.timerText);
        qustionsText = (TextView) findViewById(R.id.problemText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        Button answerButton1 = (Button) findViewById(R.id.answerButton1);
        Button answerButton2 = (Button) findViewById(R.id.answerButton2);
        Button answerButton3 = (Button) findViewById(R.id.answerButton3);
        Button answerButton4 = (Button) findViewById(R.id.answerButton4);
        answerOptionGrid = (GridLayout) findViewById(R.id.answerOptionsGridLayout);
        resultFinalText = (TextView) findViewById(R.id.finalResultText);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        timerText.setText("30s");
        qustionsText.setText("");
        scoreText.setText("0/0");
    }
}
