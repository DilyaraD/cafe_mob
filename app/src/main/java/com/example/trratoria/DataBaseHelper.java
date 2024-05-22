package com.example.trratoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHelper {

    private static final String URL = "jdbc:jtds:sqlserver://146.19.207.159:1433;databaseName=cafe";
    private static final String USER = "sa";
    private static final String PASSWORD = "DbReFoRpm228@";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC-драйвер не найден.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Не удалось создать подключение к базе данных.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void queryDatabase() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            if (connection == null) {
                throw new SQLException("Подключение равно null.");
            }

            statement = connection.createStatement();
            String query = "SELECT * FROM your_table_name";
            resultSet = statement.executeQuery(query);

            while (
                    resultSet.next
                            ()) {
                // Обрабатывайте результаты
                String columnValue = resultSet.getString("column_name");
                System.out.println(columnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
} 