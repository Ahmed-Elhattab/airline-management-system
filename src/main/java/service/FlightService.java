package service;

import model.AirplanePilot;
import model.Airport;
import model.Flight;
import model.StaffCabin;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing flights in memory.
 * Provides CRUD operations and flight-related actions such as planning,
 * cancellation, modification, and crew assignment.
 */
public class FlightService {

    private List<Flight> flights;

    /**
     * Initializes the flight list.
     */
    public FlightService() {
        flights = new ArrayList<>();
    }

    /**
     * Adds a new flight to the system.
     *
     * @param flight the flight to add
     */
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    /**
     * Retrieves a flight by its number.
     *
     * @param flightNumber the flight identifier
     * @return the matching Flight, or null if not found
     */
    public Flight getFlightByNumber(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    /**
     * Updates an existing flight.
     *
     * @param flightNumber the flight to update
     * @param updatedFlight the new flight data
     * @return true if the update was successful
     */
    public boolean updateFlight(String flightNumber, Flight updatedFlight) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber().equals(flightNumber)) {
                flights.set(i, updatedFlight);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a flight from the system.
     *
     * @param flightNumber the flight identifier
     * @return true if deletion was successful
     */
    public boolean deleteFlight(String flightNumber) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber().equals(flightNumber)) {
                flights.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all stored flights.
     *
     * @return list of flights
     */
    public List<Flight> getAllFlights() {
        return flights;
    }

    /**
     * Marks a flight as planned.
     *
     * @param flightNumber the flight identifier
     * @return true if operation was successful
     */
    public boolean planFlight(String flightNumber) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null) {
            flight.planFlight();
            return true;
        }

        return false;
    }

    /**
     * Cancels a flight.
     *
     * @param flightNumber the flight identifier
     * @return true if operation was successful
     */
    public boolean cancelFlight(String flightNumber) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null) {
            flight.cancelFlight();
            return true;
        }

        return false;
    }

    /**
     * Modifies flight details such as route and schedule.
     *
     * @param flightNumber the flight identifier
     * @param newOrigin new origin airport
     * @param newDestination new destination airport
     * @param newDeparture new departure time
     * @param newArrival new arrival time
     * @return true if modification was successful
     */
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

    /**
     * Assigns a pilot to a flight.
     *
     * @param flightNumber the flight identifier
     * @param pilot the pilot to assign
     * @return true if assignment was successful
     */
    public boolean assignPilotToFlight(String flightNumber, AirplanePilot pilot) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null && pilot != null) {
            pilot.assignFlight(flight);
            return true;
        }

        return false;
    }

    /**
     * Assigns a cabin crew member to a flight.
     *
     * @param flightNumber the flight identifier
     * @param crewMember the crew member to assign
     * @return true if assignment was successful
     */
    public boolean assignCabinCrewToFlight(String flightNumber, StaffCabin crewMember) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight != null && crewMember != null) {
            crewMember.assignFlight(flight);
            return true;
        }

        return false;
    }
}