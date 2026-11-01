
package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class DatabaseRepository {

    private Connection connection;

    public DatabaseRepository(Connection connection) {
        this.connection = connection;
    }

    // Μετρά όλες τις συναντήσεις
    public int countMeetings() throws SQLException {

        String sql = "SELECT COUNT(*) FROM meetings";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    // Επιστρέφει τα IDs των συναντήσεων
    public List<Integer> getMeetingIds() throws SQLException {

        List<Integer> ids = new ArrayList<>();

        String sql = "SELECT id FROM meetings ORDER BY id ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }

        return ids;
    }

    // Επιστρέφει ενεργούς κωδικούς που δεν έχουν λήξει
    public List<Integer> getActiveAccessCodeIds() throws SQLException {

        List<Integer> ids = new ArrayList<>();

        String sql = "SELECT id FROM access_codes "
                   + "WHERE is_active = TRUE "
                   + "AND expires_at > CURRENT_TIMESTAMP";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }

        return ids;
    }

    // Επιστρέφει τα IDs των καταγεγραμμένων συμβάντων
    public List<Integer> getAuditLogIds() throws SQLException {

        List<Integer> ids = new ArrayList<>();

        String sql = "SELECT id FROM audit_logs ORDER BY id ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }

        return ids;
    }
}
