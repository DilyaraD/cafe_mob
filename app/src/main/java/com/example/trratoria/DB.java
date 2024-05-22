package com.example.trratoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB {

    private static String ip = "146.19.207.159";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "cafe";
    private static String username = "sa";
    private static String password = "DbReFoRpm228@";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private Connection connection = null;

    public static void insertData(String firstName, String lastName, String phoneNumber, String bookingDate, String bookingTime, int guestsCount, int stolID) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String query = "INSERT INTO Bron (FirstName, LastName, PhoneNumber, BookingDate, BookingTime, GuestsCount, StolID, Status) VALUES (?, ?, ?, ?, ?, ?, ?, 'Active')";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.setString(4, bookingDate);
            statement.setString(5, bookingTime);
            statement.setInt(6, guestsCount);
            statement.setInt(7, stolID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}