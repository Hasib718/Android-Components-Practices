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
    // For checking which player is currently active
    int activePlayer = 0;

    // For checking whether the game is currently active or not
    boolean activeGame = true;

    // For controlling over tapping once the game is over or drawn
    List<Integer> gameState = new ArrayList<>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2));

    // For keeping the record of game winning positions
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

    //Game controlling method
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappredCounter = Integer.parseInt(counter.getTag().toString());
        System.out.println(tappredCounter);


        //Checking game state and game activity
        //Also checking if the player tapped twice which invalid by gameState arrayList
        //and checking the game is drawn or not by activeGame variable
        if (gameState.get(tappredCounter) == 2 && activeGame) {
            gameState.set(tappredCounter, activePlayer);

            counter.setTranslationY(-1000f);

            //Depending on which player is active we change color of the ImageView
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            //Simple animation for popping in ImageView
            counter.animate()
                    .translationYBy(1000f)
                    .rotation(360f)
                    .setDuration(400);

            //For checking Game Result
            for (ArrayList<Integer> winningposition : winningPositions) {
                //checking winning
                if (gameState.get(winningposition.get(0)) != 2 &&
                    gameState.get(winningposition.get(0)) == gameState.get(winningposition.get(1)) &&
                        gameState.get(winningposition.get(1)) == gameState.get(winningposition.get(2))) {

                    //After Winning
                    activeGame = false; //when winner is found

                    //checking Winner
                    String winner = "Red";
                    if (gameState.get(winningposition.get(0)) == 0) {
                        winner = "Yellow";
                    }

                    //Displaying Winning Text
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");

                    //For the visibility of Linear Layout
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                } else { //If the game is drawn
                    boolean gameOver = true;

                    //Checking validity of drawm match
                    for (int counterState : gameState) {
                        if (counterState == 2) gameOver = false;
                    }

                    //Resetting if game is drawn
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

    //Method for resetting everything
    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;//changing to default player which is 0
        activeGame = true;//Restarting game means the game is active

        //Resetting gameState
        //ajaira jinsih pati
        for (int i=0; i<gameState.size(); i++) {
            gameState.set(i, 2);
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        //For setting all the ImageView to null or empty
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
