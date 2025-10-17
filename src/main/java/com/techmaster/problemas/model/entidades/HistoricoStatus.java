package com.techmaster.problemas.model.entidades;

import java.util.Date;

public class HistoricoStatus {
    private int idHistorico;
    private int idProblema;
    private Date dataMudanca;
    private String statusAnterior;
    private String statusNovo;
    private String observacao;
    private int idResponsavel; // FK Tecnico
    
    public HistoricoStatus() {}
    
    // Inclua todos os Getters e Setters aqui (omitidos para concisão)
    public int getIdHistorico() { return idHistorico; }
    public void setIdHistorico(int idHistorico) { this.idHistorico = idHistorico; }
    public int getIdProblema() { return idProblema; }
    public void setIdProblema(int idProblema) { this.idProblema = idProblema; }
    public Date getDataMudanca() { return dataMudanca; }
    public void setDataMudanca(Date dataMudanca) { this.dataMudanca = dataMudanca; }
    public String getStatusAnterior() { return statusAnterior; }
    public void setStatusAnterior(String statusAnterior) { this.statusAnterior = statusAnterior; }
    public String getStatusNovo() { return statusNovo; }
    public void setStatusNovo(String statusNovo) { this.statusNovo = statusNovo; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public int getIdResponsavel() { return idResponsavel; }
    public void setIdResponsavel(int idResponsavel) { this.idResponsavel = idResponsavel; }
}