package com.techmaster.problemas.model.dao.ProblemaDAO.java;

import com.techmaster.problemas.model.entidades.HistoricoStatus;
import com.techmaster.problemas.model.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoStatusDAO {

    private static final String SQL_INSERT = 
        "INSERT INTO HISTORICO_STATUS (id_problema, data_mudanca, status_novo, observacao, id_responsavel) VALUES (?, ?, ?, ?, ?)";

    // Registra uma nova mudança de status no log
    public void registrarMudanca(HistoricoStatus historico) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setInt(1, historico.getIdProblema());
            // Converte java.util.Date para java.sql.Date
            stmt.setDate(2, new java.sql.Date(historico.getDataMudanca().getTime())); 
            stmt.setString(3, historico.getStatusNovo());
            stmt.setString(4, historico.getObservacao());
            stmt.setInt(5, historico.getIdResponsavel());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao registrar histórico de status: " + e.getMessage());
            throw e;
        }
    }
}