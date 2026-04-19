package service;

import model.Reservation;
import model.Passenger;
import model.Flight;
import model.ReservationStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing reservations in memory.
 * Handles booking logic, updates, cancellations, and flight associations.
 */
public class ReservationService {

    private List<Reservation> reservations;

    /**
     * Initializes the reservation list.
     */
    public ReservationService() {
        reservations = new ArrayList<>();
    }

    /**
     * Adds a reservation to the system.
     *
     * @param reservation the reservation to add
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Retrieves a reservation by its number.
     *
     * @param reservationNumber the reservation identifier
     * @return the matching Reservation, or null if not found
     */
    public Reservation getReservationByNumber(String reservationNumber) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationNumber().equals(reservationNumber)) {
                return reservation;
            }
        }
        return null;
    }

    /**
     * Updates an existing reservation.
     *
     * @param reservationNumber the reservation identifier
     * @param updatedReservation the new reservation data
     * @return true if the update was successful
     */
    public boolean updateReservation(String reservationNumber, Reservation updatedReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationNumber().equals(reservationNumber)) {
                reservations.set(i, updatedReservation);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a reservation by its number.
     *
     * @param reservationNumber the reservation identifier
     * @return true if deletion was successful
     */
    public boolean deleteReservation(String reservationNumber) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationNumber().equals(reservationNumber)) {
                reservations.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all reservations.
     *
     * @return list of reservations
     */
    public List<Reservation> getAllReservations() {
        return reservations;
    }

    /**
     * Books a flight for a passenger by creating a new reservation.
     * The reservation is automatically confirmed and linked to the passenger.
     *
     * @param passenger the passenger making the booking
     * @param flight the flight to reserve
     * @param reservationNumber unique reservation identifier
     * @param dateReservation reservation date
     * @return the created Reservation, or null if booking fails
     */
    public Reservation bookFlight(Passenger passenger, Flight flight, String reservationNumber, String dateReservation) {
        if (passenger == null || flight == null) {
            return null;
        }

        if (getReservationByNumber(reservationNumber) != null) {
            return null;
        }

        Reservation reservation = new Reservation(reservationNumber, dateReservation, ReservationStatus.PENDING);

        reservation.getFlights().add(flight);
        reservation.confirmReservation();

        passenger.bookFlight(reservation);

        reservations.add(reservation);

        return reservation;
    }

    /**
     * Cancels a reservation by updating its status.
     *
     * @param reservationNumber the reservation identifier
     * @return true if cancellation was successful
     */
    public boolean cancelReservationByNumber(String reservationNumber) {
        Reservation reservation = getReservationByNumber(reservationNumber);

        if (reservation != null) {
            reservation.cancelReservation();
            return true;
        }

        return false;
    }

    /**
     * Replaces the flights associated with a reservation.
     *
     * @param reservationNumber the reservation identifier
     * @param newFlights new list of flights
     * @return true if modification was successful
     */
    public boolean modifyReservationFlights(String reservationNumber, List<Flight> newFlights) {
        Reservation reservation = getReservationByNumber(reservationNumber);

        if (reservation != null) {
            reservation.modifyReservation(newFlights);
            return true;
        }

        return false;
    }
}