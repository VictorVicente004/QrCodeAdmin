package com.projetofatec.qrcodeadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.projetofatec.qrcodeadmin.MainActivity;
import com.projetofatec.qrcodeadmin.R;
import com.projetofatec.qrcodeadmin.dao.Autenticacao;

public class Login_e_Registro extends AppCompatActivity {


    private EditText edtEmail, edtSenha;
    private Button  btnRegistrar, btnLogar;
    private Autenticacao autenticacao;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_eregistro);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogar = findViewById(R.id.btnLogar);
        btnRegistrar = findViewById(R.id.btnRegistrar);


        // Inicialize a instância de Autenticacao
        autenticacao = new Autenticacao();

        btnLogar.setOnClickListener(v -> fazerLogin());

        btnRegistrar.setOnClickListener(v -> fazerRegistro());

    }

    // Método para fazer login
    private void fazerLogin() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        autenticacao.signInWithEmailAndPassword(email, senha, new Autenticacao.AuthListener() {
            @Override
            public void onAuthSuccess(String adminId) {
                Intent intent = new Intent(Login_e_Registro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthFailure(String errorMessage) {
                Toast.makeText(Login_e_Registro.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Método para fazer registro
    private void fazerRegistro() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        autenticacao.createUserWithEmailAndPassword(email, senha, new Autenticacao.AuthListener() {
            @Override
            public void onAuthSuccess(String adminId) {
                Intent intent = new Intent(Login_e_Registro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthFailure(String errorMessage) {
                Toast.makeText(Login_e_Registro.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
