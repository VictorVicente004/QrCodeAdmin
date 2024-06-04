package com.projetofatec.qrcodeadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

public class EnviarEmail extends AppCompatActivity {

    private EditText edtAssunto, edtConteudo, edtEmail;
    private Button btnEnviarConvite, btnMenu;
    private Bitmap qrCodeBitmap;
    private ImageView ivQrcode;
    private String qrContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enviar_email);

        edtAssunto = findViewById(R.id.subject);
        edtConteudo = findViewById(R.id.content);
        edtEmail = findViewById(R.id.to_email);
        btnEnviarConvite = findViewById(R.id.btnEnviarConvite);
        btnMenu = findViewById(R.id.btnMenu);
        ivQrcode = findViewById(R.id.ivQrcode);



        // Retornar ao início do programa
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnviarEmail.this, Menu.class);
                startActivity(intent);
            }
        });

        // -------------------------------------------------------------------------------------------------//




        // Receber os dados da MainActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("dadosEvento")) {
            String dadosEvento = intent.getStringExtra("dadosEvento");
            gerarQR(dadosEvento);
        }

        // -------------------------------------------------------------------------------------------------//


        // OnClick de enviar email
        btnEnviarConvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = edtAssunto.getText().toString().trim();
                String content = edtConteudo.getText().toString().trim();
                String toEmail = edtEmail.getText().toString().trim();

                if (subject.isEmpty() || content.isEmpty() || toEmail.isEmpty()) {
                    Toast.makeText(EnviarEmail.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    enviarEmail(subject, content, toEmail);
                }
            }
        });

        // -------------------------------------------------------------------------------------------------//

    }
    private void gerarQR(String dadosEvento) {
        // Criar QR Code com base nos dados do evento
        qrContent = dadosEvento;

        // Converter o conteúdo do QR Code em um bitmap
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            qrCodeBitmap = barcodeEncoder.createBitmap(bitMatrix);

            // Exibir o QR Code na ImageView
            ivQrcode.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar o QR Code", Toast.LENGTH_SHORT).show();
        }
    }

    // -------------------------------------------------------------------------------------------------//


    // Função para enviar o email
    public void enviarEmail(String subject, String content, String toEmail) {
        if (qrCodeBitmap == null) {
            Toast.makeText(this, "QR Code não gerado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar um Intent para enviar e-mail
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("image/jpeg");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);


        // Converter o bitmap do QR Code em um URI e adicionar como anexo
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            qrCodeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), qrCodeBitmap, "QRCode", null);
            Uri qrCodeUri = Uri.parse(path);
            emailIntent.putExtra(Intent.EXTRA_STREAM, qrCodeUri);

            // Iniciar a atividade de compartilhamento de e-mail
            startActivity(Intent.createChooser(emailIntent, "Escolha o aplicativo de e-mail:"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao anexar o QR Code ao e-mail", Toast.LENGTH_SHORT).show();
        }
    }
}




















