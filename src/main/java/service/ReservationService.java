package service;

import model.Reservation;
import model.Passenger;
import model.Flight;
import model.ReservationStatus;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private List<Reservation> reservations;

    public ReservationService() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public Reservation getReservationByNumber(String reservationNumber) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationNumber().equals(reservationNumber)) {
                return reservation;
            }
        }
        return null;
    }

    public boolean updateReservation(String reservationNumber, Reservation updatedReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationNumber().equals(reservationNumber)) {
                reservations.set(i, updatedReservation);
                return true;
            }
        }
        return false;
    }

    public boolean deleteReservation(String reservationNumber) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationNumber().equals(reservationNumber)) {
                reservations.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

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

    public boolean cancelReservationByNumber(String reservationNumber) {
        Reservation reservation = getReservationByNumber(reservationNumber);

        if (reservation != null) {
            reservation.cancelReservation();
            return true;
        }

        return false;
    }

    public boolean modifyReservationFlights(String reservationNumber, List<Flight> newFlights) {
        Reservation reservation = getReservationByNumber(reservationNumber);

        if (reservation != null) {
            reservation.modifyReservation(newFlights);
            return true;
        }

        return false;
    }
}