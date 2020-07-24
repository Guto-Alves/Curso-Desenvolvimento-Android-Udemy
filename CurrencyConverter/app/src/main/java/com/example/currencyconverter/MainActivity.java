package com.example.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
    }

    public void convert(View view) {
        if (!editText.getText().toString().matches("\\d+\\.*\\d*")) {
            return;
        }

        double pounds = Double.parseDouble(editText.getText().toString());
        double dollars = pounds * 1.3;

        String result = String.format(Locale.US, "£%.2f is $%.2f", pounds, dollars);
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}