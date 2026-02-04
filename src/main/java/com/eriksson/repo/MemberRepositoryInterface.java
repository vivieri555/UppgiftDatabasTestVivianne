package com.eriksson.repo;

import com.eriksson.entity.Member;

import java.util.List;

public interface MemberRepositoryInterface {
    void save(Member member);
    void delete(Long member);

    List<Member> getAllMembers();
    String searchEmail(Member member);
    Long searchId(Long id);
}
