package com.gutotech.alcoolougasolina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editPriceOfGasoline;
    private EditText editPriceOfAlcohol;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPriceOfAlcohol = findViewById(R.id.editTextAlcohol);
        editPriceOfGasoline = findViewById(R.id.editTextGasoline);
        textResult = findViewById(R.id.textResult);
    }

    public void calculatePrice(View view) {
        String alcoholPrice = editPriceOfAlcohol.getText().toString();
        String gasolinePrice = editPriceOfGasoline.getText().toString();

        if (isValidFields(alcoholPrice, gasolinePrice))
            calculateBestPrice(Double.parseDouble(alcoholPrice), Double.parseDouble(gasolinePrice));
        else
            textResult.setText("Preencha os preços primeiro!");
    }

    public void calculateBestPrice(double alcoholPrice, double gasolinePrice) {
        if (alcoholPrice / gasolinePrice >= 0.7)
            textResult.setText("Melhor utilizar Gasolina!");
        else
            textResult.setText("Melhor utilizar Álcool!");
    }

    public boolean isValidFields(String alcoholField, String gasolineField) {
        if (alcoholField.equals("") || alcoholField == null)
            return false;
        else if (gasolineField.equals("") || gasolineField == null)
            return false;

        return true;
    }
}
