package com.eriksson.repo;

import com.eriksson.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryInterface {
    void save(Member member);
    void delete(Long member);

    List<Member> getAllMembers();
    Optional<Member> searchEmail(String email);
    Member searchId(Long id);
    void updateName (String firstName, String lastName, Long id);
}
