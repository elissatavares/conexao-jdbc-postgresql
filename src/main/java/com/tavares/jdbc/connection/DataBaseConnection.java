package com.tavares.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DataBaseConnection {

    private static DataBaseConnection instance;

    private DataBaseConnection() {

    }

    public static DataBaseConnection getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/connection-jdbc","postgres", "postgres");
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro durante a conexao com o banco de dados");
        }
    }

}
