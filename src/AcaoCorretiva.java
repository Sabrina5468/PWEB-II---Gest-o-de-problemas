package model;

import java.util.Date;

public class AcaoCorretiva {
    private String descricao;
    private Date data;
    private Tecnico autor;

    public AcaoCorretiva(String descricao, Date data, Tecnico autor) {
        this.descricao = descricao;
        this.data = data;
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Ação: " + descricao + " | Data: " + data + " | Autor: " + autor.getNome();
    }
}

