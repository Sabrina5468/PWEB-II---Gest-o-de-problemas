package com.techmaster.problemas.model.entidades;

public class Tecnico {
    private int idTecnico;
    private String nome;
    private String email;

    public Tecnico() {}
    
    // Inclua todos os Getters e Setters aqui (omitidos para concisão)
    public int getIdTecnico() { return idTecnico; }
    public void setIdTecnico(int idTecnico) { this.idTecnico = idTecnico; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}