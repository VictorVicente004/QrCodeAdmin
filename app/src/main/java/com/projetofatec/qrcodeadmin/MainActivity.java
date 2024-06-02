package com.projetofatec.qrcodeadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

import javax.security.auth.Subject;

public class MainActivity extends AppCompatActivity {


    private EditText nomeEvento, nomeParticipante, detalheEvento, dataEvento, localEvento;
    private Button  btnEnviar, btnScanner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //FindView

        nomeEvento = findViewById(R.id.nomeEvento);
        nomeParticipante = findViewById(R.id.nomeParticipante);
        detalheEvento = findViewById(R.id.detalheEvento);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnScanner = findViewById(R.id.btnScanner);
        dataEvento = findViewById(R.id.dataEvento);
        localEvento = findViewById(R.id.localEvento);




        //função para escanear qrcode
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });

        //função para enviar convite
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Concatenar os dados do evento em uma única string
                String dadosEvento = "Nome do Evento: " + nomeEvento.getText().toString() + "\n" +
                        "Nome do Participante: " + nomeParticipante.getText().toString() + "\n" +
                        "Detalhes: " + detalheEvento.getText().toString() + "\n" +
                        "Data: " + dataEvento.getText().toString() + "\n" +
                        "Local: " + localEvento.getText().toString();

                Intent intent = new Intent(MainActivity.this, EnviarEmail.class);
                intent.putExtra("dadosEvento", dadosEvento);
                startActivity(intent);
            }
        });
    }
}