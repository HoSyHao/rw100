package buoi7.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/rw100_testing_system";
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