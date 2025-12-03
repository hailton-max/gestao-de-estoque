package com.seuportfolio.util;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            System.out.println("SUCESSO! Conexão com MySQL aberta.");
            conn.close();
        } catch (Exception e) {
            System.out.println("ERRO ao conectar:");
            e.printStackTrace();
        }
    }
}