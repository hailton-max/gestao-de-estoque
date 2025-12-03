package com.seuportfolio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QueryLoader {
    private static Properties props = new Properties();

    static {
        try (InputStream input = QueryLoader.class.getClassLoader().getResourceAsStream("queries.properties")) {
            if (input == null) {
                System.out.println("ERRO: queries.properties não encontrado");
            } else {
                props.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}