package com.tavares.jdbc;

import com.tavares.jdbc.connection.DataBaseConnection;
import com.tavares.jdbc.entity.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Connection connection = DataBaseConnection.getInstance().getConnection();
            createTable(connection);
            insertUsuarios(connection);

            List<Usuario> usuarios = findUsuarios(connection);
            usuarios.forEach(System.out::println);

            deleteAll(connection);
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS usuario (" +
                "nome VARCHAR(255) NOT NULL, " +

                "email VARCHAR(255) NOT NULL)";
        Statement statement = connection.createStatement();
        statement.executeUpdate(createTable);
        statement.close();
        System.out.println("Tabela 'usuario' criada com sucesso!");
    }

    private static void insertUsuarios(Connection connection) throws SQLException {
        /*
            PreparedStatement: Similar ao Statement, mas permite o uso de parâmetros para proteger contra SQL injection.
            connection.prepareStatement(insertSQL): Cria uma instância de PreparedStatement com o comando SQL parametrizado.
            preparedStatement.setString(1, usuario.getNome()): Define o valor do primeiro parâmetro (nome) no comando SQL.
            preparedStatement.setString(2, usuario.getEmail()): Define o valor do segundo parâmetro (email) no comando SQL.
            preparedStatement.addBatch(): Adiciona o comando parametrizado a um lote de comandos.
            preparedStatement.executeBatch(): Executa todos os comandos no lote.
            preparedStatement.close(): Fecha o PreparedStatement após o uso.
        */
        List<Usuario> usuarios = List.of(
                new Usuario("João", "joao@example.com"),
                new Usuario("Maria", "maria@example.com"),
                new Usuario("Pedro", "pedro@example.com")
        );

        String insertSQL = "INSERT INTO usuario (nome, email) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

        for (Usuario usuario : usuarios) {
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        preparedStatement.close();
        System.out.println("Usuários inseridos com sucesso!");
    }

    private static List<Usuario> findUsuarios(Connection connection) throws SQLException {
        /*
        preparedStatement.executeQuery(): Executa a consulta SQL e retorna um ResultSet contendo os resultados.
        resultSet.next(): Itera sobre cada linha do resultado.
        resultSet.getString("nome"): Obtém o valor da coluna "nome" da linha atual.
        resultSet.getString("email"): Obtém o valor da coluna "email" da linha atual.
        Adiciona os dados do usuário a uma lista de usuários.
        */
        List<Usuario> usuarios = new ArrayList<>();
        String selectSQL = "SELECT * FROM usuario";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            String nome = resultSet.getString("nome");
            String email = resultSet.getString("email");
            usuarios.add(new Usuario(nome, email));
        }
        statement.close();
        resultSet.close();
        return usuarios;
    }

    private static void deleteAll(Connection connection) throws SQLException {
        String deleteSQL = "DELETE FROM usuario";
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteSQL);
        statement.close();
        System.out.println("Todos os registros da tabela 'usuario' foram removidos com sucesso!");
    }
}