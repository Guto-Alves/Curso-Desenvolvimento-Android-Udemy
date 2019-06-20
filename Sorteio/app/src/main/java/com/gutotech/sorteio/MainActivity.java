package com.gutotech.sorteio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateNumber(View view){
        TextView resultText = findViewById(R.id.resultText);
        resultText.setText("O número selecionado é: "+ new SecureRandom().nextInt(11));
    }
}
