package com.eriksson.service;

import com.eriksson.entity.*;
import com.eriksson.enums.RENTALTYPE;
import com.eriksson.exception.DoubleBookingException;
import com.eriksson.exception.InvalidDateException;
import com.eriksson.exception.InvalidIdException;
import com.eriksson.exception.MemberNotFoundException;
import com.eriksson.repo.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import static com.eriksson.enums.RENTALTYPE.*;
import static java.lang.Integer.parseInt;

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

    public void createCar(String brand, String model, String gearbox, Boolean loanable) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException ("fyll i varumärke");
        }
        if(model == null || model.isBlank()) {
            throw new IllegalArgumentException ("fyll i modell");
        }
        if (loanable == null) {
            throw new IllegalArgumentException ("fyll i om den är lånbar");
        }
        Car car = new Car(brand.trim(), model.trim(), gearbox.trim(), loanable);
        carRepository.save(car);
    }
    public void createBike(String model, Boolean loanable, String gears) {
        if(model == null || model.isBlank()) {
            throw new IllegalArgumentException ("fyll i modell");
        }
        if (loanable == null) {
            throw new IllegalArgumentException ("fyll i om den är lånbar");
        }
        Bike bike = new Bike(model.trim(), loanable, gears.trim());
        bikeRepository.save(bike);
    }
    public void createCaravan(String model, Boolean loanable, String axles) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException ("fyll i modell");
        }
        Caravan caravan = new Caravan(model.trim(), loanable, axles);
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

//    public void sum(){
//        double sum = 0;
//        for(Rental cost: rentals){
//            System.out.println("Intäkter: " + cost.getCost());
//            sum = sum + cost.getCost();
//        } System.out.println("Summan av intäkterna: " + sum + " kr");
//    }


        public void terminateRental() {
            System.out.println("Vilket uthyrnings-id har den du vill avsluta?");
            Long id = input.nextLong();
//            Rental rental = rentalRepository.getRentalById(id);
//            if (rental != null) {
//                System.out.println("Hittar uthyrningen: " + rental.getId() + ", "
//                        + rental.getMember() + ", " + rental.getReturnDate());
            rentalRepository.getRentalById(id);
                //uppdate rental till "Avslutad med dagens datum i databasen
                //fordonet ska bli tillgängligt att hyra igen
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
    //Anropa repo från RentalService, i repo ska jag hämta en member med en sql query, returnera member hit
    //hämta från databasen
    public void rentCar(String car, Long rentalObjectId,
                          LocalDate rentalDate, LocalDate returnDate, BigDecimal cost
            , Member member) {
        //hitta rentalobjectId från databasen, kolla om det är false
        //när rental redan är sparad, kolla i rental vad den är kopplad till i db
        //kolla med sql query vad det är för rentalobjectid på car/bike
        //behöver bara veta vilken typ och vilket id, så sparar man en rental
        Optional<Car> objectId = carRepository.findById(rentalObjectId);
        Objects.requireNonNull("Rental får inte vara tomt");
        if (CAR == null) {
            throw new DoubleBookingException("Bilen går inte att låna");
        }
        RENTALTYPE carA = RENTALTYPE.valueOf(String.valueOf(car));

        if (carA == CAR) {
            Rental rental = new Rental(rentalObjectId, rentalDate, returnDate, cost, carA, member);
            rentalRepository.save(rental);
        }
        if (carA == BIKE) {
            Rental rental = new Rental(rentalObjectId, rentalDate, returnDate, cost, carA, member);
            rentalRepository.save(rental);
        }
        if (carA == CARAVAN) {
            Rental rental = new Rental(rentalObjectId, rentalDate, returnDate, cost, carA, member);
            rentalRepository.save(rental);
        }
        System.out.println("Det är nu bokat");
    }
}