package gr.aueb.para8.db;

import java.sql.PreparedStatement;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DatabaseManager {

    private Connection connection;

    public void connectToDatabase() {
        Properties settings = new Properties();
        try (InputStream input = DatabaseManager.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new IllegalStateException("db.properties is missing");
            }
            settings.load(input);
            connection = DriverManager.getConnection(
                settings.getProperty("url"),
                settings.getProperty("user"),
                settings.getProperty("password"));
            } catch (IOException | SQLException exception) {
            throw new IllegalStateException("could not connect to the database " + exception.getMessage());
        }
    }

    public void initializeMockData() {
        if (connection == null) {
            throw new IllegalStateException("connect to the database first");
        }
        String insertFloor = 
            "INSERT INTO floors (id, name, is_restricted) VALUES (?, ?, ?) ON CONFLICT (id) DO NOTHING";
        String insertElevator =
            "INSERT INTO elevators (id, capacity, current_floor, status) VALUES (?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";
        try (
            PreparedStatement floors = connection.prepareStatement(insertFloor);
            PreparedStatement elevators = connection.prepareStatement(insertElevator);
        ) {
            for (int floor = 0; floor <= 9; floor ++) {
                floors.setInt(1, floor);
                floors.setString(2, floor == 0 ? "Ισόγειο" : "Όροφος " + floor);
                floors.setBoolean(3, floor == 9);
                floors.executeUpdate();
            }
            for (String id : new String[] {"A", "B", "C"}) {
                elevators.setString(1, id);
                elevators.setInt(2, 8);
                elevators.setInt(3, 0);
                elevators.setString(4, "IDLE");
                elevators.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("could not insert mock data", exception);
        }
        

    }

    public void closeConnection() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException exception) {
            throw new IllegalStateException("could not close the  database", exception);
        }
    }
}    
