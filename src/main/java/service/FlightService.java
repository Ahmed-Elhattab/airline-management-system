package service;

import model.AirplanePilot;
import model.Airport;
import model.Flight;
import model.StaffCabin;

import java.util.ArrayList;
import java.util.List;

public class FlightService {

    private List<Flight> flights;

    public FlightService() {
        flights = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public Flight getFlightByNumber(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    public boolean updateFlight(String flightNumber, Flight updatedFlight) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber().equals(flightNumber)) {
                flights.set(i, updatedFlight);
                return true;
            }
        }
        return false;
    }

    public boolean deleteFlight(String flightNumber) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber().equals(flightNumber)) {
                flights.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Flight> getAllFlights() {
        return flights;
    }

    public boolean planFlight(String flightNumber) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null) {
            flight.planFlight();
            return true;
        }

        return false;
    }

    public boolean cancelFlight(String flightNumber) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null) {
            flight.cancelFlight();
            return true;
        }

        return false;
    }

    public boolean modifyFlight(String flightNumber, Airport newOrigin, Airport newDestination,
                                String newDeparture, String newArrival) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null) {
            flight.setOrigin(newOrigin);
            flight.setDestination(newDestination);
            flight.modifyFlight(newDeparture, newArrival);
            return true;
        }

        return false;
    }

    public boolean assignPilotToFlight(String flightNumber, AirplanePilot pilot) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null && pilot != null) {
            pilot.assignFlight(flight);
            return true;
        }

        return false;
    }

    public boolean assignCabinCrewToFlight(String flightNumber, StaffCabin crewMember) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null && crewMember != null) {
            crewMember.assignFlight(flight);
            return true;
        }

        return false;
    }
}