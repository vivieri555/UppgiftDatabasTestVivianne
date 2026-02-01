package com.eriksson.service;

import com.eriksson.entity.Member;
import com.eriksson.repo.MemberRepository;
import com.eriksson.repo.MemberRepositoryInterface;

import java.util.List;

public class MemberService {
    private final MemberRepositoryInterface memberRepository;

    public MemberService(MemberRepositoryInterface memberRepository) { this.memberRepository = memberRepository;}

    //Skapa o spara ny medlem, validerar, Mer avancerad validering (t.ex. email-format)
    //         * skulle också höra hemma här.

    public void createMember(String firstName, String lastName, String email, String status, String history) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException ("fyll i namn");
        }
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException ("fyll i email");
        }

        Member member = new Member(firstName.trim(), lastName.trim(), email.trim(),  status.trim(), history.trim());
        memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }
}
