package com.techmaster.problemas.model.dao.ProblemaDAO.java;

import com.techmaster.problemas.model.entidades.Tecnico;
import com.techmaster.problemas.model.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {
    
    // Método necessário para popular a lista de responsáveis no futuro
    public List<Tecnico> listarTodos() {
        List<Tecnico> tecnicos = new ArrayList<>();
        String sql = "SELECT id_tecnico, nome, email FROM TECNICO";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Tecnico t = new Tecnico();
                t.setIdTecnico(rs.getInt("id_tecnico"));
                t.setNome(rs.getString("nome"));
                t.setEmail(rs.getString("email"));
                tecnicos.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return tecnicos;
    }
}