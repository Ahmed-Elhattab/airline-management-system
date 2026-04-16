package model;

import java.util.ArrayList;
import java.util.List;

public class Flight {
    private String flightNumber;
    private Airport origin;
    private Airport destination;
    private String departureDateTime;
    private String arrivalDateTime;
    private FlightStatus status;
    private Aircraft aircraft;
    private AirplanePilot pilot;
    private List<StaffCabin> cabinCrew;

    public Flight() {
        this.cabinCrew = new ArrayList<>();
    }

    public Flight(String flightNumber, Airport origin, Airport destination,
                  String departureDateTime, String arrivalDateTime, FlightStatus status) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.status = status;
        this.cabinCrew = new ArrayList<>();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public AirplanePilot getPilot() {
        return pilot;
    }

    public void setPilot(AirplanePilot pilot) {
        this.pilot = pilot;
    }

    public List<StaffCabin> getCabinCrew() {
        return cabinCrew;
    }

    public void setCabinCrew(List<StaffCabin> cabinCrew) {
        this.cabinCrew = cabinCrew;
    }

    public void planFlight() {
        this.status = FlightStatus.PLANNED;
    }

    public void cancelFlight() {
        this.status = FlightStatus.CANCELED;
    }

    public void modifyFlight(String newDeparture, String newArrival) {
        this.departureDateTime = newDeparture;
        this.arrivalDateTime = newArrival;
    }

    public void listingPassenger(List<Passenger> allPassengers) {
        System.out.println("Passengers for flight " + flightNumber + ":");

        boolean found = false;

        for (Passenger passenger : allPassengers) {
            for (Reservation reservation : passenger.getReservations()) {
                if (reservation.getFlights().contains(this)) {
                    System.out.println("Name: " + passenger.getName() +
                            ", Passport: " + passenger.getPassport());
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No passengers found for this flight.");
        }
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", departureDateTime='" + departureDateTime + '\'' +
                ", arrivalDateTime='" + arrivalDateTime + '\'' +
                ", status=" + status +
                ", aircraft=" + aircraft +
                ", pilot=" + pilot +
                ", cabinCrew=" + cabinCrew +
                '}';
    }
}