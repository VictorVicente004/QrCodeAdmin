package com.projetofatec.qrcodeadmin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.projetofatec.qrcodeadmin.dao.EventoDAO;
import com.projetofatec.qrcodeadmin.model.Evento;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    private EditText nomeEvento, nomeParticipante, detalheEvento, dataEvento, localEvento;
    private Button  btnEnviar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //FindView

        nomeEvento = findViewById(R.id.nomeEvento);
        nomeParticipante = findViewById(R.id.nomeParticipante);
        detalheEvento = findViewById(R.id.detalheEvento);
        btnEnviar = findViewById(R.id.btnEnviar);
        dataEvento = findViewById(R.id.dataEvento);
        localEvento = findViewById(R.id.localEvento);

        // Configurar DatePickerDialog
        dataEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Note que o mês começa do zero
                        String selectedDate = String.format("%02d/%02d/%02d", dayOfMonth, month + 1, year % 100);
                        dataEvento.setText(selectedDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Criar instancia do objeto Evento Dao
        EventoDAO eventoDAO = new EventoDAO();


        // Defina um OnClickListener para o Button
        btnEnviar.setOnClickListener(v -> {
            // Obtenha os dados preenchidos nos EditTexts
            String nomeEventoStr = nomeEvento.getText().toString();
            String nomeParticipanteStr = nomeParticipante.getText().toString();
            String detalhesEventoStr = detalheEvento.getText().toString();
            String dataEventoStr = dataEvento.getText().toString();
            String localEventoStr = localEvento.getText().toString();

            // Crie um objeto Evento com os dados preenchidos
            Evento evento = new Evento(nomeEventoStr, nomeParticipanteStr, detalhesEventoStr, dataEventoStr, localEventoStr);

            // Adicione o evento ao banco de dados
            eventoDAO.adicionarEvento(evento);



            // enviar convite
            // Concatenar os dados do evento em uma única string
            String dadosEvento = "Nome do Evento: " + nomeEventoStr + "\n" +
                    "Nome do Participante: " + nomeParticipanteStr + "\n" +
                    "Detalhes: " + detalhesEventoStr + "\n" +
                    "Data: " + dataEventoStr + "\n" +
                    "Local: " + localEventoStr;

            // Criar um Intent para a atividade de envio de e-mail
            Intent intent = new Intent(MainActivity.this, EnviarEmail.class);
            intent.putExtra("dadosEvento", dadosEvento);
            startActivity(intent);
        });

    }
}