package com.example.connect3game;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TextView winnerTextView;
    private Button playAgainButton;

    private int[] board = new int[9];
    private int currentPlayer; // 0 = red; 1 = yellow; -1 = empty
    private boolean gameActive;

    private int[][] winnerPositions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        winnerTextView = findViewById(R.id.winnerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        startGame();
    }

    public void startGame() {
        currentPlayer = 0;
        Arrays.fill(board, -1);

        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        TableRow tableRow1 = findViewById(R.id.tableRow1);
        TableRow tableRow2 = findViewById(R.id.tableRow2);
        TableRow tableRow3 = findViewById(R.id.tableRow3);

        for (int i = 0; i < 3; i++) {
            ImageView imageView = (ImageView) tableRow1.getVirtualChildAt(i);
            imageView.setImageResource(0);
            imageView = (ImageView) tableRow2.getVirtualChildAt(i);
            imageView.setImageResource(0);
            imageView = (ImageView) tableRow3.getVirtualChildAt(i);
            imageView.setImageResource(0);
        }

        gameActive = true;
    }

    public void finishGame(String winner) {
        gameActive = false;
        winnerTextView.setText(winner + " has won!");

        winnerTextView.setAlpha(0);
        winnerTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.VISIBLE);

        winnerTextView.animate()
                .alpha(1)
                .rotation(360)
                .setDuration(300);
    }

    public void playAgain(View view) {
        startGame();
    }

    public void dropIn(View view) {
        ImageView imageView = (ImageView) view;

        int tappedPosition = Integer.parseInt(imageView.getTag().toString());

        if (board[tappedPosition] != -1 || !gameActive) {
            return;
        }

        board[tappedPosition] = currentPlayer;
        imageView.setTranslationY(-1500);

        if (currentPlayer == 0) {
            imageView.setImageResource(R.drawable.red);
        } else {
            imageView.setImageResource(R.drawable.yellow);
        }

        imageView.animate()
                .translationYBy(1500)
                .rotation(3600)
                .setDuration(300);

        for (int[] winnerPosition : winnerPositions) {
            if (board[winnerPosition[0]] == 0 &&
                    board[winnerPosition[1]] == 0 &&
                    board[winnerPosition[2]] == 0) {
                winnerTextView.setTextColor(Color.RED);
                finishGame("RED");
                break;
            } else if (board[winnerPosition[0]] == 1 &&
                    board[winnerPosition[1]] == 1 &&
                    board[winnerPosition[2]] == 1) {
                winnerTextView.setTextColor(Color.YELLOW);
                finishGame("YELLOY");
                break;
            }
        }

        currentPlayer = (currentPlayer + 1) % 2;
    }
}