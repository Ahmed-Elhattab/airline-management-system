# ✈️ Airline Management System (Java + MySQL)

## 1. Introduction

This project is a console-based Airline Management System developed in Java.  
It allows managing passengers, flights, aircraft, and reservations using a structured object-oriented design.

The application was initially built using in-memory storage (ArrayLists) and later extended with a MySQL database using JDBC for persistent data storage.

---

## 2. Project Structure

The project follows a layered architecture:

```

src/main/java
│
├── app
│   └── Main.java
│
├── model
│   ├── Person.java
│   ├── Employee.java
│   ├── Passenger.java
│   ├── AirplanePilot.java
│   ├── StaffCabin.java
│   ├── Aircraft.java
│   ├── Flight.java
│   ├── Reservation.java
│   ├── Airport.java
│   ├── FlightStatus.java
│   └── ReservationStatus.java
│
├── service
│   ├── PassengerService.java
│   ├── FlightService.java
│   ├── ReservationService.java
│   └── AircraftService.java
│
└── database
├── DatabaseConnection.java
├── AirportDAO.java
├── PassengerDAO.java
├── AircraftDAO.java
├── FlightDAO.java
└── ReservationDAO.java

```

---

## 3. Features

The system supports the following functionalities:

### Passenger Management
- Add a passenger
- View passenger information

### Flight Management
- Add a flight
- Assign an aircraft (optional)
- Cancel a flight
- View all flights

### Aircraft Management
- Add an aircraft
- Store aircraft details (model, capacity)

### Reservation Management
- Create a reservation for a passenger
- Add one or multiple flights to a reservation
- Cancel a reservation
- View all reservations

### Additional Features
- Display all passengers of a specific flight
- Use of enums for status management (FlightStatus, ReservationStatus)
- Console-based interactive menu

---

## 4. Technologies Used

- Java (OOP concepts)
- JDBC (Java Database Connectivity)
- MySQL (database)
- Maven (dependency management)

---

## 5. Database Design

The project uses a relational database named:

```

airline_management

````

### Tables:

- **airports**
- **passengers**
- **aircrafts**
- **flights**
- **reservations**
- **reservation_flights** (junction table)

### Relationships:

- A flight is linked to:
  - one origin airport
  - one destination airport
  - optionally one aircraft

- A reservation:
  - belongs to one passenger
  - can contain multiple flights

- The `reservation_flights` table enables a **many-to-many relationship** between reservations and flights.

---

## 6. How to Run

### Requirements

- Java (JDK 17 or later recommended)
- MySQL or XAMPP
- Maven

### Steps

1. Create the database:

```sql
CREATE DATABASE airline_management;
````

2. Create all required tables (airports, passengers, aircrafts, flights, reservations, reservation_flights)

3. Configure database connection in:

```
DatabaseConnection.java
```

```java
private static final String URL = "jdbc:mysql://localhost:3306/airline_management";
private static final String USER = "root";
private static final String PASSWORD = "";
```

4. Run the application:

```
Main.java
```

5. Use the console menu to interact with the system.

---

## 7. Design Choices

* DAO pattern used for database access
* Separation between model, service, and database layers
* JDBC used for persistence
* Transactions used when creating reservations
* Enums used instead of strings for statuses
* Simplified date handling using `String`

---

## 8. Limitations

* Crew (pilot and cabin crew) is not stored in the database
* No GUI (console-based application only)
* Date handling could be improved using `LocalDateTime`
* Basic input validation

---

## 9. Conclusion

This project demonstrates the use of object-oriented programming in Java combined with database integration using MySQL and JDBC.

It covers essential concepts such as:

* class design and relationships
* data persistence
* layered architecture (DAO pattern)

The system is functional, extensible, and provides a solid foundation for further improvements.


