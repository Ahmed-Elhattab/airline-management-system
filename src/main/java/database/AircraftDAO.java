package database;

import model.Aircraft;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations related to Aircraft entities.
 * Provides basic CRUD operations using JDBC.
 */
public class AircraftDAO {

    /**
     * Inserts a new aircraft into the database.
     *
     * @param aircraft the aircraft to add
     * @return true if the insertion was successful
     */
    public boolean addAircraft(Aircraft aircraft) {
        String sql = "INSERT INTO aircrafts (registration, model, capacity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, aircraft.getRegistration());
            statement.setString(2, aircraft.getModel());
            statement.setInt(3, aircraft.getCapacity());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves an aircraft by its registration number.
     *
     * @param registration the aircraft registration
     * @return the matching Aircraft, or null if not found
     */
    public Aircraft getAircraftByRegistration(String registration) {
        String sql = "SELECT * FROM aircrafts WHERE registration = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, registration);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Aircraft(
                            rs.getString("registration"),
                            rs.getString("model"),
                            rs.getInt("capacity")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all aircrafts stored in the database.
     *
     * @return a list of all aircrafts
     */
    public List<Aircraft> getAllAircrafts() {
        List<Aircraft> aircrafts = new ArrayList<>();
        String sql = "SELECT * FROM aircrafts";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                aircrafts.add(new Aircraft(
                        rs.getString("registration"),
                        rs.getString("model"),
                        rs.getInt("capacity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aircrafts;
    }

    /**
     * Updates an existing aircraft in the database.
     *
     * @param aircraft the aircraft with updated values
     * @return true if the update was successful
     */
    public boolean updateAircraft(Aircraft aircraft) {
        String sql = "UPDATE aircrafts SET model = ?, capacity = ? WHERE registration = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, aircraft.getModel());
            statement.setInt(2, aircraft.getCapacity());
            statement.setString(3, aircraft.getRegistration());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an aircraft from the database.
     *
     * @param registration the aircraft registration
     * @return true if the deletion was successful
     */
    public boolean deleteAircraft(String registration) {
        String sql = "DELETE FROM aircrafts WHERE registration = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, registration);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}