package com.dedekurnn.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // 0 : Yellow, 1 : Red, 2 : Empty
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                                {0, 4, 8}, {2, 4, 6}};
    int yellowPlayerScore = 0;
    int redPlayerScore = 0;
    boolean gameActive = true;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        // Counting winning score
        TextView yellowScoreTextView = findViewById(R.id.yellowScoreTextView);
        TextView redScoreTextView = findViewById(R.id.redScoreTextView);

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // Checking wheter grid is empty and game is active.
        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            // Alternate active player each turn.
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            // Checking if player has won.
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    String winner;
                    if (activePlayer == 1) {
                        winner = "Yellow!";
                        yellowPlayerScore += 1;
                        yellowScoreTextView.setText(String.format(java.util.Locale.US,"%d", yellowPlayerScore));
                    } else {
                        winner = "Red!";
                        redPlayerScore += 1;
                        redScoreTextView.setText(String.format(java.util.Locale.US,"%d", redPlayerScore));
                    }
                    Toast.makeText(this, "Winner is " + winner, Toast.LENGTH_SHORT).show();

                    Button playAgainButton = findViewById(R.id.playAgainBtn);
                    playAgainButton.setVisibility(View.VISIBLE);
                    gameActive = false;
                } else if (checkGameState()) {
                    Button playAgainButton = findViewById(R.id.playAgainBtn);
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public boolean checkGameState () {
        boolean isBoardFull = true;
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView reset = (ImageView) gridLayout.getChildAt(i);
            if(reset.getDrawable() == null) {
                isBoardFull = false;
            }
        }
        return isBoardFull;
    }

    public void btnFunction() {
        Button playAgainButton = findViewById(R.id.playAgainBtn);
        playAgainButton.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView reset = (ImageView) gridLayout.getChildAt(i);
            reset.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        activePlayer = 0;
        gameActive = true;
    }
    public void playAgain(View view) {
        btnFunction();
    }
    public void resetGame(View view) {
        TextView yellowScoreTextView = findViewById(R.id.yellowScoreTextView);
        TextView redScoreTextView = findViewById(R.id.redScoreTextView);
        btnFunction();
        redPlayerScore = 0;
        yellowPlayerScore = 0;
        yellowScoreTextView.setText(String.format(java.util.Locale.US,"%d", yellowPlayerScore));
        redScoreTextView.setText(String.format(java.util.Locale.US,"%d", redPlayerScore));
    }


}