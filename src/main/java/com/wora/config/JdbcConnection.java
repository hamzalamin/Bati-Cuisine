package com.wora.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    private static JdbcConnection INSTANCE = null;
    private static Connection CONNECTION = null;

    private final String url = "jdbc:postgresql://localhost:5432/bati_cuisine2";
    private final String username = "postgres";
    private final String password = "123";

    private JdbcConnection(){
        initConnection();
    }

    public static JdbcConnection getInstance(){
        try {
            if (INSTANCE == null || !INSTANCE.getConnection().isClosed()){
                INSTANCE = new JdbcConnection();
            }
            return INSTANCE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection(){
        return CONNECTION;
    }

    public static void closeConnection(){
        if (INSTANCE != null){
            try {
                INSTANCE.getConnection().close();
                INSTANCE = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initConnection() {
        try {
            CONNECTION = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
