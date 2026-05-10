package buoi7.src.backend;

import buoi7.src.entity.Position;
import buoi7.src.enums.PositionName;

import java.sql.*;
import java.util.ArrayList;

public class QLPosition {
    public static void showAllPosition() throws SQLException, ClassNotFoundException {
        String query = "select * from position";

        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                ArrayList<Position> positions = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("position_id");
                    PositionName name = PositionName.valueOf(rs.getString("position_name"));
                    Position position = new Position(id, name);
                    positions.add(position);
                }
                if (positions.isEmpty()) {
                    System.out.println("No positions found");
                } else {
                    for (Position position : positions) {
                        System.out.println(position.toString());
                    }
                }

            }

        }

    }

    public static void findByPositionName(String val) throws SQLException, ClassNotFoundException {
        String query = "select * from position where position_name like upper(?)";

        try (Connection conn = connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, "%" + val + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Position> positions = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("position_id");
                    PositionName name = PositionName.valueOf(rs.getString("position_name"));
                    Position position = new Position(id, name);
                    positions.add(position);
                }
                if (positions.isEmpty()) {
                    System.out.println("No positions found with name " + val);
                } else {
                    for (Position position : positions) {
                        System.out.println(position.toString());
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
