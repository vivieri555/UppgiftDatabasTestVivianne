package com.eriksson;

import com.eriksson.entity.Member;
import com.eriksson.repo.MemberRepository;
import com.eriksson.repo.MemberRepositoryInterface;
import com.eriksson.repo.RentalRepository;
import com.eriksson.repo.RentalRepositoryInterface;
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

        //Service
        MemberService memberService = new MemberService(memberRepo);
        RentalService rentalService = new RentalService(rentalRepo);

        Scanner input = new Scanner(System.in);

        try {

            System.out.println("------Välkommen till Vivi's uthyrning!-----------");
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

            Member member = memberService.createMember(firstName, lastName, email, status, history);


            Thread.sleep(100000);
        } catch (InterruptedException e){

        }
        finally {
            HibernateUtil.shutdown();
        }
    }
}