package com.ecommerce.sellerpage.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static final String DB_HOST = "localhost";
    private static final String DB_NAME = "dress";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "***********";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME;
    private static Connection connection;
    private static ConnectionSingleton singleton = new ConnectionSingleton();
    private ConnectionSingleton() {
        try {
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            System.out.println("Connected");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Server down. Try again later.");
        }
    }
    public static Connection getConnection() {
        return connection;
    }
}
