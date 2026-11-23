
package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;
import java.sql.Types;

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
    public List<Elevator> getElevators() throws SQLException {
    List<Elevator> elevators = new ArrayList<>();

    String sql = "SELECT id, capacity, current_floor, status "
               + "FROM public.elevators ORDER BY id";

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String id = rs.getString("id");
            int capacity = rs.getInt("capacity");
            Integer currentFloor = rs.getObject("current_floor", Integer.class);

            if (currentFloor == null) {
                throw new SQLException("Λείπει ο τρέχων όροφος του ανελκυστήρα " + id);
            }

            Elevator elevator = new Elevator(id, capacity);
            elevator.setCurrentFloor(currentFloor);
            elevator.setStatus(rs.getString("status"));
            elevators.add(elevator);
        }
    }

    return elevators;
}

    // Καταχωρίζει νέα κλήση και επιστρέφει το id που δημιουργεί η βάση.
    public long saveRideRequest(RideRequest ride) throws SQLException {
        String sql = "INSERT INTO public.ride_requests "
                   + "(employee_id, origin_floor, destination_floor, priority_level, meeting_id) "
                   + "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, ride.getEmployeeId(), Types.BIGINT);
            stmt.setInt(2, ride.getOriginalFloor());
            stmt.setInt(3, ride.getDestinationFloor());
            stmt.setInt(4, ride.getPriorityLevel());
            stmt.setObject(5, ride.getMeetingId(), Types.BIGINT);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Η βάση δεν επέστρεψε id για τη νέα κλήση.");
                }

                long id = rs.getLong("id");
                ride.setId(id);
                return id;
            }
        }
    }
        // Αναθέτει ανελκυστήρα σε εκκρεμή κλήση.
    public boolean assignElevator(long rideId, String elevatorId) throws SQLException {
        if (elevatorId == null || elevatorId.isBlank()) {
            throw new IllegalArgumentException("Elevator id is required");
        }

        String sql = "UPDATE public.ride_requests "
                   + "SET elevator_id = ?, status = ? "
                   + "WHERE id = ? AND status = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, elevatorId);
            stmt.setString(2, RideRequest.ASSIGNED);
            stmt.setLong(3, rideId);
            stmt.setString(4, RideRequest.PENDING);

            return stmt.executeUpdate() == 1;
        }
    }
}
