package com.techmaster.problemas.model.entidades;

import java.util.Date;

public class AcaoCorretiva {
    private int idAcao;
    private int idProblema;
    private String descricao;
    private Date dataRegistro;
    private int idAutor; // FK Tecnico
    
    public AcaoCorretiva() {}
    
    // Inclua todos os Getters e Setters aqui (omitidos para concisão)
    public int getIdAcao() { return idAcao; }
    public void setIdAcao(int idAcao) { this.idAcao = idAcao; }
    public int getIdProblema() { return idProblema; }
    public void setIdProblema(int idProblema) { this.idProblema = idProblema; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Date getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(Date dataRegistro) { this.dataRegistro = dataRegistro; }
    public int getIdAutor() { return idAutor; }
    public void setIdAutor(int idAutor) { this.idAutor = idAutor; }
}
