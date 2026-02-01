package com.eriksson.repo;

import com.eriksson.entity.Member;

import java.util.List;

public interface MemberRepositoryInterface {
    void save(Member member);
    void delete(Member member);

    List<Member> getAllMembers();
}
