package com.techmaster.problemas.model.entidades;

import java.util.Date;

public class Problema {
    private int idProblema;
    private String descricao;
    private Date dataIdentificacao;
    private String status;
    private int idTipo; 
    private int idImpacto; 
    private String nomeTipo; // Para uso na View
    private String nomeImpacto; // Para uso na View

    public Problema() {}
    
    // Inclua todos os Getters e Setters aqui (omitidos para concisão)
    public int getIdProblema() { return idProblema; }
    public void setIdProblema(int idProblema) { this.idProblema = idProblema; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Date getDataIdentificacao() { return dataIdentificacao; }
    public void setDataIdentificacao(Date dataIdentificacao) { this.dataIdentificacao = dataIdentificacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }
    public int getIdImpacto() { return idImpacto; }
    public void setIdImpacto(int idImpacto) { this.idImpacto = idImpacto; }
    public String getNomeTipo() { return nomeTipo; }
    public void setNomeTipo(String nomeTipo) { this.nomeTipo = nomeTipo; }
    public String getNomeImpacto() { return nomeImpacto; }
    public void setNomeImpacto(String nomeImpacto) { this.nomeImpacto = nomeImpacto; }
}