package com.techmaster.problemas.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    
    private static final String URL = "jdbc:h2:~/test"; 
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static boolean initialized = false;

    public static Connection getConnection() throws SQLException {
        if (!initialized) {
            initializeDatabase();
            initialized = true;
        }
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver H2 não encontrado: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            // --- CRIAÇÃO DAS 6 TABELAS ---
            stmt.execute("CREATE TABLE IF NOT EXISTS TIPO_PROBLEMA (id_tipo INT PRIMARY KEY AUTO_INCREMENT, nome_tipo VARCHAR(100) NOT NULL UNIQUE);");
            stmt.execute("CREATE TABLE IF NOT EXISTS IMPACTO (id_impacto INT PRIMARY KEY AUTO_INCREMENT, nome_impacto VARCHAR(50) NOT NULL UNIQUE);");
            stmt.execute("CREATE TABLE IF NOT EXISTS TECNICO (id_tecnico INT PRIMARY KEY AUTO_INCREMENT, nome VARCHAR(150) NOT NULL, email VARCHAR(150) NOT NULL UNIQUE);");
            stmt.execute("CREATE TABLE IF NOT EXISTS PROBLEMA (id_problema INT PRIMARY KEY AUTO_INCREMENT, descricao VARCHAR(500) NOT NULL, data_identificacao DATE NOT NULL, status VARCHAR(50) NOT NULL, id_tipo INT, id_impacto INT, data_resolucao DATE, causa_raiz_final VARCHAR(500), solucao_adotada VARCHAR(500), FOREIGN KEY (id_tipo) REFERENCES TIPO_PROBLEMA(id_tipo), FOREIGN KEY (id_impacto) REFERENCES IMPACTO(id_impacto));");
            stmt.execute("CREATE TABLE IF NOT EXISTS ACAO_CORRETIVA (id_acao INT PRIMARY KEY AUTO_INCREMENT, id_problema INT NOT NULL, descricao VARCHAR(500) NOT NULL, data_registro DATE NOT NULL, id_autor INT NOT NULL, FOREIGN KEY (id_problema) REFERENCES PROBLEMA(id_problema), FOREIGN KEY (id_autor) REFERENCES TECNICO(id_tecnico));");
            stmt.execute("CREATE TABLE IF NOT EXISTS HISTORICO_STATUS (id_historico INT PRIMARY KEY AUTO_INCREMENT, id_problema INT NOT NULL, data_mudanca DATE NOT NULL, status_anterior VARCHAR(50), status_novo VARCHAR(50) NOT NULL, observacao VARCHAR(500), id_responsavel INT, FOREIGN KEY (id_problema) REFERENCES PROBLEMA(id_problema), FOREIGN KEY (id_responsavel) REFERENCES TECNICO(id_tecnico));");

            // --- INSERÇÃO DE DADOS INICIAIS (SEED) ---
            stmt.execute("MERGE INTO TIPO_PROBLEMA VALUES (1, 'Lentidão de Serviço');");
            stmt.execute("MERGE INTO IMPACTO VALUES (1, 'Alto');");
            stmt.execute("MERGE INTO TECNICO VALUES (1, 'João Silva', 'joao.silva@techmaster.com');");
            stmt.execute("MERGE INTO PROBLEMA VALUES (1, 'Sistema de Vendas apresenta lentidão extrema.', '2025-10-01', 'Em Investigação', 1, 1, NULL, NULL, NULL);");
            
        } catch (Exception e) {
            System.err.println("Aviso/Erro durante a inicialização do H2: " + e.getMessage());
        }
    }
}