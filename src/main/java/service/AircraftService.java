package service;

import model.Aircraft;
import model.Flight;

import java.util.ArrayList;
import java.util.List;

public class AircraftService {

    private List<Aircraft> aircrafts;

    public AircraftService() {
        aircrafts = new ArrayList<>();
    }

    public void addAircraft(Aircraft aircraft) {
        aircrafts.add(aircraft);
    }

    public Aircraft getAircraftByRegistration(String registration) {
        for (Aircraft aircraft : aircrafts) {
            if (aircraft.getRegistration().equals(registration)) {
                return aircraft;
            }
        }
        return null;
    }

    public boolean updateAircraft(String registration, Aircraft updatedAircraft) {
        for (int i = 0; i < aircrafts.size(); i++) {
            if (aircrafts.get(i).getRegistration().equals(registration)) {
                aircrafts.set(i, updatedAircraft);
                return true;
            }
        }
        return false;
    }

    public boolean deleteAircraft(String registration) {
        for (int i = 0; i < aircrafts.size(); i++) {
            if (aircrafts.get(i).getRegistration().equals(registration)) {
                aircrafts.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Aircraft> getAllAircrafts() {
        return aircrafts;
    }

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