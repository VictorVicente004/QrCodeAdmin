package com.projetofatec.qrcodeadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.projetofatec.qrcodeadmin.MainActivity;
import com.projetofatec.qrcodeadmin.Menu;
import com.projetofatec.qrcodeadmin.R;
import com.projetofatec.qrcodeadmin.dao.Autenticacao;


public class Registrar extends AppCompatActivity {

    private EditText edtEmailRegistrar, edtSenhaRegistrar;
    private TextView txtVoltar;
    private Button btnRegistrar1;
    private Autenticacao autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        edtEmailRegistrar = findViewById(R.id.edtEmailRegistrar);
        edtSenhaRegistrar = findViewById(R.id.edtSenhaRegistrar);
        btnRegistrar1 = findViewById(R.id.btnRegistrar1);

        txtVoltar = findViewById(R.id.txtVoltar);

        // Inicialize a instância de Autenticacao
       autenticacao = new Autenticacao();

        btnRegistrar1.setOnClickListener(v -> fazerRegistro());


        txtVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }


    // Método para fazer registro
        private void fazerRegistro() {
            String email = edtEmailRegistrar.getText().toString().trim();
            String senha = edtSenhaRegistrar.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            autenticacao.createUserWithEmailAndPassword(email, senha, new Autenticacao.AuthListener() {
                @Override
                public void onAuthSuccess(String adminId) {
                    Intent intent = new Intent(Registrar.this, Menu.class);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onAuthFailure(String errorMessage) {
                    Toast.makeText(Registrar.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });



    }
}