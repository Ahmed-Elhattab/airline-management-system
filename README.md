# Airline Management System

## Introduction

This project is a console-based Airline Management System developed in Java as part of an academic assignment.  
It allows managing the main elements of an airline such as passengers, flights, aircraft, and reservations.

The system was initially implemented using in-memory data structures (ArrayLists), then extended by integrating a MySQL database using JDBC in order to make the data persistent.

---

## Project Overview

The application simulates basic airline operations. Through a menu in the console, the user can create and manage passengers, flights, and reservations.

The project follows a layered approach:

- The **model layer** represents the main entities (Passenger, Flight, Reservation, etc.)
- The **service layer** handles logic in memory (initial version)
- The **database layer (DAO)** handles data storage and retrieval using MySQL

The final version mainly uses the database layer for data persistence.

---

## Main Functionalities

### Passenger Management
- Add a new passenger with personal information
- Retrieve passenger data by ID
- Store passenger data in the database

### Flight Management
- Create a flight with origin, destination, and schedule
- Assign an aircraft to a flight (optional)
- Cancel an existing flight
- Display all available flights

### Aircraft Management
- Add new aircraft with model and capacity
- Store aircraft information in the database

### Reservation Management
- Create a reservation linked to a passenger
- Add one or multiple flights to a reservation
- Store reservations in the database
- Cancel a reservation
- View all reservations with their flights

### Additional Feature
- Display all passengers assigned to a specific flight using database queries

---

## Technologies Used

- Java (Object-Oriented Programming)
- JDBC (Java Database Connectivity)
- MySQL (database management)
- Maven (dependency management)

---

## Database Design

The project uses a MySQL database named:

airline_management

The database is composed of several tables:

- **airports**: stores airport information (name, city, description)
- **passengers**: stores passenger data
- **aircrafts**: stores aircraft information
- **flights**: stores flight details and links to airports and aircraft
- **reservations**: stores reservation information linked to passengers
- **reservation_flights**: intermediate table linking reservations and flights

### Relationships

- A flight is linked to:
  - one origin airport
  - one destination airport
  - optionally one aircraft

- A reservation:
  - belongs to one passenger
  - can include multiple flights

- The `reservation_flights` table enables a many-to-many relationship between reservations and flights.

---

## How to Run the Project

### Requirements

- Java (JDK 17 or later recommended)
- MySQL or XAMPP
- Maven

### Steps

1. Create the database in MySQL:

   CREATE DATABASE airline_management;

2. Create all necessary tables (airports, passengers, aircrafts, flights, reservations, reservation_flights)

3. Open the file `DatabaseConnection.java` and verify the connection settings:
   - URL
   - username
   - password

4. Run the application by executing `Main.java`

5. Use the console menu to interact with the system

---

## Design Choices

Several design decisions were made during the development:

- Use of the **DAO pattern** to separate database logic from business logic
- Keeping the **service layer** for the initial in-memory implementation
- Direct interaction with DAO classes in the final version for simplicity
- Use of **transactions** when inserting reservations to ensure data consistency
- Use of **enums** for managing flight and reservation status instead of raw strings

---

## Limitations

- Crew members (pilot and cabin crew) are not stored in the database yet
- The application uses a console interface not graphical UI for now

---

## Conclusion

This project demonstrates the application of object-oriented programming in Java combined with database integration using MySQL and JDBC.

It provides a functional system for managing airline operations and highlights key concepts such as class design, relationships, and data persistence.

The project can be extended in the future by adding features such as a graphical interface, improved validation, or full management of airline staff.
