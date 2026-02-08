package com.eriksson.service;

import com.eriksson.entity.Member;
import com.eriksson.exception.InvalidEmailException;
import com.eriksson.exception.InvalidNameException;
import com.eriksson.repo.MemberRepository;
import com.eriksson.repo.MemberRepositoryInterface;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MemberService {
    private final MemberRepositoryInterface memberRepository;

    public MemberService(MemberRepositoryInterface memberRepository) { this.memberRepository = memberRepository;}


    public void createMember(String firstName, String lastName, String email, String status, String history) {
        if (firstName == null || firstName.isBlank()) {
            throw new InvalidNameException("fyll i förnamn");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new InvalidNameException("fyll i efternamn");
        }
        if(email == null || email.isBlank()) {
            throw new InvalidEmailException("fyll i email");
        }

        Member member = new Member(firstName.trim(), lastName.trim(), email.trim(),  status.trim(), history.trim());
        memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }
    public void membersMenu() {
        boolean isRunning = true;
        Scanner input = new Scanner(System.in);
        while (isRunning) {
            System.out.println("******** Hantera Medlemmar *********");
            System.out.println("Tryck 1 för att lägga till ny medlem");
            System.out.println("Tryck 2 för att ändra på medlem");
            System.out.println("Tryck 3 för att lista alla medlemmar");
            System.out.println("Tryck 4 för att söka medlem via email");
            System.out.println("Tryck 5 för att radera en medlem");
            System.out.println("Tryck 9 för att avsluta medlemsmenyn");

        int answer = 0;
        try {
            answer = input.nextInt();
        }
        catch (Exception e) {
            System.out.println("Inte ett giltigt menyval, testa igen");
            answer = input.nextInt();
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

                createMember(firstName, lastName, email, status, history);
                break;
            case 2:
                //Ändra på medlem Update
              //  memberRepository.updateName();
                break;
            case 3:
                for(Member member : getAllMembers())
                {
                    System.out.println(member.getFirstName() + " " +member.getLastName() + ", id: "
                            + member.getId() + ", email: " + member.getEmail() + ", status: " + member.getStatus() +
                            ", historik: " + member.getHistory());
                }
                break;
            case 4:
                input.nextLine();
                System.out.println("Vilken emailadress vill du söka på?");
                String searchEmail = input.nextLine();
                Optional<Member> result = memberRepository.searchEmail(searchEmail);
                System.out.println(result);
                break;
            case 5:
                System.out.println("Vilket id har medlemmen som du vill radera?");
                Long del = input.nextLong();
                memberRepository.delete(del);
                break;
            case 9:
                System.out.println("Medlemsmenyn avslutas nu...");
                isRunning = false;
                break;
            default:
                System.out.println("Inte giltigt menyval");
                break;
        }
        System.out.println();
        }
    }
}
