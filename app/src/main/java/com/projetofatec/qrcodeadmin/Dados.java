package com.projetofatec.qrcodeadmin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dados extends AppCompatActivity {

    private  TextView txtDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);

        // Recuperar os dados enviados pela ScannerActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String scannedData = extras.getString("scannedData");
            // Exibir os dados em um TextView ou fazer o que for necessário com eles
            TextView txtDados = findViewById(R.id.txtDados);
            if (txtDados != null) {
                txtDados.setText(scannedData);
            }


        }
    }
}