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

public class EnviarEmail extends AppCompatActivity {

    private EditText edtAssunto, edtConteudo, edtEmail;
    private Button btnEnviarConvite;
    private Bitmap qrCodeBitmap;
    private ImageView ivQrcode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enviar_email);

        edtAssunto = findViewById(R.id.subject);
        edtConteudo = findViewById(R.id.content);
        edtEmail = findViewById(R.id.to_email);
        btnEnviarConvite = findViewById(R.id.btnEnviarConvite);
        ivQrcode = findViewById(R.id.ivQrcode);


        // Recebe o QR Code codificado em Base64 e os dados do evento
        Intent intent = getIntent();
        String encodedBitmap = intent.getStringExtra("qrCodeBitmap");
        if (encodedBitmap != null) {
            byte[] decodedString = Base64.decode(encodedBitmap, Base64.DEFAULT);
            qrCodeBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivQrcode.setImageBitmap(qrCodeBitmap);
        }

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
    }

    // função para enviar o email
    public void enviarEmail(String subject, String content, String toEmail) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/octet-stream");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);

        // Converter o Bitmap para URI
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), qrCodeBitmap, "QRCode", null);
        Uri qrCodeUri = Uri.parse(path);

        emailIntent.putExtra(Intent.EXTRA_STREAM, qrCodeUri);

        try {
            startActivity(Intent.createChooser(emailIntent, "Escolha o E-mail:"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Não há clientes de e-mail instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}
