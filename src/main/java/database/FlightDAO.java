package database;

import model.Aircraft;
import model.Airport;
import model.Flight;
import model.FlightStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations related to Flight entities.
 * Manages flight persistence and reconstructs related airport and aircraft data.
 */
public class FlightDAO {

    private final AirportDAO airportDAO = new AirportDAO();
    private final AircraftDAO aircraftDAO = new AircraftDAO();

    /**
     * Inserts a new flight into the database.
     * Ensures origin and destination airports exist before insertion.
     *
     * @param flight the flight to add
     * @return true if the insertion was successful
     */
    public boolean addFlight(Flight flight) {
        int originId = airportDAO.addAirport(flight.getOrigin());
        int destinationId = airportDAO.addAirport(flight.getDestination());

        String sql = "INSERT INTO flights (flight_number, origin_airport_id, destination_airport_id, departure_time, arrival_time, status, aircraft_registration) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, flight.getFlightNumber());
            statement.setInt(2, originId);
            statement.setInt(3, destinationId);
            statement.setString(4, flight.getDepartureDateTime());
            statement.setString(5, flight.getArrivalDateTime());
            statement.setString(6, flight.getStatus().name());

            if (flight.getAircraft() != null) {
                statement.setString(7, flight.getAircraft().getRegistration());
            } else {
                statement.setNull(7, Types.VARCHAR);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a flight by its flight number.
     * Also rebuilds the associated origin, destination, and aircraft objects.
     *
     * @param flightNumber the flight number
     * @return the matching Flight, or null if not found
     */
    public Flight getFlightByNumber(String flightNumber) {
        String sql = """
                SELECT f.*, 
                       ao.name AS origin_name, ao.city AS origin_city, ao.description AS origin_description,
                       ad.name AS destination_name, ad.city AS destination_city, ad.description AS destination_description
                FROM flights f
                JOIN airports ao ON f.origin_airport_id = ao.id
                JOIN airports ad ON f.destination_airport_id = ad.id
                WHERE f.flight_number = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, flightNumber);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Airport origin = new Airport(
                            rs.getString("origin_name"),
                            rs.getString("origin_city"),
                            rs.getString("origin_description")
                    );

                    Airport destination = new Airport(
                            rs.getString("destination_name"),
                            rs.getString("destination_city"),
                            rs.getString("destination_description")
                    );

                    Flight flight = new Flight(
                            rs.getString("flight_number"),
                            origin,
                            destination,
                            rs.getString("departure_time"),
                            rs.getString("arrival_time"),
                            FlightStatus.valueOf(rs.getString("status"))
                    );

                    String aircraftRegistration = rs.getString("aircraft_registration");
                    if (aircraftRegistration != null) {
                        Aircraft aircraft = aircraftDAO.getAircraftByRegistration(aircraftRegistration);
                        flight.setAircraft(aircraft);
                    }

                    return flight;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all flights stored in the database.
     *
     * @return a list of all flights
     */
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT flight_number FROM flights";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Flight flight = getFlightByNumber(rs.getString("flight_number"));
                if (flight != null) {
                    flights.add(flight);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    /**
     * Updates an existing flight in the database.
     *
     * @param flight the flight containing updated values
     * @return true if the update was successful
     */
    public boolean updateFlight(Flight flight) {
        int originId = airportDAO.addAirport(flight.getOrigin());
        int destinationId = airportDAO.addAirport(flight.getDestination());

        String sql = "UPDATE flights SET origin_airport_id = ?, destination_airport_id = ?, departure_time = ?, arrival_time = ?, status = ?, aircraft_registration = ? WHERE flight_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, originId);
            statement.setInt(2, destinationId);
            statement.setString(3, flight.getDepartureDateTime());
            statement.setString(4, flight.getArrivalDateTime());
            statement.setString(5, flight.getStatus().name());

            if (flight.getAircraft() != null) {
                statement.setString(6, flight.getAircraft().getRegistration());
            } else {
                statement.setNull(6, Types.VARCHAR);
            }

            statement.setString(7, flight.getFlightNumber());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a flight from the database.
     *
     * @param flightNumber the flight number
     * @return true if the deletion was successful
     */
    public boolean deleteFlight(String flightNumber) {
        String sql = "DELETE FROM flights WHERE flight_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, flightNumber);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}