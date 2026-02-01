package com.eriksson.service;

import com.eriksson.entity.Car;
import com.eriksson.entity.Member;
import com.eriksson.entity.Rental;
import com.eriksson.enums.RentalType;
import com.eriksson.exception.DoubleBookingException;
import com.eriksson.exception.InvalidDateException;
import com.eriksson.exception.MemberNotFoundException;
import com.eriksson.exception.RentalDaysException;
import com.eriksson.repo.CarRepositoryInterface;
import com.eriksson.repo.RentalRepository;
import com.eriksson.repo.RentalRepositoryInterface;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static com.eriksson.enums.RentalType.CAR;

public class RentalService {

    private final RentalRepositoryInterface rentalRepository;
    private final CarRepositoryInterface carRepository;


    public RentalService(RentalRepositoryInterface rentalRepository, CarRepositoryInterface carRepository)
    { this.rentalRepository = rentalRepository;
     this.carRepository = carRepository; }

    public void createCar(String brand, String model, String gearbox, Boolean loanable) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException ("fyll i namn");
        }
        if(model == null || model.isBlank()) {
            throw new IllegalArgumentException ("fyll i email");
        }
        if (loanable == null) {
            throw new IllegalArgumentException ("fyll i loanable");
        }
        Car car = new Car(brand.trim(), model.trim(), gearbox.trim(), loanable);
        carRepository.save(car);
    }
    public void addRental (Rental rental) {
    //rentals.add(rental);
    //rental.setRental(this);
    }

/*
    public void sum(Label sumLabel, Label sumsLabel){
        double sum = 0;
        for(Rental cost: rentals){
            sumLabel.setText("Intäkterna: " + cost.getCost());
            System.out.println("Intäkter: " + cost.getCost());
            sum = sum + cost.getCost();
        } sumsLabel.setText("Summan av intäkterna: " + sum + " kr");
    }
    public Vehicle searchCar(String search) {
        for (Vehicle car : inventory.getVehicleList()) {
            if (car.getBrand().contains(search) || car.getModel().contains(search)) {
                return car;
            }
        }
        return null;
    }
    public int cost(int days) {
        return 1000 * days;
    }
    public void listRental(){
        for (Rental rental : rentals){
            System.out.println("Uthyrningens uppgifter:");
            System.out.println(rental.member.getName() + ", bilen: " + rental.getVehicle() + ", " + rental.getRentalDays() + " dagar, Kostnad: " + rental.getCost());
        }
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
    public void delete(Rental rental) {
        {
            rentals.remove(rental);
        }
    }
    public double getDiscountedCost(Rental rental, Label discountLabel) {
        double discountedCost = 0;
        if (rental.member.getStatus().equalsIgnoreCase("Premium")) {
            discountLabel.setText("Medlemmen kan få rabatt 100 kr på varje uthyrning");
            discountedCost = cost(rental.getRentalDays()) -100;
        }
        else{
            return cost(rental.getRentalDays());
        }
        return discountedCost;
    }
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
    }
    public Rental rentCar(RentalType rentalType, long rentalObjectId, int rentalDays, double cost,
                          String rentalDate) {

        Objects.requireNonNull("Rental får inte vara tomt");
        if (CAR == null && Car.isLoanable() == false) {
            throw new DoubleBookingException("Bilen går inte att låna");
        }
        if(rentalObjectId == 0) {
        //koppla ihop objektet bil med id
        }
        if (rentalType == CAR) {
            System.out.println("Boka bil");
        }
        if (rentalDays <= 0) {
            throw new RentalDaysException("Hyrdagar får inte vara 0");
        }
        try {LocalDate.parse(rentalDate);} catch (Exception e){
            throw new InvalidDateException("Ogiltigt datum");}

        if (member.getId() == null) {
            throw new MemberNotFoundException("Hittar inte medlem");
        }
        Rental rental = new Rental(0, rentalDays, cost, rentalType);
        rentalRepository.save(rental);
        return rental;
    } */
}
