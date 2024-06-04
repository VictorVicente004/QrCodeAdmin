package com.projetofatec.qrcodeadmin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Dados extends AppCompatActivity {

    private  TextView txtDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);


        // Obter data
        Date dataHoraAtual = new Date();


        // Formatando a data e hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(dataHoraAtual);


        // Recuperar os dados enviados pela ScannerActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String scannedData = extras.getString("scannedData");
            // Adiciona a data e hora aos dados escaneados
            String finalData = scannedData + "\nData e Hora: " + formattedDate;

            // Exibir os dados em um TextView ou fazer o que for necessário com eles
            txtDados = findViewById(R.id.txtDados);
            if (txtDados != null) {
                txtDados.setText(finalData);
            }
        }
    }
}