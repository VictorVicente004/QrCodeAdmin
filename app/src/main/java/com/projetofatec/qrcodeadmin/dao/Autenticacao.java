package com.projetofatec.qrcodeadmin.dao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Autenticacao {

private FirebaseAuth mAuth;

public Autenticacao(){
    mAuth = FirebaseAuth.getInstance();
}

    // Método para registrar um novo usuário com e-mail e senha
    public void createUserWithEmailAndPassword(String email, String password, AuthListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registro bem-sucedido
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String adminId = user.getUid();
                            // Recupere os detalhes do administrador se necessário
                            listener.onAuthSuccess(adminId);
                        }
                    } else {
                        // Registro falhou
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Trate o erro aqui
                            listener.onAuthFailure(exception.getMessage());
                        }
                    }
                });
    }

    // Método para fazer login com e-mail e senha
    public void signInWithEmailAndPassword(String email, String password, AuthListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login bem-sucedido
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String adminId = user.getUid();
                            // Recupere os detalhes do administrador se necessário
                            listener.onAuthSuccess(adminId);
                        }
                    } else {
                        // Login falhou
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Trate o erro aqui
                            listener.onAuthFailure(exception.getMessage());
                        }
                    }
                });
    }
    // Método para fazer logout
    public void signOut() {
        mAuth.signOut();
    }

    // Interface para escutar os eventos de autenticação
    public interface AuthListener {
        void onAuthSuccess(String adminId);
        void onAuthFailure(String errorMessage);
    }
}
