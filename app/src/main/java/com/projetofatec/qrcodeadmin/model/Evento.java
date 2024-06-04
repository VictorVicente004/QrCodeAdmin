package com.projetofatec.qrcodeadmin.model;

public class Evento {

    private String nomeEvento;
    private String nomeParticipante;
    private String detalhesEvento;
    private String dataEvento;
    private String localEvento;


    //Construtor vazio
    public Evento() {
    }


    //Construtor
    public Evento(String nomeEvento, String nomeParticipante, String detalhesEvento, String dataEvento, String localEvento) {
        this.nomeEvento = nomeEvento;
        this.nomeParticipante = nomeParticipante;
        this.detalhesEvento = detalhesEvento;
        this.dataEvento = dataEvento;
        this.localEvento = localEvento;
    }

    //getters e setters


    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }

    public String getDetalhesEvento() {
        return detalhesEvento;
    }

    public void setDetalhesEvento(String detalhesEvento) {
        this.detalhesEvento = detalhesEvento;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }
}
