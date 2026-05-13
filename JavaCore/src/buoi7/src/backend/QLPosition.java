package buoi7.src.backend;

import buoi7.src.entity.Position;
import buoi7.src.enums.PositionName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static buoi7.utils.JDBCUtils.getConnection;

public class QLPosition {
    public static List<Position> findAllPosition() {
        String query = "select * from position";
        List<Position> positions = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("position_id");
                    PositionName name = PositionName.valueOf(rs.getString("position_name"));
                    Position position = new Position(id, name);
                    positions.add(position);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    public static List<Position> findByPositionName(String val) {
        List<Position> positions = new ArrayList<>();
        String query = "select * from position where position_name like upper(?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, "%" + val + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("position_id");
                    PositionName name = PositionName.valueOf(rs.getString("position_name"));
                    Position position = new Position(id, name);
                    positions.add(position);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    public static boolean createPosition(String name) {
        String insert = "insert into `position` (position_name) values (?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, name.trim());
            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePosition(String name, int id) {
        String update = "UPDATE `position`\n" +
                "SET position_name = ?\n" +
                "WHERE position_id = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(update);
        ) {
            stmt.setString(1, name.trim());
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deletePosition(String name) {
        String delelte = "delete from `position` where position_name = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(delelte);
        ) {
            stmt.setString(1, name.trim());
            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showPosition(List<Position> positions, String val) {
        if (positions.isEmpty()) {
            if (val == null) {
                System.out.println("No positions found");
            } else {
                System.out.println("No positions found with name " + val);
            }
            return;
        }

        System.out.println("+-----+--------------------+");
        System.out.printf("|%5s|%20s|\n", "ID", "NAME");
        System.out.println("+-----+--------------------+");

        for (Position position : positions) {
            System.out.printf("|%5s|%20s|\n",
                    position.getPositionID(),
                    position.getPositionName());
        }

        System.out.println("+-----+--------------------+");
    }
}
