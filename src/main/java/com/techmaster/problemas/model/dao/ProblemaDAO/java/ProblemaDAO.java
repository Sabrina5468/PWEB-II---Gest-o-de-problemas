package com.techmaster.problemas.model.dao.ProblemaDAO.java;

import com.techmaster.problemas.model.entidades.Problema;
import com.techmaster.problemas.model.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date; // IMPORTANTE: java.sql.Date para uso com JDBC

public class ProblemaDAO {

    // QUERY PARA LISTAGEM (Incluindo campos de resolução e JOINs)
    private static final String SQL_SELECT_ALL =
        "SELECT p.id_problema, p.descricao, p.data_identificacao, p.status, t.nome_tipo, i.nome_impacto, " +
        "p.id_tipo, p.id_impacto, p.causa_raiz_final, p.solucao_adotada, p.data_resolucao " +
        "FROM PROBLEMA p " +
        "JOIN TIPO_PROBLEMA t ON p.id_tipo = t.id_tipo " +
        "JOIN IMPACTO i ON p.id_impacto = i.id_impacto " +
        "ORDER BY p.data_identificacao DESC";

    private static final String SQL_SELECT_BY_ID =
        "SELECT p.id_problema, p.descricao, p.data_identificacao, p.status, t.nome_tipo, i.nome_impacto, " +
        "p.id_tipo, p.id_impacto, p.causa_raiz_final, p.solucao_adotada, p.data_resolucao " +
        "FROM PROBLEMA p " +
        "JOIN TIPO_PROBLEMA t ON p.id_tipo = t.id_tipo " +
        "JOIN IMPACTO i ON p.id_impacto = i.id_impacto " +
        "WHERE p.id_problema = ?";

    private static final String SQL_INSERT =
        "INSERT INTO PROBLEMA (descricao, data_identificacao, status, id_tipo, id_impacto) VALUES (?, ?, ?, ?, ?)";
        
    private static final String SQL_UPDATE_RESOLVER = 
        "UPDATE PROBLEMA SET status = 'Resolvido', causa_raiz_final = ?, solucao_adotada = ?, data_resolucao = ? WHERE id_problema = ?";


    // ------------------------------------------
    // IMPLEMENTAÇÃO DOS MÉTODOS
    // ------------------------------------------

    public List<Problema> listarTodos() throws SQLException {
        List<Problema> problemas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                problemas.add(mapRowToProblema(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratamento de erro simplificado
            throw new SQLException("Erro ao listar todos os problemas: " + e.getMessage());
        }
        return problemas;
    }
    
    public void salvar(Problema problema) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setString(1, problema.getDescricao());
            stmt.setDate(2, new Date(problema.getDataIdentificacao().getTime())); 
            stmt.setString(3, "Identificado"); 
            stmt.setInt(4, problema.getIdTipo());
            stmt.setInt(5, problema.getIdImpacto());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao salvar novo problema: " + e.getMessage());
        }
    }


    // MÉTODO: BUSCAR PROBLEMA POR ID (Para detalhamento)
    public Problema buscarPorId(int id) throws SQLException {
        Problema problema = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    problema = mapRowToProblema(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao buscar problema por ID: " + e.getMessage());
        }
        return problema;
    }


    // MÉTODO: RESOLVER PROBLEMA (Atualiza status, causa raiz e solução)
    public void resolver(Problema problema) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_RESOLVER)) {
            
            stmt.setString(1, problema.getCausaRaizFinal());
            stmt.setString(2, problema.getSolucaoAdotada());
            stmt.setDate(3, new Date(problema.getDataIdentificacao().getTime())); 
            stmt.setInt(4, problema.getIdProblema());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao resolver problema: " + e.getMessage());
        }
    }
    
    // MÉTODO AUXILIAR: Mapeia o resultado do banco para o objeto Problema
    private Problema mapRowToProblema(ResultSet rs) throws SQLException {
        Problema p = new Problema();
        p.setIdProblema(rs.getInt("id_problema"));
        p.setDescricao(rs.getString("descricao"));
        p.setDataIdentificacao(rs.getDate("data_identificacao"));
        p.setStatus(rs.getString("status"));
        
        // Campos de JOIN (necessitam de Getters/Setters no Problema.java)
        p.setNomeTipo(rs.getString("nome_tipo"));
        p.setNomeImpacto(rs.getString("nome_impacto"));
        p.setIdTipo(rs.getInt("id_tipo"));
        p.setIdImpacto(rs.getInt("id_impacto"));
        
        // Campos de resolução (necessitam de Getters/Setters no Problema.java)
        p.setCausaRaizFinal(rs.getString("causa_raiz_final"));
        p.setSolucaoAdotada(rs.getString("solucao_adotada"));
        p.setDataIdentificacao(rs.getDate("data_resolucao"));
        
        return p;
    }
}