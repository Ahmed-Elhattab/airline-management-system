package model;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private String reservationNumber;
    private String dateReservation;
    private ReservationStatus status;
    private List<Flight> flights;

    public Reservation() {
        this.flights = new ArrayList<>();
    }

    public Reservation(String reservationNumber, String dateReservation, ReservationStatus status) {
        this.reservationNumber = reservationNumber;
        this.dateReservation = dateReservation;
        this.status = status;
        this.flights = new ArrayList<>();
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void confirmReservation() {
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCELED;
    }

    public void modifyReservation(List<Flight> newFlights) {
        this.flights = newFlights;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationNumber='" + reservationNumber + '\'' +
                ", dateReservation='" + dateReservation + '\'' +
                ", status=" + status +
                '}';
    }
}