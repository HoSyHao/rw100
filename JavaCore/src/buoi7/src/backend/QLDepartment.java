package buoi7.src.backend;


import buoi7.src.entity.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static buoi7.utils.JDBCUtils.getConnection;

public class QLDepartment {
    public static List<Department> findAllDepartment() {
        List<Department> departmentList = new ArrayList<>(); // Save data from DB
        String query = "SELECT * FROM `department`";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) { // Excute sql statement then assign result into rs
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    // Tìm các phòng ban theo id và có tên có các kí tự như tham số
    public static List<Department> findDepartmentByIdAndName(String searchName, int searchId) {

        List<Department> departmentList = new ArrayList<>(); // Save data from DB
        String query = "SELECT * FROM `department` WHERE LOWER(department_name) LIKE ? AND department_id = ? ";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)
        ) {

            stmt.setString(1, "%" + searchName.toLowerCase() + "%"); //Assign parm to ? 1
            stmt.setInt(2, searchId); //Assign parm to ? 2

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departmentList;
    }

    //method in ra các phòng ban có >=2 nhân viên
    public static List<Department> findeDepartmentHaveMoreThan2Acc() {
        List<Department> departmentList = new ArrayList<>(); // Save data from DB
        String query = "SELECT d.*,\n" +
                "       COUNT(1) AS total_acc,\n" +
                "\t   GROUP_CONCAT(a.account_id) AS accounts\n" +
                "FROM `account` a\n" +
                "JOIN `department` d ON d.department_id = a.department_id\n" +
                "GROUP BY d.department_id\n" +
                "HAVING total_acc >= 2";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departmentList;
    }

    // Tạo phòng ban với tên phòng ban đưa từ ngoài vào
    public static boolean createDepartment(String departmentName) {
        String insert = "insert into `department` (department_name) values (?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, departmentName);
            int rows = stmt.executeUpdate(); // Number of lines affacted in db
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa phòng ban theo tên
    public static boolean deleteDepartment(String departmentName) {
        String insert = "delete from `department` where department_name = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, departmentName);
            int rows = stmt.executeUpdate(); // Number of lines affacted in db
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật phòng ban
    public static boolean updateDepartment(int id, String departmentName) {
        String insert = "UPDATE department\n" +
                "SET department_name = ?\n" +
                "WHERE department_id = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, departmentName);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate(); // Number of lines affacted in db
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showDepartment(List<Department> departmentList, String searchName, Integer searchId) {
        if (searchName == null && searchId == null && departmentList.isEmpty()) {
            System.out.println("No departments found");
        } else if (departmentList.isEmpty()) {
            System.out.println("No department found with name: " + searchName + " and id: " + searchId);
        } else {
            System.out.println("+-----+--------------------+");
            System.out.printf("|%5s|%20s|\n", "ID", "NAME");
            System.out.println("+-----+--------------------+");
            for (Department department : departmentList) {
                System.out.printf("|%5s|%20s|\n", department.getDepartmentID(), department.getDepartmentName());
            }
            System.out.println("+-----+--------------------+");
        }
    }
}
