package com.hasib.connect3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow ||| 1 = red
    int activePlayer = 0;
    boolean activeGame = true;
    List<Integer> gameState = new ArrayList<>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2));
    ArrayList<ArrayList<Integer>> winningPositions = new ArrayList<ArrayList<Integer>>();
    {
        winningPositions.add(new ArrayList<>(Arrays.asList(0, 1, 2)));
        winningPositions.add(new ArrayList<>(Arrays.asList(3, 4, 5)));
        winningPositions.add(new ArrayList<>(Arrays.asList(6, 7, 8)));
        winningPositions.add(new ArrayList<>(Arrays.asList(0, 3, 6)));
        winningPositions.add(new ArrayList<>(Arrays.asList(1, 4, 7)));
        winningPositions.add(new ArrayList<>(Arrays.asList(2, 5, 8)));
        winningPositions.add(new ArrayList<>(Arrays.asList(0, 4, 8)));
        winningPositions.add(new ArrayList<>(Arrays.asList(2, 4, 6)));
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappredCounter = Integer.parseInt(counter.getTag().toString());
        System.out.println(tappredCounter);

        if (gameState.get(tappredCounter) == 2 && activeGame) {
            gameState.set(tappredCounter, activePlayer);

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate()
                    .translationYBy(1000f)
                    .rotation(360f)
                    .setDuration(400);

            for (ArrayList<Integer> winningposition : winningPositions) {
                if (gameState.get(winningposition.get(0)) != 2 &&
                    gameState.get(winningposition.get(0)) == gameState.get(winningposition.get(1)) &&
                        gameState.get(winningposition.get(1)) == gameState.get(winningposition.get(2))) {

                    //After Winning
                    activeGame = false;

                    String winner = "Red";
                    if (gameState.get(winningposition.get(0)) == 0) {
                        winner = "Yellow";
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameOver = true;

                    for (int counterState : gameState) {
                        if (counterState == 2) gameOver = false;
                    }

                    if (gameOver) {
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a Draw");

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        activeGame = true;
        for (int i=0; i<gameState.size(); i++) {
            gameState.set(i, 2);
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i=0; i<gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
