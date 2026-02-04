package com.eriksson.service;

import com.eriksson.entity.*;
import com.eriksson.enums.RentalType;
import com.eriksson.exception.DoubleBookingException;
import com.eriksson.exception.InvalidDateException;
import com.eriksson.exception.MemberNotFoundException;
import com.eriksson.repo.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import static com.eriksson.enums.RentalType.CAR;

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
    public BigDecimal cost(int days) {
        return BigDecimal.valueOf(1000 * days);
    }
    /*
    public double getDiscountedCost(Rental rental) {
        double discountedCost = 0;
        if (rental.getMember().getStatus().equalsIgnoreCase("Premium")) {
            System.out.println("Medlemmen kan få rabatt 100 kr på varje uthyrning");
            discountedCost = cost(rental.getRentalDays()) -100;
        }
        else{
            return cost(rental.getRentalDays());
        }
        return discountedCost;
    }

    public void sum(){
        double sum = 0;
        for(Rental cost: rentals){
            System.out.println("Intäkter: " + cost.getCost());
            sum = sum + cost.getCost();
        } System.out.println("Summan av intäkterna: " + sum + " kr");
    }


        public void terminateRental(String name, Label termLabel, Label terminate){
            for(Rental rental: rentals){
                if(rental.getMember().getName().equals(name)){
                    termLabel.setText("Avslutar bokning på " + rental.getMember().getName());
                    System.out.println("Avslutar bokning på " + rental.getMember().getName());
                    rental.getVehicle().setLoanable(true);
                    delete(rental);
                }
            } terminate.setText("Avslutar bokningen");
        }
        */
    /*
   public String available(){
       String text = "";
       StringBuilder textBuilder = new StringBuilder();
       for(Car vehicle: inventory.getVehicleList()){
           if(vehicle.isLoanable()){
               textBuilder.append(vehicle.getBrand() + ", ").append(vehicle.getModel()).append("\n");
           }
           text = textBuilder.toString();
       } return text;
   }
   public void bookVehicle(Vehicle vehicle, Member searchedNamed, TextField booking, TextField days,
                           Label search, Label discountLabel, TextField historyText, Label saved) {
       Rental rental = new Rental();
       rental.setMember(searchedNamed);

       booking.getText();
       if (vehicle == null || !vehicle.isLoanable()) {
           search.setText("Fordonet är inte tillgängligt att låna just nu");
       } else {
           vehicle.setLoanable(false);
           rental.setVehicle(vehicle);
           booking.clear();

           String day = String.valueOf((days.getText()));
           rental.setRentalDays(Integer.parseInt(day));
           int amount = cost(Integer.parseInt(days.getText()));
           search.setText("Kostnaden blir innan eventuell rabatt " + amount + " kr.");
           rental.setCost(amount);

           double discount = getDiscountedCost(rental, discountLabel);
           rental.setCost(discount);
           rentals.add(rental);

           searchedNamed.setHistory(historyText.getText());
           saved.setText("Bokning sparad");
       }
   } */
    public LocalDate dateFormat(String rentalDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookingDate;
        try {
            bookingDate = LocalDate.parse(rentalDate, formatter);
            //bookingDate = localDate.plusDays(rentalDays);
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
       // LocalDate date1 = LocalDate.of(LocalDate.parse(rentalDate));
       // LocalDate date2 = LocalDate.of(returnDate);
        //long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        long daysBetween = rentalDate.until(returnDate, ChronoUnit.DAYS);
        return daysBetween;
    }
    //Anropa repo från RentalService, i repo ska jag hämta en member med en sql query, returnera member hit
    //hämta från databasen
    public Rental rentCar(RentalType rentalType, long rentalObjectId,
                          LocalDate rentalDate, LocalDate returnDate, Long memberId) {
        //hitta rentalobjectId från databasen, kolla om det är false
        Optional<Car> objectId = carRepository.findById(rentalObjectId);
        Long member = memberRepository.searchId(memberId);
        Objects.requireNonNull("Rental får inte vara tomt");
        if (CAR == null) {
            throw new DoubleBookingException("Bilen går inte att låna");
        }
        if(rentalObjectId == 0) {
            //koppla ihop objektet bil med id
        }
        if (rentalType == CAR) {
            System.out.println("Boka bil");
        }
//        if (rentalDays <= 0) {
//            throw new RentalDaysException("Hyrdagar får inte vara 0");
//        }
        try {LocalDate.parse(rentalDate);} catch (Exception e){
            throw new InvalidDateException("Ogiltigt datum");}

        if (member == null) {
            throw new MemberNotFoundException("Hittar inte medlem");
        }
        Rental rental = new Rental(0, rentalDate, returnDate, cost, rentalType, member);
        rentalRepository.save(rental);
        return rental;
    }
}