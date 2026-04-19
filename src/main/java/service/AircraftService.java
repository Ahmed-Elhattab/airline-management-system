package service;

import model.Aircraft;
import model.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing aircraft in memory.
 * Provides CRUD operations and basic availability checks.
 */
public class AircraftService {

    private List<Aircraft> aircrafts;

    /**
     * Initializes the aircraft list.
     */
    public AircraftService() {
        aircrafts = new ArrayList<>();
    }

    /**
     * Adds a new aircraft to the system.
     *
     * @param aircraft the aircraft to add
     */
    public void addAircraft(Aircraft aircraft) {
        aircrafts.add(aircraft);
    }

    /**
     * Retrieves an aircraft by its registration number.
     *
     * @param registration the aircraft registration
     * @return the matching Aircraft, or null if not found
     */
    public Aircraft getAircraftByRegistration(String registration) {
        for (Aircraft aircraft : aircrafts) {
            if (aircraft.getRegistration().equals(registration)) {
                return aircraft;
            }
        }
        return null;
    }

    /**
     * Updates an existing aircraft.
     *
     * @param registration the registration of the aircraft to update
     * @param updatedAircraft the new aircraft data
     * @return true if the update was successful
     */
    public boolean updateAircraft(String registration, Aircraft updatedAircraft) {
        for (int i = 0; i < aircrafts.size(); i++) {
            if (aircrafts.get(i).getRegistration().equals(registration)) {
                aircrafts.set(i, updatedAircraft);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an aircraft from the system.
     *
     * @param registration the aircraft registration
     * @return true if the deletion was successful
     */
    public boolean deleteAircraft(String registration) {
        for (int i = 0; i < aircrafts.size(); i++) {
            if (aircrafts.get(i).getRegistration().equals(registration)) {
                aircrafts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all aircraft currently stored in memory.
     *
     * @return list of aircraft
     */
    public List<Aircraft> getAllAircrafts() {
        return aircrafts;
    }

    /**
     * Checks whether an aircraft is available for a given flight.
     * An aircraft is considered unavailable if it is already assigned
     * to another flight with the same departure or arrival time.
     *
     * @param aircraft the aircraft to check
     * @param flight the target flight
     * @param allFlights list of all existing flights
     * @return true if the aircraft is available, false otherwise
     */
    public boolean checkAvailability(Aircraft aircraft, Flight flight, List<Flight> allFlights) {
        for (Flight existingFlight : allFlights) {
            if (existingFlight.getAircraft() != null &&
                    existingFlight.getAircraft().getRegistration().equals(aircraft.getRegistration())) {

                if (existingFlight.getDepartureDateTime().equals(flight.getDepartureDateTime()) ||
                        existingFlight.getArrivalDateTime().equals(flight.getArrivalDateTime())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Assigns an aircraft to a flight if it is available.
     *
     * @param aircraft the aircraft to assign
     * @param flight the flight to assign to
     * @param allFlights list of all existing flights
     * @return true if assignment was successful
     */
    public boolean assignAircraftToFlight(Aircraft aircraft, Flight flight, List<Flight> allFlights) {
        if (aircraft == null || flight == null) {
            return false;
        }

        if (checkAvailability(aircraft, flight, allFlights)) {
            aircraft.assignFlight(flight);
            return true;
        }

        return false;
    }
}