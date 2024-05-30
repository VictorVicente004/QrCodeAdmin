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
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

import javax.security.auth.Subject;

public class MainActivity extends AppCompatActivity {


    private EditText nomeEvento, nomeParticipante, detalheEvento;
    private Button  btnGerarCodigo;
    private ImageView ivQrcode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //FindView

        nomeEvento = findViewById(R.id.nomeEvento);
        nomeParticipante = findViewById(R.id.nomeParticipante);
        detalheEvento = findViewById(R.id.detalheEvento);
        btnGerarCodigo = findViewById(R.id.btnGerarCodigo);
        ivQrcode = findViewById(R.id.ivQrcode);


        //função para gerar QRCODE

        btnGerarCodigo.setOnClickListener(v->{
             gerarQR();

        });
    }

    private void gerarQR() {

        String text = nomeEvento.getText().toString().trim();
        if (text.isEmpty()) {
            Log.d("MainActivity", "Texto do evento está vazio");
            return;
        }

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            ivQrcode.setImageBitmap(bitmap);



            // Converter Bitmap para Base64 string
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


            // Iniciar a nova Activity e passar o bitmap codificado
            Intent intent = new Intent(MainActivity.this, EnviarEmail.class);
            intent.putExtra("qrCodeBitmap", encoded);
            startActivity(intent);


            Toast.makeText(this, "QRCode Gerado com sucesso :)", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "QR Code gerado com sucesso");

        } catch (WriterException e) {
            Log.e("MainActivity", "Erro ao gerar QR Code", e);
        }


    }
}