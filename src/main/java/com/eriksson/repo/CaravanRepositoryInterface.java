package com.eriksson.repo;

import com.eriksson.entity.Caravan;

import java.util.Optional;

public interface CaravanRepositoryInterface {
    void save(Caravan caravan);
    Optional<Caravan> findById(Long id);
}
