package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private Button goButton;

    private CountDownTimer countDownTimer;
    private boolean timerIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.countDownTextView);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void startTimer(View view) {
        if (timerIsActive) {
            resetTimer();
            return;
        }

        timerIsActive = true;
        timerSeekBar.setEnabled(false);
        goButton.setText("STOP!");

        countDownTimer = new CountDownTimer(
                timerSeekBar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long l) {
                updateTimer((int) l / 1000);
            }

            @Override
            public void onFinish() {
                playSound();
                resetTimer();
            }
        }.start();
    }

    private void resetTimer() {
        timerSeekBar.setEnabled(true);
        goButton.setText("GO!");
        countDownTimer.cancel();
        updateTimer(timerSeekBar.getProgress());
        timerIsActive = false;
    }

    private void updateTimer(int i) {
        int minutes = i / 60;
        int seconds = i - minutes * 60;
        timerTextView.setText(String.format("%d:%02d", minutes, seconds));
    }

    private void playSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.air_horn);
        mediaPlayer.start();
    }
}