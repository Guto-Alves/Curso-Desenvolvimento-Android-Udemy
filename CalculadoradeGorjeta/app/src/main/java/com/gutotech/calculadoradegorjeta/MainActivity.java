package com.gutotech.calculadoradegorjeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editTextValor;
    private TextView textPorcentagem;
    private TextView textGorjeta;
    private TextView textTotal;
    private SeekBar seekBar;

    private double porcentagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValor = findViewById(R.id.editTextValor);
        textPorcentagem = findViewById(R.id.textPorcentagem);
        textGorjeta = findViewById(R.id.textGorjeta);
        textTotal = findViewById(R.id.textTotal);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                porcentagem = seekBar.getProgress();
                textPorcentagem.setText(String.format("%,.0f%%", porcentagem));
                calcular();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calcular() {
        if (!editTextValor.getText().toString().isEmpty()) {
            double valor = Double.parseDouble(editTextValor.getText().toString());

            double gorjeta = valor * (porcentagem / 100);
            double total = valor + gorjeta;

            textGorjeta.setText(String.format("R$ %,.0f", gorjeta));
            textTotal.setText(String.format("R$ %,.2f", total));
        } else {
            editTextValor.setError("Digite um valor!");
            textGorjeta.setText("R$ 0.00");
            textTotal.setText("R$ 0.00");
        }
    }
}
