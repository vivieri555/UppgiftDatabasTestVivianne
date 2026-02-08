package com.eriksson;

import com.eriksson.entity.Member;
import com.eriksson.entity.Rental;
import com.eriksson.repo.*;
import com.eriksson.service.MemberService;
import com.eriksson.service.RentalService;
import com.eriksson.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //repo

        MemberRepositoryInterface memberRepo = new MemberRepository(sessionFactory);
        RentalRepositoryInterface rentalRepo = new RentalRepository(sessionFactory);
        CarRepositoryInterface carRepo = new CarRepository(sessionFactory);
        BikeRepositoryInterface bikeRepo = new BikeRepository(sessionFactory);
        CaravanRepositoryInterface caravanRepo = new CaravanRepository(sessionFactory);

        //Service
        MemberService memberService = new MemberService(memberRepo);
        RentalService rentalService = new RentalService(rentalRepo, carRepo, bikeRepo, caravanRepo, memberRepo);

        Scanner input = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("*************************************************");
            System.out.println("------Välkommen till Vivi's uthyrning!-----------");
            System.out.println("För att komma till meny för medlemmar tryck 1");
            System.out.println("För att skapa ny bil tryck 2");
            System.out.println("För att skapa ny cykel tryck 3");
            System.out.println("För att skapa ny husvagn tryck 4");
            System.out.println("För att skapa ny bokning tryck 5");
            System.out.println("För att avsluta uthyrning tryck 6");
            System.out.println("Tryck 7 för se samlade intäkter");
            System.out.println("8 lista rentals");
            System.out.println("Avsluta tryck 9");
            int answer = 0;
            try {
                answer = input.nextInt();
            }
            catch (Exception e) {
                System.out.println("Du måste göra ett giltigt menyval");
            }
            switch (answer) {
                case 1:
                   memberService.membersMenu();
                    break;
                case 2:
                    input.nextLine();
                    System.out.println("Skriv in uppgifter för att spara ny bil");
                    System.out.println("Skriv in varumärke:");
                    String carBrand = input.nextLine();
                    System.out.println("Skriv in modell:");
                    String carModel = input.nextLine();
                    System.out.println("Vilken växellåda har bilen:");
                    String carGearbox = input.nextLine();
                    System.out.println("Lånbar true eller false:");
                    Boolean carLoanable = input.nextBoolean();
                    rentalService.createCar(carBrand, carModel, carGearbox, carLoanable);
                    //Spara in bilen
                    break;
                case 3:
                    input.nextLine();
                    System.out.println("Vad har cykeln för modell:");
                    String bikeModel = input.nextLine();
                    System.out.println("Lånbar true annars skriv false:");
                    Boolean bikeLoanable = input.nextBoolean();
                    input.nextLine();
                    System.out.println("Hur många växlar har cykeln:");
                    String bikeGears = input.nextLine();
                    rentalService.createBike(bikeModel, bikeLoanable, bikeGears);
                    break;
                case 4:
                    input.nextLine();
                    System.out.println("Fyll i modell på husvagnen:");
                    String caravanModel = input.nextLine();
                    System.out.println("Är husvagnen lånbar true eller false:");
                    Boolean caravanLoanable = input.nextBoolean();
                    input.nextLine();
                    System.out.println("Är husvagnen Dubbalaxlad eller Enkelaxlad:");
                    String caravanAxles = input.nextLine();
                    rentalService.createCaravan(caravanModel, caravanLoanable, caravanAxles);
                    break;
                case 5:
                    //Hämta medlemmar
                    for(Member member : memberService.getAllMembers())
                    {
                        System.out.println(member.getFirstName() + ", id: " + member.getId());
                    }
                    System.out.println("Skriv in id på medlemmen du önskar göra bokningen på, se id i listan");
                    Long id = input.nextLong();

                    input.nextLine();
                    System.out.println("Vill du boka CAR, BIKE eller CARAVAN?");
                    String car = input.nextLine();
                    System.out.println("Vilket Rentalobject id har fordonet du vill hyra:");
                    Long objectId = input.nextLong();
                    input.nextLine();
                    System.out.println("Från vilket datum vill du boka? Ange åååå-mm-dd");
                    String date = input.nextLine();
                    System.out.println("Till vilket datum önskar du boka? Ange åååå-mm-dd");
                    String returnD = input.nextLine();
                    LocalDate rentalDate = rentalService.rentalDate(date);
                    LocalDate returnDate = rentalService.returnDate(returnD);
                    long dateDiff = rentalService.dateDiff(rentalDate, returnDate);
                    Member member = memberRepo.searchId((id));
                    BigDecimal amount = rentalService.cost(member, (int)dateDiff);
                    System.out.println("Kostnaden blir uträknat: " + amount);

                    rentalService.rentCar(car, objectId, rentalDate, returnDate, amount, member);

                case 6:
                    rentalService.terminateRental();
                    break;
                case 8:
                    for(Object rental: rentalRepo.getAllRentals()) {
                        System.out.println(rental.toString());
                    }
                    break;
                case 9:
                    System.out.println("Uthyrningsprogrammet avslutas nu..\nVälkommen åter!");
                    running = false;
                    break;
                default:
                    System.out.println("Prova igen att ange ett giltigt menyval.");
                    break;
            }

            System.out.println();
        }

    }

}
