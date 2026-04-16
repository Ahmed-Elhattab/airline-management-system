package model;

import java.util.ArrayList;
import java.util.List;

public class Passenger extends Person {
    private String passport;
    private List<Reservation> reservations;

    public Passenger() {
        this.reservations = new ArrayList<>();
    }

    public Passenger(int id, String name, String address, String contact, String passport) {
        super(id, name, address, contact);
        this.passport = passport;
        this.reservations = new ArrayList<>();
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void bookFlight(Reservation reservation) {
        reservations.add(reservation);
    }

    public void cancelFlight(Reservation reservation) {
        reservations.remove(reservation);
    }

    public List<Reservation> getBooks() {
        return reservations;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}