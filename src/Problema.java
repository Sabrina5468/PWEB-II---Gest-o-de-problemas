package model;

import java.util.*;

public class Problema {
    private int id;
    private String descricao;
    private Date dataIdentificacao;
    private String tipo;
    private String impacto;
    private String status;
    private Tecnico responsavel;
    private List<AcaoCorretiva> acoes = new ArrayList<>();
    private String causaRaiz;
    private Date dataEncerramento;

    public Problema(int id, String descricao, Date dataIdentificacao, String tipo, String impacto, String status, Tecnico responsavel) {
        this.id = id;
        this.descricao = descricao;
        this.dataIdentificacao = dataIdentificacao;
        this.tipo = tipo;
        this.impacto = impacto;
        this.status = status;
        this.responsavel = responsavel;
    }

    public int getId() { return id; }

    public void adicionarAcao(AcaoCorretiva acao) {
        acoes.add(acao);
    }

    public void encerrar(String causaRaiz) {
        this.causaRaiz = causaRaiz;
        this.status = "Encerrado";
        this.dataEncerramento = new Date();
    }

    @Override
    public String toString() {
        return "Problema #" + id + " - " + descricao +
                "\nData: " + dataIdentificacao +
                "\nTipo: " + tipo +
                "\nImpacto: " + impacto +
                "\nStatus: " + status +
                "\nResponsável: " + responsavel.getNome() +
                "\nAções: " + acoes +
                "\nCausa Raiz: " + (causaRaiz == null ? "Não registrada" : causaRaiz) +
                "\nEncerrado em: " + (dataEncerramento == null ? "Ainda aberto" : dataEncerramento);
    }
}

