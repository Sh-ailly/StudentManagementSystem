package com.example.studentmanagementsystem;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    protected static String db = "SMS";
    protected static String hostname = "localhost";
    protected static String port = "3306";
    protected static String username = "root";
    protected static String password = "password";
    private Exception lastException;

    public Connection CONN() {
        Connection conn = null;
        try {
            // Check if the MySQL JDBC driver is loaded
            Class.forName("com.mysql.jdbc.Driver");

            // Construct the URL for the database connection
            String url = "jdbc:mysql://"+hostname+":"+port+"/"+db;

            Log.d("CONNECTION_INFO", "Connecting to database: " + url);

            // Attempt to establish the database connection
            conn = DriverManager.getConnection(url, username, password);

            Log.d("CONNECTION_INFO", "Connection successful!");
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", "MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            Log.e("ERROR", "Failed to connect to database: " + e.getMessage(), e);

        }
        return conn;
    }

}
