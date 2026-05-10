package buoi7.src.backend;

import buoi7.src.entity.Account;
import buoi7.src.entity.Department;
import buoi7.src.entity.Position;
import buoi7.src.enums.PositionName;

import java.sql.*;
import java.util.ArrayList;

public class QLAccount {
    public static void showAllAccount() throws SQLException, ClassNotFoundException {
        String query = "select * from `account` a\n" +
                "left join `department` d ON d.department_id = a.department_id\n" +
                "left join `position` p ON p.position_id = a.position_id";

        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                ArrayList<Account> accounts = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("account_id");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    String fullname = rs.getString("full_name");

                    Integer departmentId = (Integer) rs.getObject("department_id");
                    Integer positionId = (Integer) rs.getObject("position_id");

                    Position position = null;
                    Department department = null;

                    //Kiem tra chua co id thi khong tao pos va dept
                    if (departmentId != null) {
                        String departmentName = rs.getString("department_name");
                        department = new Department(departmentId, departmentName);
                    }
                    if (positionId != null) {
                        String positionNameStr = rs.getString("position_name");
                        PositionName positionName = PositionName.valueOf(positionNameStr);
                        position = new Position(positionId, positionName);
                    }
                    Account account = new Account(id, email, username, fullname, department, position);
                    accounts.add(account);
                }
                if (accounts.isEmpty()) {
                    System.out.println("No accounts found");
                } else {
                    for (Account account : accounts) {
                        System.out.println(account);
                    }
                }

            }

        }
    }

    public static void findByFullname(String val) throws SQLException, ClassNotFoundException {
        String query = "select * from `account` a\n" +
                "left join `department` d ON d.department_id = a.department_id\n" +
                "left join `position` p ON p.position_id = a.position_id\n" +
                "where lower(a.full_name) like lower(?)";

        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, "%" + val + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Account> accounts = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("account_id");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    String fullname = rs.getString("full_name");

                    Integer departmentId = (Integer) rs.getObject("department_id");
                    Integer positionId = (Integer) rs.getObject("position_id");

                    Position position = null;
                    Department department = null;

                    //Kiem tra chua co id thi khong tao pos va dept
                    if (departmentId != null) {
                        String departmentName = rs.getString("department_name");
                        department = new Department(departmentId, departmentName);
                    }
                    if (positionId != null) {
                        String positionNameStr = rs.getString("position_name");
                        PositionName positionName = PositionName.valueOf(positionNameStr);
                        position = new Position(positionId, positionName);
                    }
                    Account account = new Account(id, email, username, fullname, department, position);
                    accounts.add(account);
                }
                if (accounts.isEmpty()) {
                    System.out.println("No accounts found with fullname " + val);
                } else {
                    for (Account account : accounts) {
                        System.out.println(account);
                    }
                }

            }

        }
    }

    public static void findByFullnameAndUsername(String val_ful, String val_user) throws SQLException, ClassNotFoundException {
        String query = "select * from `account` a\n" +
                "left join `department` d ON d.department_id = a.department_id\n" +
                "left join `position` p ON p.position_id = a.position_id\n" +
                "where lower(full_name) like lower(?) \n" +
                "and lower(username) like lower(?);";

        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, "%" + val_ful + "%");
            stmt.setString(2, "%" + val_user + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Account> accounts = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("account_id");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    String fullname = rs.getString("full_name");

                    Integer departmentId = (Integer) rs.getObject("department_id");
                    Integer positionId = (Integer) rs.getObject("position_id");

                    Position position = null;
                    Department department = null;

                    //Kiem tra chua co id thi khong tao pos va dept
                    if (departmentId != null) {
                        String departmentName = rs.getString("department_name");
                        department = new Department(departmentId, departmentName);
                    }
                    if (positionId != null) {
                        String positionNameStr = rs.getString("position_name");
                        PositionName positionName = PositionName.valueOf(positionNameStr);
                        position = new Position(positionId, positionName);
                    }
                    Account account = new Account(id, email, username, fullname, department, position);
                    accounts.add(account);
                }
                if (accounts.isEmpty()) {
                    System.out.println("No accounts found with fullname " + val_ful + " and username " + val_user);
                } else {
                    for (Account account : accounts) {
                        System.out.println(account);
                    }
                }

            }

        }
    }

    public static Connection connectDB() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/rw100_testing_system";
        String username = "root";
        String password = "admin321@";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connected to database successfully");
        return conn;
    }
}
