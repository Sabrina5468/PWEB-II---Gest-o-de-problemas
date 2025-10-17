package com.techmaster.problemas.model.dao.ProblemaDAO.java;

import com.techmaster.problemas.model.entidades.Impacto;
import com.techmaster.problemas.model.entidades.TipoProblema;
import com.techmaster.problemas.model.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.techmaster.problemas.model.entidades.TipoProblema;
import com.techmaster.problemas.model.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DominioDAO {
    
    public List<TipoProblema> listarTipos() {
        List<TipoProblema> tipos = new ArrayList<>();
        String sql = "SELECT id_tipo, nome_tipo FROM TIPO_PROBLEMA";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TipoProblema tipo = new TipoProblema();
                tipo.setIdTipo(rs.getInt("id_tipo"));
                tipo.setNomeTipo(rs.getString("nome_tipo"));
                tipos.add(tipo);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return tipos;
    }

    public List<Impacto> listarImpactos() {
        List<Impacto> impactos = new ArrayList<>();
        String sql = "SELECT id_impacto, nome_impacto FROM IMPACTO";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Impacto impacto = new Impacto();
                impacto.setIdImpacto(rs.getInt("id_impacto"));
                impacto.setNomeImpacto(rs.getString("nome_impacto"));
                impactos.add(impacto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return impactos;
    }
}