CREATE DATABASE airline_management;
USE airline_management;

CREATE TABLE airports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    city VARCHAR(100),
    description VARCHAR(255),
    UNIQUE(name, city)
);

CREATE TABLE passengers (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(255),
    contact VARCHAR(50),
    passport VARCHAR(50)
);

CREATE TABLE aircrafts (
    registration VARCHAR(50) PRIMARY KEY,
    model VARCHAR(100),
    capacity INT
);

CREATE TABLE flights (
    flight_number VARCHAR(50) PRIMARY KEY,
    origin_airport_id INT,
    destination_airport_id INT,
    departure_time VARCHAR(50),
    arrival_time VARCHAR(50),
    status VARCHAR(50),
    aircraft_registration VARCHAR(50),
    FOREIGN KEY (origin_airport_id) REFERENCES airports(id),
    FOREIGN KEY (destination_airport_id) REFERENCES airports(id),
    FOREIGN KEY (aircraft_registration) REFERENCES aircrafts(registration)
);

CREATE TABLE reservations (
    reservation_number VARCHAR(50) PRIMARY KEY,
    date_reservation VARCHAR(50),
    status VARCHAR(50),
    passenger_id INT,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id)
);

CREATE TABLE reservation_flights (
    reservation_number VARCHAR(50),
    flight_number VARCHAR(50),
    PRIMARY KEY (reservation_number, flight_number),
    FOREIGN KEY (reservation_number) REFERENCES reservations(reservation_number),
    FOREIGN KEY (flight_number) REFERENCES flights(flight_number)
);
