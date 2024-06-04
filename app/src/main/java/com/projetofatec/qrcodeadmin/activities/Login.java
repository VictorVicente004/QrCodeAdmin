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

public class Login extends AppCompatActivity {


    private EditText edtEmail, edtSenha;
    private Button  btnLogar;
    private TextView txtRegistrar;
    private Autenticacao autenticacao;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogar = findViewById(R.id.btnLogar);
        txtRegistrar = findViewById(R.id.txtRegistrar);

        // Inicialize a instância de Autenticacao
        autenticacao = new Autenticacao();

        btnLogar.setOnClickListener(v -> fazerLogin());


        // Chamar tela de cadastro
        txtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registrar.class );
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(Login.this, Menu.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthFailure(String errorMessage) {
                Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
