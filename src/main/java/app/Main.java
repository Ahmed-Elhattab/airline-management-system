package app;

import database.AircraftDAO;
import database.FlightDAO;
import database.PassengerDAO;
import database.ReservationDAO;
import model.*;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point of the airline management system.
 * Provides a console menu connected to the database layer.
 */
public class Main {

    /**
     * Launches the application menu and handles user interaction.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        PassengerDAO passengerDAO = new PassengerDAO();
        FlightDAO flightDAO = new FlightDAO();
        ReservationDAO reservationDAO = new ReservationDAO();
        AircraftDAO aircraftDAO = new AircraftDAO();

        int choice;

        do {
            System.out.println("========== AIRLINE MANAGEMENT SYSTEM ==========");
            System.out.println("1. Add passenger");
            System.out.println("2. Add flight");
            System.out.println("3. Book flight");
            System.out.println("4. Show flights");
            System.out.println("5. Show reservations");
            System.out.println("6. Add aircraft");
            System.out.println("7. Cancel reservation");
            System.out.println("8. Cancel flight");
            System.out.println("9. Show passengers of a flight");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // Add a new passenger
                case 1:
                    System.out.print("Enter passenger ID: ");
                    int passengerId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.nextLine();

                    System.out.print("Enter passenger address: ");
                    String passengerAddress = scanner.nextLine();

                    System.out.print("Enter passenger contact: ");
                    String passengerContact = scanner.nextLine();

                    System.out.print("Enter passenger passport: ");
                    String passengerPassport = scanner.nextLine();

                    Passenger passenger = new Passenger(
                            passengerId,
                            passengerName,
                            passengerAddress,
                            passengerContact,
                            passengerPassport
                    );

                    boolean passengerAdded = passengerDAO.addPassenger(passenger);

                    if (passengerAdded) {
                        System.out.println("Passenger added successfully to database.");
                    } else {
                        System.out.println("Failed to add passenger.");
                    }
                    break;

                // Add a new flight
                case 2:
                    System.out.print("Enter flight number: ");
                    String flightNumber = scanner.nextLine();

                    System.out.print("Enter origin airport name: ");
                    String originName = scanner.nextLine();
                    System.out.print("Enter origin city: ");
                    String originCity = scanner.nextLine();
                    System.out.print("Enter origin description: ");
                    String originDescription = scanner.nextLine();

                    Airport originAirport = new Airport(originName, originCity, originDescription);

                    System.out.print("Enter destination airport name: ");
                    String destinationName = scanner.nextLine();
                    System.out.print("Enter destination city: ");
                    String destinationCity = scanner.nextLine();
                    System.out.print("Enter destination description: ");
                    String destinationDescription = scanner.nextLine();

                    Airport destinationAirport = new Airport(destinationName, destinationCity, destinationDescription);

                    System.out.print("Enter departure date/time: ");
                    String departureDateTime = scanner.nextLine();

                    System.out.print("Enter arrival date/time: ");
                    String arrivalDateTime = scanner.nextLine();

                    Flight flight = new Flight(
                            flightNumber,
                            originAirport,
                            destinationAirport,
                            departureDateTime,
                            arrivalDateTime,
                            FlightStatus.PLANNED
                    );

                    System.out.print("Do you want to assign an aircraft now? (yes/no): ");
                    String assignAircraftNow = scanner.nextLine();

                    if (assignAircraftNow.equalsIgnoreCase("yes")) {
                        System.out.print("Enter aircraft registration: ");
                        String aircraftRegistration = scanner.nextLine();

                        Aircraft aircraft = aircraftDAO.getAircraftByRegistration(aircraftRegistration);

                        if (aircraft != null) {
                            flight.setAircraft(aircraft);
                        } else {
                            System.out.println("Aircraft not found. Flight will be saved without aircraft.");
                        }
                    }

                    boolean flightAdded = flightDAO.addFlight(flight);

                    if (flightAdded) {
                        System.out.println("Flight added successfully to database.");
                    } else {
                        System.out.println("Failed to add flight.");
                    }
                    break;

                // Create a reservation and attach one or more flights
                case 3:
                    System.out.print("Enter passenger ID: ");
                    int bookingPassengerId = scanner.nextInt();
                    scanner.nextLine();

                    Passenger foundPassenger = passengerDAO.getPassengerById(bookingPassengerId);

                    if (foundPassenger == null) {
                        System.out.println("Passenger not found.");
                        break;
                    }

                    System.out.print("Enter reservation number: ");
                    String reservationNumber = scanner.nextLine();

                    System.out.print("Enter reservation date: ");
                    String reservationDate = scanner.nextLine();

                    Reservation reservation = new Reservation(
                            reservationNumber,
                            reservationDate,
                            ReservationStatus.CONFIRMED
                    );

                    String addAnotherFlight;
                    do {
                        System.out.print("Enter flight number to add to reservation: ");
                        String bookingFlightNumber = scanner.nextLine();

                        Flight foundFlight = flightDAO.getFlightByNumber(bookingFlightNumber);

                        if (foundFlight == null) {
                            System.out.println("Flight not found.");
                        } else {
                            reservation.getFlights().add(foundFlight);
                            System.out.println("Flight added to reservation.");
                        }

                        System.out.print("Do you want to add another flight to this reservation? (yes/no): ");
                        addAnotherFlight = scanner.nextLine();

                    } while (addAnotherFlight.equalsIgnoreCase("yes"));

                    if (reservation.getFlights().isEmpty()) {
                        System.out.println("No valid flights were added. Reservation not saved.");
                        break;
                    }

                    boolean reservationAdded = reservationDAO.addReservation(reservation, foundPassenger.getId());

                    if (reservationAdded) {
                        System.out.println("Reservation saved successfully to database.");
                    } else {
                        System.out.println("Failed to save reservation.");
                    }
                    break;

                // Display all flights from the database
                case 4:
                    System.out.println("\n===== FLIGHTS FROM DATABASE =====");
                    List<Flight> flights = flightDAO.getAllFlights();

                    if (flights.isEmpty()) {
                        System.out.println("No flights found.");
                    } else {
                        for (Flight f : flights) {
                            System.out.println(f);
                        }
                    }
                    break;

                // Display all reservations from the database
                case 5:
                    System.out.println("\n===== RESERVATIONS FROM DATABASE =====");
                    List<Reservation> reservations = reservationDAO.getAllReservations();

                    if (reservations.isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        for (Reservation r : reservations) {
                            System.out.println(r);
                            System.out.println("Flights in this reservation:");
                            for (Flight f : r.getFlights()) {
                                System.out.println("  - " + f.getFlightNumber());
                            }
                        }
                    }
                    break;

                // Add a new aircraft
                case 6:
                    System.out.print("Enter aircraft registration: ");
                    String registration = scanner.nextLine();

                    System.out.print("Enter aircraft model: ");
                    String aircraftModel = scanner.nextLine();

                    System.out.print("Enter aircraft capacity: ");
                    int capacity = scanner.nextInt();
                    scanner.nextLine();

                    Aircraft aircraft = new Aircraft(registration, aircraftModel, capacity);

                    boolean aircraftAdded = aircraftDAO.addAircraft(aircraft);

                    if (aircraftAdded) {
                        System.out.println("Aircraft added successfully to database.");
                    } else {
                        System.out.println("Failed to add aircraft.");
                    }
                    break;

                // Cancel an existing reservation
                case 7:
                    System.out.print("Enter reservation number to cancel: ");
                    String cancelReservationNumber = scanner.nextLine();

                    Reservation reservationToCancel =
                            reservationDAO.getReservationByNumber(cancelReservationNumber);

                    if (reservationToCancel == null) {
                        System.out.println("Reservation not found.");
                        break;
                    }

                    boolean reservationCanceled = reservationDAO.updateReservationStatus(
                            cancelReservationNumber,
                            ReservationStatus.CANCELED
                    );

                    if (reservationCanceled) {
                        System.out.println("Reservation canceled successfully.");
                    } else {
                        System.out.println("Failed to cancel reservation.");
                    }
                    break;

                // Cancel an existing flight
                case 8:
                    System.out.print("Enter flight number to cancel: ");
                    String cancelFlightNumber = scanner.nextLine();

                    Flight flightToCancel = flightDAO.getFlightByNumber(cancelFlightNumber);

                    if (flightToCancel == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    flightToCancel.setStatus(FlightStatus.CANCELED);

                    boolean flightCanceled = flightDAO.updateFlight(flightToCancel);

                    if (flightCanceled) {
                        System.out.println("Flight canceled successfully.");
                    } else {
                        System.out.println("Failed to cancel flight.");
                    }
                    break;

                // Show all passengers linked to a specific flight
                case 9:
                    System.out.print("Enter flight number: ");
                    String passengerFlightNumber = scanner.nextLine();

                    Flight flightForPassengers = flightDAO.getFlightByNumber(passengerFlightNumber);

                    if (flightForPassengers == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    List<Passenger> passengers = passengerDAO.getPassengersByFlightNumber(passengerFlightNumber);

                    System.out.println("\n===== PASSENGERS OF FLIGHT " + passengerFlightNumber + " =====");
                    if (passengers.isEmpty()) {
                        System.out.println("No passengers found for this flight.");
                    } else {
                        for (Passenger p : passengers) {
                            System.out.println(p);
                        }
                    }
                    break;

                // Exit the program
                case 10:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 10);

        scanner.close();
    }
}