package app;

import model.*;
import service.AircraftService;
import service.FlightService;
import service.PassengerService;
import service.ReservationService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        PassengerService passengerService = new PassengerService();
        FlightService flightService = new FlightService();
        ReservationService reservationService = new ReservationService();
        AircraftService aircraftService = new AircraftService();

        int choice;

        do {
            System.out.println("========== AIRLINE MANAGEMENT SYSTEM ==========");
            System.out.println("1. Add passenger");
            System.out.println("2. Add flight");
            System.out.println("3. Book flight");
            System.out.println("4. Show flights");
            System.out.println("5. Show reservations");
            System.out.println("6. Cancel reservation");
            System.out.println("7. Cancel flight");
            System.out.println("8. Add aircraft");
            System.out.println("9. Assign aircraft to flight");
            System.out.println("10. Assign pilot to flight");
            System.out.println("11. Assign cabin crew to flight");
            System.out.println("12. Show passengers of a flight");
            System.out.println("13. Show passengers");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

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

                    passengerService.addPassenger(passenger);
                    System.out.println("Passenger added successfully.");
                    break;

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

                    flightService.addFlight(flight);
                    System.out.println("Flight added successfully.");
                    break;

                case 3:
                    System.out.print("Enter passenger ID: ");
                    int bookingPassengerId = scanner.nextInt();
                    scanner.nextLine();

                    Passenger foundPassenger = passengerService.getPassengerById(bookingPassengerId);

                    if (foundPassenger == null) {
                        System.out.println("Passenger not found.");
                        break;
                    }

                    System.out.print("Enter flight number: ");
                    String bookingFlightNumber = scanner.nextLine();

                    Flight foundFlight = flightService.getFlightByNumber(bookingFlightNumber);

                    if (foundFlight == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    System.out.print("Enter reservation number: ");
                    String reservationNumber = scanner.nextLine();

                    System.out.print("Enter reservation date: ");
                    String reservationDate = scanner.nextLine();

                    Reservation reservation = reservationService.bookFlight(
                            foundPassenger,
                            foundFlight,
                            reservationNumber,
                            reservationDate
                    );

                    if (reservation != null) {
                        System.out.println("Flight booked successfully.");
                        System.out.println(reservation);
                    } else {
                        System.out.println("Booking failed.");
                    }
                    break;

                case 4:
                    System.out.println("\n===== FLIGHTS =====");
                    if (flightService.getAllFlights().isEmpty()) {
                        System.out.println("No flights available.");
                    } else {
                        for (Flight f : flightService.getAllFlights()) {
                            System.out.println(f);
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n===== RESERVATIONS =====");
                    if (reservationService.getAllReservations().isEmpty()) {
                        System.out.println("No reservations available.");
                    } else {
                        for (Reservation r : reservationService.getAllReservations()) {
                            System.out.println(r);
                        }
                    }
                    break;

                case 6:
                    System.out.print("Enter reservation number to cancel: ");
                    String cancelReservationNumber = scanner.nextLine();

                    boolean reservationCanceled =
                            reservationService.cancelReservationByNumber(cancelReservationNumber);

                    if (reservationCanceled) {
                        System.out.println("Reservation canceled successfully.");
                    } else {
                        System.out.println("Reservation not found.");
                    }
                    break;

                case 7:
                    System.out.print("Enter flight number to cancel: ");
                    String cancelFlightNumber = scanner.nextLine();

                    boolean flightCanceled = flightService.cancelFlight(cancelFlightNumber);

                    if (flightCanceled) {
                        System.out.println("Flight canceled successfully.");
                    } else {
                        System.out.println("Flight not found.");
                    }
                    break;

                case 8:
                    System.out.print("Enter aircraft registration: ");
                    String registration = scanner.nextLine();

                    System.out.print("Enter aircraft model: ");
                    String aircraftModel = scanner.nextLine();

                    System.out.print("Enter aircraft capacity: ");
                    int capacity = scanner.nextInt();
                    scanner.nextLine();

                    Aircraft aircraft = new Aircraft(registration, aircraftModel, capacity);
                    aircraftService.addAircraft(aircraft);

                    System.out.println("Aircraft added successfully.");
                    break;

                case 9:
                    System.out.print("Enter aircraft registration: ");
                    String aircraftRegistration = scanner.nextLine();

                    Aircraft foundAircraft = aircraftService.getAircraftByRegistration(aircraftRegistration);

                    if (foundAircraft == null) {
                        System.out.println("Aircraft not found.");
                        break;
                    }

                    System.out.print("Enter flight number: ");
                    String aircraftFlightNumber = scanner.nextLine();

                    Flight aircraftFlight = flightService.getFlightByNumber(aircraftFlightNumber);

                    if (aircraftFlight == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    boolean aircraftAssigned = aircraftService.assignAircraftToFlight(
                            foundAircraft,
                            aircraftFlight,
                            flightService.getAllFlights()
                    );

                    if (aircraftAssigned) {
                        System.out.println("Aircraft assigned successfully.");
                    } else {
                        System.out.println("Aircraft is not available for this flight.");
                    }
                    break;

                case 10:
                    System.out.print("Enter flight number: ");
                    String pilotFlightNumber = scanner.nextLine();

                    Flight pilotFlight = flightService.getFlightByNumber(pilotFlightNumber);

                    if (pilotFlight == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    System.out.print("Enter pilot ID: ");
                    int pilotId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter pilot name: ");
                    String pilotName = scanner.nextLine();

                    System.out.print("Enter pilot address: ");
                    String pilotAddress = scanner.nextLine();

                    System.out.print("Enter pilot contact: ");
                    String pilotContact = scanner.nextLine();

                    System.out.print("Enter employee number: ");
                    int pilotEmpNumber = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter hiring date: ");
                    String pilotHiringDate = scanner.nextLine();

                    System.out.print("Enter licence: ");
                    String pilotLicence = scanner.nextLine();

                    System.out.print("Enter flight hours: ");
                    int pilotHours = scanner.nextInt();
                    scanner.nextLine();

                    AirplanePilot pilot = new AirplanePilot(
                            pilotId,
                            pilotName,
                            pilotAddress,
                            pilotContact,
                            pilotEmpNumber,
                            pilotHiringDate,
                            pilotLicence,
                            pilotHours
                    );

                    boolean pilotAssigned = flightService.assignPilotToFlight(pilotFlightNumber, pilot);

                    if (pilotAssigned) {
                        System.out.println("Pilot assigned successfully.");
                    } else {
                        System.out.println("Pilot assignment failed.");
                    }
                    break;

                case 11:
                    System.out.print("Enter flight number: ");
                    String cabinFlightNumber = scanner.nextLine();

                    Flight cabinFlight = flightService.getFlightByNumber(cabinFlightNumber);

                    if (cabinFlight == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    System.out.print("Enter cabin crew ID: ");
                    int cabinId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter cabin crew name: ");
                    String cabinName = scanner.nextLine();

                    System.out.print("Enter cabin crew address: ");
                    String cabinAddress = scanner.nextLine();

                    System.out.print("Enter cabin crew contact: ");
                    String cabinContact = scanner.nextLine();

                    System.out.print("Enter employee number: ");
                    int cabinEmpNumber = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter hiring date: ");
                    String cabinHiringDate = scanner.nextLine();

                    System.out.print("Enter qualification: ");
                    String qualification = scanner.nextLine();

                    StaffCabin staffCabin = new StaffCabin(
                            cabinId,
                            cabinName,
                            cabinAddress,
                            cabinContact,
                            cabinEmpNumber,
                            cabinHiringDate,
                            qualification
                    );

                    boolean cabinAssigned =
                            flightService.assignCabinCrewToFlight(cabinFlightNumber, staffCabin);

                    if (cabinAssigned) {
                        System.out.println("Cabin crew assigned successfully.");
                    } else {
                        System.out.println("Cabin crew assignment failed.");
                    }
                    break;

                case 12:
                    System.out.print("Enter flight number: ");
                    String passengerFlightNumber = scanner.nextLine();

                    Flight passengerFlight = flightService.getFlightByNumber(passengerFlightNumber);

                    if (passengerFlight == null) {
                        System.out.println("Flight not found.");
                    } else {
                        passengerFlight.listingPassenger(passengerService.getAllPassengers());
                    }
                    break;

                case 13:
                    System.out.println("\n===== PASSENGERS =====");
                    if (passengerService.getAllPassengers().isEmpty()) {
                        System.out.println("No passengers available.");
                    } else {
                        for (Passenger p : passengerService.getAllPassengers()) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 14:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 14);

        scanner.close();
    }
}