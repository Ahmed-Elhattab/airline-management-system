package service;

import model.Passenger;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing passengers in memory.
 * Provides basic CRUD operations on the passenger list.
 */
public class PassengerService {

    private List<Passenger> passengers;

    /**
     * Initializes the passenger list.
     */
    public PassengerService() {
        passengers = new ArrayList<>();
    }

    /**
     * Adds a new passenger.
     *
     * @param passenger the passenger to add
     */
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    /**
     * Retrieves a passenger by their ID.
     *
     * @param id the passenger identifier
     * @return the matching Passenger, or null if not found
     */
    public Passenger getPassengerById(int id) {
        for (Passenger passenger : passengers) {
            if (passenger.getId() == id) {
                return passenger;
            }
        }
        return null;
    }

    /**
     * Updates an existing passenger.
     *
     * @param id the ID of the passenger to update
     * @param updatedPassenger the new passenger data
     * @return true if the update was successful
     */
    public boolean updatePassenger(int id, Passenger updatedPassenger) {
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getId() == id) {
                passengers.set(i, updatedPassenger);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a passenger by ID.
     *
     * @param id the passenger identifier
     * @return true if deletion was successful
     */
    public boolean deletePassenger(int id) {
        for (Passenger passenger : passengers) {
            if (passenger.getId() == id) {
                passengers.remove(passenger);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all passengers.
     *
     * @return list of passengers
     */
    public List<Passenger> getAllPassengers() {
        return passengers;
    }
}