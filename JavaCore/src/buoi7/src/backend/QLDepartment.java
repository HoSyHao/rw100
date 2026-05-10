package buoi7.src.backend;


import buoi7.src.entity.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QLDepartment {
    public static void showDepartment() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/rw100_testing_system";
        String username = "root";
        String password = "admin321@";// mk mysql
        try {
            //b1: connect db
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                System.out.println("Connected to the database");
            }

            //b2: get data from Department table
            String query = "SELECT * FROM `department`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query); // Excute sql statement then assign result into rs
            List<Department> departmentList = new ArrayList<>(); // Save data from DB
            while (rs.next()) { // Loop through each line of rs
                int id = rs.getInt("department_id"); // Get value from column department_id
                String name = rs.getString("department_name"); // Get value from column department_name
                Department department = new Department(id, name);
                departmentList.add(department);
            }
            if (departmentList.isEmpty()) {
                System.out.println("No departments found");
            } else {
                for(Department department : departmentList){
                    System.out.println(department);
                }
            }

        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
        }
    }

    // Tìm các phòng ban theo id và có tên có các kí tự như tham số
    public static void findDepartmentByIdAndName(String searchName, int searchId) throws ClassNotFoundException, SQLException {

        String query = "SELECT * FROM `department` WHERE LOWER(department_name) LIKE ? AND department_id = ? ";

        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)
        ) {

            stmt.setString(1, "%" + searchName.toLowerCase() + "%"); //Assign parm to ? 1
            stmt.setInt(2, searchId); //Assign parm to ? 2

            try (ResultSet rs = stmt.executeQuery()) {
                List<Department> departmentList = new ArrayList<>(); // Save data from DB
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
                if (!departmentList.isEmpty()) {
                    for (Department department : departmentList) {
                        System.out.println(department);
                    }
                } else  {
                    System.out.println("No department found with name " + searchName + " and id: " + searchId);
                }
            }
        }
    }

    public static Connection connectDB() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/rw100_testing_system";
        String username = "root";
        String password = "admin321@";

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection(url, username, password);

        System.out.println("Connected to the database");

        return conn;
    }

    //method in ra các phòng ban có >=2 nhân viên
    public static void findeDepartmentHaveLessThan2Acc() throws SQLException, ClassNotFoundException {
        String query = "SELECT a.department_id,\n" +
                "\t   d.department_name,\n" +
                "       COUNT(1) AS total_acc,\n" +
                "\t   GROUP_CONCAT(a.account_id) AS accounts\n" +
                "FROM `account` a\n" +
                "JOIN `department` d ON d.department_id = a.department_id\n" +
                "GROUP BY a.department_id\n" +
                "HAVING total_acc >= 2";

        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                List<Department> departmentList = new ArrayList<>(); // Save data from DB
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
                if (!departmentList.isEmpty()) {
                    for (Department department : departmentList) {
                        System.out.println(department);
                    }
                } else {
                    System.out.println("No department have more than 2 accounts!!!!");
                }

            }
        }
    }
}
