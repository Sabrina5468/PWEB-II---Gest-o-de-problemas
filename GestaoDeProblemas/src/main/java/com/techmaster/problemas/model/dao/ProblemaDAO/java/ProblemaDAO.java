package com.techmaster.problemas.model.dao.ProblemaDAO.java;

import com.techmaster.problemas.model.entidades.Problema;
import com.techmaster.problemas.model.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date; // java.sql.Date para uso com JDBC

public class ProblemaDAO {

    private static final String SQL_SELECT_ALL = 
        "SELECT p.id_problema, p.descricao, p.data_identificacao, p.status, t.nome_tipo, i.nome_impacto, p.id_tipo, p.id_impacto " +
        "FROM PROBLEMA p " +
        "JOIN TIPO_PROBLEMA t ON p.id_tipo = t.id_tipo " +
        "JOIN IMPACTO i ON p.id_impacto = i.id_impacto " +
        "ORDER BY p.data_identificacao DESC";

    private static final String SQL_INSERT = 
        "INSERT INTO PROBLEMA (descricao, data_identificacao, status, id_tipo, id_impacto) VALUES (?, ?, ?, ?, ?)";
        
    public List<Problema> listarTodos() {
        List<Problema> problemas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Problema p = new Problema();
                p.setIdProblema(rs.getInt("id_problema"));
                p.setDescricao(rs.getString("descricao"));
                p.setDataIdentificacao(rs.getDate("data_identificacao"));
                p.setStatus(rs.getString("status"));
                p.setNomeTipo(rs.getString("nome_tipo"));
                p.setNomeImpacto(rs.getString("nome_impacto"));
                p.setIdTipo(rs.getInt("id_tipo"));
                p.setIdImpacto(rs.getInt("id_impacto"));
                problemas.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return problemas;
    }
    
    public void salvar(Problema problema) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setString(1, problema.getDescricao());
            // Converte java.util.Date para java.sql.Date
            stmt.setDate(2, new Date(problema.getDataIdentificacao().getTime())); 
            stmt.setString(3, "Identificado"); 
            stmt.setInt(4, problema.getIdTipo());
            stmt.setInt(5, problema.getIdImpacto());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}