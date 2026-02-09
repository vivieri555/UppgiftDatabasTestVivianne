package com.eriksson.service;

import com.eriksson.entity.*;
import com.eriksson.enums.RENTALTYPE;
import com.eriksson.exception.DoubleBookingException;
import com.eriksson.exception.InvalidDateException;
import com.eriksson.exception.VehicleNotFoundException;
import com.eriksson.repo.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import static com.eriksson.enums.RENTALTYPE.*;

public class RentalService {

    private final RentalRepositoryInterface rentalRepository;
    private final CarRepositoryInterface carRepository;
    private final BikeRepositoryInterface bikeRepository;
    private final CaravanRepositoryInterface caravanRepository;
    private final MemberRepositoryInterface memberRepository;

    public RentalService(RentalRepositoryInterface rentalRepository, CarRepositoryInterface carRepository
            , BikeRepositoryInterface bikeRepository, CaravanRepositoryInterface caravanRepository, MemberRepositoryInterface memberRepository)
    { this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.bikeRepository = bikeRepository;
        this.caravanRepository = caravanRepository;
    this.memberRepository = memberRepository; }

    Scanner input = new Scanner(System.in);

    public void createCar(String brand, String model, String gearbox) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException ("fyll i varumärke");
        }
        if(model == null || model.isBlank()) {
            throw new IllegalArgumentException ("fyll i modell");
        }
        Car car = new Car(brand.trim(), model.trim(), gearbox.trim());
        carRepository.save(car);
    }
    public void createBike(String model, String gears) {
        if(model == null || model.isBlank()) {
            throw new IllegalArgumentException ("fyll i modell");
        }
        Bike bike = new Bike(model.trim(), gears.trim());
        bikeRepository.save(bike);
    }
    public void createCaravan(String model, String axles) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException ("fyll i modell");
        }
        Caravan caravan = new Caravan(model.trim(), axles);
        caravanRepository.save(caravan);
    }
    public BigDecimal cost(Member member, int days) {
        if (member.getStatus().equalsIgnoreCase("Premium")) {
            System.out.println("Medlemmen kan få rabatt 100 kr på varje uthyrning");
            return BigDecimal.valueOf(1050.50 * days - 100.50);
        }
        else {
            return BigDecimal.valueOf(1050.50 * days);
        }
    }
        public void terminateRental() {
            System.out.println("Vilket uthyrnings-id har den du vill avsluta?");
            Long id = input.nextLong();
            rentalRepository.terminateRental(id);
                System.out.println("Bokning avslutad.");
        }
    public LocalDate rentalDate(String rentalDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookingDate;
        try {
            bookingDate = LocalDate.parse(rentalDate, formatter);
            System.out.println("Påbörjar uthyrningen datumet " + bookingDate);
        }
        catch (Exception e) {
            throw new InvalidDateException("Ogiltigt uthyrningsdatum, fyll i åååå-mm-dd");
        }
        return bookingDate;
    }
    public LocalDate returnDate(String returnDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate returnD;
        try {
            returnD = LocalDate.parse(returnDate, formatter);
            System.out.println("Återlämningsdatum: " + returnD);
        } catch (Exception e) {
            throw new InvalidDateException ("Ogiltigt returdatum, fyll i åååå-mm-dd");
        }
        return returnD;
    }
    public long dateDiff(LocalDate rentalDate, LocalDate returnDate) {
        return rentalDate.until(returnDate, ChronoUnit.DAYS);
    }
    public void rentVehicle(String vehicleType, Long rentalObjectId,
                            LocalDate rentalDate, LocalDate returnDate, BigDecimal cost
            , Member member) {


        Objects.requireNonNull(vehicleType,"Rental får inte vara tomt");

        RENTALTYPE vehicle = RENTALTYPE.valueOf(vehicleType.trim().toUpperCase());
        if (rentalRepository.isVehicleRented(vehicle, rentalObjectId)) {
            throw new DoubleBookingException("Fordonet är redan uthyrt");
        }
        if (vehicle == CAR) {
            Optional<Car> car = carRepository.findById(rentalObjectId);
            if(car.isEmpty()) {
                throw new VehicleNotFoundException("Finns ingen bil");
            }
            Rental rental = new Rental(rentalObjectId, rentalDate, returnDate, cost, vehicle, member);
            rentalRepository.save(rental);
        }
        if (vehicle == BIKE) {
            Optional<Bike> bike = bikeRepository.findById(rentalObjectId);
            if(bike.isEmpty()) {
                throw new VehicleNotFoundException("Finns ingen cykel");
            }
            Rental rental = new Rental(rentalObjectId, rentalDate, returnDate, cost, vehicle, member);
            rentalRepository.save(rental);
        }
        if (vehicle == CARAVAN) {
            Optional<Caravan> caravan = caravanRepository.findById(rentalObjectId);
            if(caravan.isEmpty()) {
                throw new VehicleNotFoundException("Finns ingen husvagn");
            }
            Rental rental = new Rental(rentalObjectId, rentalDate, returnDate, cost, vehicle, member);
            rentalRepository.save(rental);
        }
        System.out.println("Det är nu bokat");
    }
}