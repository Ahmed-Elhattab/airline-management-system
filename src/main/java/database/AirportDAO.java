package database;

import model.Airport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations related to Airport entities.
 * Ensures airports are uniquely stored based on name and city.
 */
public class AirportDAO {

    /**
     * Inserts a new airport into the database.
     * If the airport already exists, returns the existing ID instead.
     *
     * @param airport the airport to add
     * @return the generated or existing airport ID, or -1 if failed
     */
    public int addAirport(Airport airport) {
        String sql = "INSERT INTO airports (name, city, description) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, airport.getName());
            statement.setString(2, airport.getCity());
            statement.setString(3, airport.getDescription());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            // if duplicate, return existing id
            Integer existingId = getAirportIdByNameAndCity(airport.getName(), airport.getCity());
            if (existingId != null) {
                return existingId;
            }
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Retrieves the ID of an airport based on its name and city.
     *
     * @param name the airport name
     * @param city the airport city
     * @return the airport ID if found, otherwise null
     */
    public Integer getAirportIdByNameAndCity(String name, String city) {
        String sql = "SELECT id FROM airports WHERE name = ? AND city = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, city);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves an airport by its ID.
     *
     * @param id the airport ID
     * @return the corresponding Airport object, or null if not found
     */
    public Airport getAirportById(int id) {
        String sql = "SELECT * FROM airports WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Airport(
                            rs.getString("name"),
                            rs.getString("city"),
                            rs.getString("description")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all airports from the database.
     *
     * @return a list of all airports
     */
    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM airports";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                airports.add(new Airport(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return airports;
    }
}