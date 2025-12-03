package com.seuportfolio.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Properties props = new Properties();

    // Bloco estático: Executa uma vez quando a classe é carregada
    static {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("ERRO: Arquivo db.properties não encontrado!");
            } else {
                props.load(input);
                // Carrega o Driver na memória manualmente (boa prática em projetos web antigos/híbridos)
                Class.forName(props.getProperty("db.driver"));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar configurações do banco", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
        );
    }
}