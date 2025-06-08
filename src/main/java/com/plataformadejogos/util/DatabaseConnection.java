package com.plataformadejogos.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/seu_banco_de_dados?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456"; // MUDE AQUI

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado", e);
        }
    }
}