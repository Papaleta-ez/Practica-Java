package com.example.registroempleados.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Centraliza la configuración de acceso a biblioteca_fx.
 * Configure DB_URL, DB_USER y DB_PASSWORD como variables de entorno.
 */
public final class DatabaseConnection {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/biblioteca_fx";

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        String url = setting("DB_URL", DEFAULT_URL);
        String user = setting("DB_USER", "postgres");
        String password = setting("DB_PASSWORD", "");
        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
