package db;

import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DatabaseRepositoryTest {

    private DatabaseRepository repository;
    private PreparedStatement statement;
    private ResultSet rows;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        rows = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        repository = new DatabaseRepository(connection);
    }

    @Test
    void getElevatorsReadsEachElevatorWithItsFloorAndStatus() throws SQLException {
        when(statement.executeQuery()).thenReturn(rows);
        when(rows.next()).thenReturn(true, true, false);
        when(rows.getString("id")).thenReturn("A", "B");
        when(rows.getInt("capacity")).thenReturn(8, 10);
        when(rows.getObject("current_floor", Integer.class)).thenReturn(0, 4);
        when(rows.getString("status")).thenReturn("IDLE", "MOVING");

        List<Elevator> elevators = repository.getElevators();

        assertEquals(2, elevators.size());

        Elevator first = elevators.get(0);
        assertEquals("A", first.getId());
        assertEquals(0, first.getCurrentFloor());
        assertEquals("IDLE", first.getStatus());
        assertEquals(8, first.getCapacity());

        Elevator second = elevators.get(1);
        assertEquals("B", second.getId());
        assertEquals(4, second.getCurrentFloor());
        assertEquals("MOVING", second.getStatus());
        assertEquals(10, second.getCapacity());
    }

    @Test
    void saveRideRequestBindsRideFieldsAndReturnsGeneratedId() throws SQLException {
        RideRequest ride = new RideRequest(12L, 2, 7, RideRequest.NORMAL);
        when(statement.executeQuery()).thenReturn(rows);
        when(rows.next()).thenReturn(true);
        when(rows.getLong("id")).thenReturn(42L);

        long savedId = repository.saveRideRequest(ride);

        verify(statement).setObject(1, 12L, Types.BIGINT);
        verify(statement).setInt(2, 2);
        verify(statement).setInt(3, 7);
        verify(statement).setInt(4, RideRequest.NORMAL);
        verify(statement).setObject(5, null, Types.BIGINT);
        assertEquals(42L, savedId);
        assertEquals(42L, ride.getId());
    }

    @Test
    void assignElevatorReportsWhetherAPendingRideWasUpdated() throws SQLException {
        when(statement.executeUpdate()).thenReturn(1, 0);

        assertTrue(repository.assignElevator(42L, "B"));

        verify(statement).setString(1, "B");
        verify(statement).setString(2, RideRequest.ASSIGNED);
        verify(statement).setLong(3, 42L);
        verify(statement).setString(4, RideRequest.PENDING);

        assertFalse(repository.assignElevator(42L, "B"));
    }
}