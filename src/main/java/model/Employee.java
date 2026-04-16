package model;

public class Employee extends Person {
    private int numberEmp;
    private String hiringDate;

    public Employee() {
    }

    public Employee(int id, String name, String address, String contact, int numberEmp, String hiringDate) {
        super(id, name, address, contact);
        this.numberEmp = numberEmp;
        this.hiringDate = hiringDate;
    }

    public int getNumberEmp() {
        return numberEmp;
    }

    public void setNumberEmp(int numberEmp) {
        this.numberEmp = numberEmp;
    }

    public String getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getRole() {
        return "Employee";
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", numberEmp=" + numberEmp +
                ", hiringDate='" + hiringDate + '\'' +
                '}';
    }
}