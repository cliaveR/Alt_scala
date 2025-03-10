package com.example.demo.sharedObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect

{
    private static Connection connection;

    public static Connection getConnection()
    {
        try {
            //CHANGE TO DEPENDING ON WHERE DID YOU PUT THE DATABASE
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gig", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
