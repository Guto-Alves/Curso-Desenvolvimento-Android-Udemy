package com.gutotech.pedrapapeloutesoura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectRock(View view){
        selectedOption("rock");
    }

    public void selectPaper(View view){
        selectedOption("paper");
    }

    public void selectScissors(View view){
        selectedOption("scissors");
    }

    public void selectedOption(String userChoise){
        ImageView appChoiseImage = findViewById(R.id.appChoiseImage);

        String[] options = {"rock", "paper", "scissors"};
        String appChoise = options[new SecureRandom().nextInt(options.length)];

        switch (appChoise){
                case "rock":
                    appChoiseImage.setImageResource(R.drawable.pedra);
                    break;
                case "paper":
                    appChoiseImage.setImageResource(R.drawable.papel);
                    break;
                case "scissors":
                    appChoiseImage.setImageResource(R.drawable.tesoura);
                    break;
        }

        TextView resultText = findViewById(R.id.resultText);

        if((appChoise.equals("rock") && userChoise.equals("scissors")) || (appChoise.equals("paper") && userChoise.equals("rock")) || (appChoise.equals("scissors") && userChoise.equals("paper"))) // winning app
            resultText.setText("Você perdeu :( ");
        else if((userChoise.equals("rock") && appChoise.equals("scissors")) || (userChoise.equals("paper") && appChoise.equals("rock")) || (userChoise.equals("scissors") && appChoise.equals("paper"))) // winning user
            resultText.setText("Você ganhou :) ");
        else // draw
            resultText.setText("Empatamos ;) ");
    }
}
