package model;

public class StaffCabin extends Employee {
    private String qualification;

    public StaffCabin() {
    }

    public StaffCabin(int id, String name, String address, String contact,
                      int numberEmp, String hiringDate,
                      String qualification) {
        super(id, name, address, contact, numberEmp, hiringDate);
        this.qualification = qualification;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void assignFlight(Flight flight) {
        flight.getCabinCrew().add(this);
    }

    public void obtainVol() {
        System.out.println("Cabin crew qualification: " + qualification);
    }

    @Override
    public String getRole() {
        return "Cabin Crew";
    }

    @Override
    public String toString() {
        return "StaffCabin{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", numberEmp=" + getNumberEmp() +
                ", hiringDate='" + getHiringDate() + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }
}