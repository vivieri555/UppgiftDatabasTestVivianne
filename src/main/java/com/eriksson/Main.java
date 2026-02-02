package com.eriksson;

import com.eriksson.entity.Member;
import com.eriksson.repo.*;
import com.eriksson.service.MemberService;
import com.eriksson.service.RentalService;
import com.eriksson.util.HibernateUtil;
import org.hibernate.SessionFactory;

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
        RentalService rentalService = new RentalService(rentalRepo, carRepo, bikeRepo, caravanRepo);

        Scanner input = new Scanner(System.in);

        Boolean running = true;
        while (running) {
            System.out.println("*************************************************");
            System.out.println("------Välkommen till Vivi's uthyrning!-----------");
            System.out.println("För att skapa ny kund tryck 1");
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
                    input.nextLine();
                    System.out.println("För att skapa kund, skriv in Förnamn:");
                    String firstName = input.nextLine();
                    System.out.println("Skriv in Efternamn: ");
                    String lastName = input.nextLine();
                    System.out.println("Skriv in email:");
                    String email = input.nextLine();
                    System.out.println("Fyll i status Standard eller Premium:");
                    String status = input.nextLine();
                    System.out.println("Fyll i eventuell historik:");
                    String history = input.nextLine();

                    memberService.createMember(firstName, lastName, email, status, history);
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
                    System.out.println("Ange id på medlem:");
                    int id = input.nextInt();
                    break;
                case 8:
                    rentalRepo.getAllRentals().forEach(System.out::println);
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
