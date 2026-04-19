package database;

import model.Passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations related to Passenger entities.
 * Provides CRUD operations and queries linked to reservations and flights.
 */
public class PassengerDAO {

    /**
     * Inserts a new passenger into the database.
     *
     * @param passenger the passenger to add
     * @return true if the insertion was successful
     */
    public boolean addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (id, name, address, contact, passport) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, passenger.getId());
            statement.setString(2, passenger.getName());
            statement.setString(3, passenger.getAddress());
            statement.setString(4, passenger.getContact());
            statement.setString(5, passenger.getPassport());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a passenger by their ID.
     *
     * @param id the passenger ID
     * @return the matching Passenger, or null if not found
     */
    public Passenger getPassengerById(int id) {
        String sql = "SELECT * FROM passengers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Passenger(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("contact"),
                            rs.getString("passport")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all passengers from the database.
     *
     * @return a list of all passengers
     */
    public List<Passenger> getAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                passengers.add(new Passenger(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact"),
                        rs.getString("passport")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengers;
    }

    /**
     * Updates an existing passenger in the database.
     *
     * @param passenger the passenger with updated information
     * @return true if the update was successful
     */
    public boolean updatePassenger(Passenger passenger) {
        String sql = "UPDATE passengers SET name = ?, address = ?, contact = ?, passport = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, passenger.getName());
            statement.setString(2, passenger.getAddress());
            statement.setString(3, passenger.getContact());
            statement.setString(4, passenger.getPassport());
            statement.setInt(5, passenger.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a passenger from the database.
     *
     * @param id the passenger ID
     * @return true if the deletion was successful
     */
    public boolean deletePassenger(int id) {
        String sql = "DELETE FROM passengers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all passengers associated with a given flight.
     * Uses joins between passengers, reservations, and reservation_flights.
     *
     * @param flightNumber the flight number
     * @return a list of passengers linked to the flight
     */
    public List<Passenger> getPassengersByFlightNumber(String flightNumber) {
        List<Passenger> passengers = new ArrayList<>();

        String sql = """
                SELECT DISTINCT p.*
                FROM passengers p
                JOIN reservations r ON p.id = r.passenger_id
                JOIN reservation_flights rf ON r.reservation_number = rf.reservation_number
                WHERE rf.flight_number = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, flightNumber);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    passengers.add(new Passenger(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("contact"),
                            rs.getString("passport")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengers;
    }
}