package database;

import model.Flight;
import model.Reservation;
import model.ReservationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations related to Reservation entities.
 * Manages reservation records as well as their links to flights.
 */
public class ReservationDAO {

    private final FlightDAO flightDAO = new FlightDAO();

    /**
     * Inserts a reservation into the database and stores its associated flights.
     * Both operations are executed in a single transaction.
     *
     * @param reservation the reservation to add
     * @param passengerId the ID of the passenger linked to the reservation
     * @return true if the reservation was stored successfully
     */
    public boolean addReservation(Reservation reservation, int passengerId) {
        String reservationSql = "INSERT INTO reservations (reservation_number, date_reservation, status, passenger_id) VALUES (?, ?, ?, ?)";
        String reservationFlightsSql = "INSERT INTO reservation_flights (reservation_number, flight_number) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement reservationStatement = connection.prepareStatement(reservationSql);
                 PreparedStatement rfStatement = connection.prepareStatement(reservationFlightsSql)) {

                reservationStatement.setString(1, reservation.getReservationNumber());
                reservationStatement.setString(2, reservation.getDateReservation());
                reservationStatement.setString(3, reservation.getStatus().name());
                reservationStatement.setInt(4, passengerId);
                reservationStatement.executeUpdate();

                for (Flight flight : reservation.getFlights()) {
                    rfStatement.setString(1, reservation.getReservationNumber());
                    rfStatement.setString(2, flight.getFlightNumber());
                    rfStatement.addBatch();
                }

                rfStatement.executeBatch();
                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a reservation by its number and loads all linked flights.
     *
     * @param reservationNumber the reservation number
     * @return the matching Reservation, or null if not found
     */
    public Reservation getReservationByNumber(String reservationNumber) {
        String reservationSql = "SELECT * FROM reservations WHERE reservation_number = ?";
        String flightsSql = """
                SELECT flight_number
                FROM reservation_flights
                WHERE reservation_number = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement reservationStatement = connection.prepareStatement(reservationSql);
             PreparedStatement flightsStatement = connection.prepareStatement(flightsSql)) {

            reservationStatement.setString(1, reservationNumber);

            try (ResultSet rs = reservationStatement.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_number"),
                            rs.getString("date_reservation"),
                            ReservationStatus.valueOf(rs.getString("status"))
                    );

                    flightsStatement.setString(1, reservationNumber);

                    try (ResultSet flightRs = flightsStatement.executeQuery()) {
                        while (flightRs.next()) {
                            Flight flight = flightDAO.getFlightByNumber(flightRs.getString("flight_number"));
                            if (flight != null) {
                                reservation.getFlights().add(flight);
                            }
                        }
                    }

                    return reservation;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all reservations stored in the database.
     *
     * @return a list of all reservations
     */
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT reservation_number FROM reservations";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Reservation reservation = getReservationByNumber(rs.getString("reservation_number"));
                if (reservation != null) {
                    reservations.add(reservation);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    /**
     * Updates the status of an existing reservation.
     *
     * @param reservationNumber the reservation number
     * @param status the new reservation status
     * @return true if the update was successful
     */
    public boolean updateReservationStatus(String reservationNumber, ReservationStatus status) {
        String sql = "UPDATE reservations SET status = ? WHERE reservation_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status.name());
            statement.setString(2, reservationNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Replaces all flights associated with a reservation.
     * The update is performed in a transaction.
     *
     * @param reservationNumber the reservation number
     * @param flights the new list of flights
     * @return true if the replacement was successful
     */
    public boolean replaceReservationFlights(String reservationNumber, List<Flight> flights) {
        String deleteSql = "DELETE FROM reservation_flights WHERE reservation_number = ?";
        String insertSql = "INSERT INTO reservation_flights (reservation_number, flight_number) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                 PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

                deleteStatement.setString(1, reservationNumber);
                deleteStatement.executeUpdate();

                for (Flight flight : flights) {
                    insertStatement.setString(1, reservationNumber);
                    insertStatement.setString(2, flight.getFlightNumber());
                    insertStatement.addBatch();
                }

                insertStatement.executeBatch();
                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a reservation from the database.
     *
     * @param reservationNumber the reservation number
     * @return true if the deletion was successful
     */
    public boolean deleteReservation(String reservationNumber) {
        String sql = "DELETE FROM reservations WHERE reservation_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reservationNumber);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}