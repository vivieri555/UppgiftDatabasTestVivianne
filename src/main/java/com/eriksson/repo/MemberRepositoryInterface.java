package com.eriksson.repo;

import com.eriksson.entity.Member;

public interface MemberRepositoryInterface {
    void save(Member member);
    void delete(Member member);
}
