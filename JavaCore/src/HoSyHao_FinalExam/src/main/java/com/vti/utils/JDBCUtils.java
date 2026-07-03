package com.vti.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/mock_test2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin321@";

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (Exception e){
            System.out.println("Connection Failed! Check output console");
        }
        return null;
    }
}
