package com.projetofatec.qrcodeadmin.dao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projetofatec.qrcodeadmin.model.Evento;
import com.projetofatec.qrcodeadmin.MainActivity;
public class EventoDAO {

    // variavel do banco de dados
    private DatabaseReference eventosRef;

    public EventoDAO() {
        // Obtenha uma referência do banco de dados
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        eventosRef = database.getReference("eventos");
    }

    public void adicionarEvento(Evento evento) {
        // Envie o objeto para o banco de dados
        String eventoId = eventosRef.push().getKey(); // Gere um ID único para o evento
        eventosRef.child(eventoId).setValue(evento);
    }

}

