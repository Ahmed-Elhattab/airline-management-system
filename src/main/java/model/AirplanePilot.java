package model;

public class AirplanePilot extends Employee {
    private String licence;
    private int flightHours;

    public AirplanePilot() {
    }

    public AirplanePilot(int id, String name, String address, String contact,
                         int numberEmp, String hiringDate,
                         String licence, int flightHours) {
        super(id, name, address, contact, numberEmp, hiringDate);
        this.licence = licence;
        this.flightHours = flightHours;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public int getFlightHours() {
        return flightHours;
    }

    public void setFlightHours(int flightHours) {
        this.flightHours = flightHours;
    }

    public void assignFlight(Flight flight) {
        flight.setPilot(this);
    }

    public void obtainVol() {
        System.out.println("Pilot flight hours: " + getFlightHours());
    }

    @Override
    public String getRole() {
        return "Pilot";
    }

    @Override
    public String toString() {
        return "AirplanePilot{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", numberEmp=" + getNumberEmp() +
                ", hiringDate='" + getHiringDate() + '\'' +
                ", licence='" + licence + '\'' +
                ", flightHours=" + flightHours +
                '}';
    }
}