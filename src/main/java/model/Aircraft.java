package model;

public class Aircraft {
    private String registration;
    private String model;
    private int capacity;

    public Aircraft() {
    }

    public Aircraft(String registration, String model, int capacity) {
        this.registration = registration;
        this.model = model;
        this.capacity = capacity;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void assignFlight(Flight flight) {
        flight.setAircraft(this);
    }

    public boolean checkAvailability() {
        return true;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "registration='" + registration + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}