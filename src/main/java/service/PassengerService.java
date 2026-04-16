package service;

import model.Passenger;
import java.util.ArrayList;
import java.util.List;

public class PassengerService {

    private List<Passenger> passengers;

    public PassengerService() {
        passengers = new ArrayList<>();
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public Passenger getPassengerById(int id) {
        for (Passenger passenger : passengers) {
            if (passenger.getId() == id) {
                return passenger;
            }
        }
        return null;
    }

    public boolean updatePassenger(int id, Passenger updatedPassenger) {
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getId() == id) {
                passengers.set(i, updatedPassenger);
                return true;
            }
        }
        return false;
    }

    public boolean deletePassenger(int id) {
        for (Passenger passenger : passengers) {
            if (passenger.getId() == id) {
                passengers.remove(passenger);
                return true;
            }
        }
        return false;
    }

    public List<Passenger> getAllPassengers() {
        return passengers;
    }
}